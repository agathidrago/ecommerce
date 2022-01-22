package com.ecommerce.shop.Service;

import com.ecommerce.shop.Model.OrderModel;
import com.ecommerce.shop.Model.OrderUnitModel;
import com.ecommerce.shop.Model.ProductModel;
import com.ecommerce.shop.Repository.OrderRepository;
import com.ecommerce.shop.Repository.OrderUnitRepository;
import com.ecommerce.shop.Repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class OrderUnitService {

    @Autowired
    private OrderUnitRepository orderUnitRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<OrderUnitModel> findAll(Integer pageNo, Integer pageSize, String sortBy) {
        log.info("Find all order units");

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<OrderUnitModel> pagedResult = orderUnitRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<OrderUnitModel>();
        }
    }

    public Optional<OrderUnitModel> findById(Long unitId) {
        log.info("Find order unit by id:: {}", unitId);
        return orderUnitRepository.findById(unitId);
    }

    public OrderUnitModel addNewOrderUnit(OrderUnitModel orderUnit) {
        log.info("Add new order unit:: {}", orderUnit);
        return orderUnitRepository.save(orderUnit);
    }

    @Transactional
    public void updateOrderUnit(Long unitId, OrderModel order, ProductModel product, int amount, float price) {
        log.info("Update Order Unit {}, order {}, product {}, amount {}, price {}", unitId, order, product, amount, price);
        OrderUnitModel orderModel = orderUnitRepository.findById(unitId)
                .orElseThrow(() -> new IllegalStateException(
                        "OrderUnit with ID " + unitId + "does not exist!"
                ));
        orderModel.setOrder(order);
        orderModel.setProduct(product);
        orderModel.setAmount(amount);
        orderModel.setPrice(price);
    }

    public void deleteOrderUnit(Long unitId) {
        log.info("Delete order unit");
        boolean exists = orderUnitRepository.existsById(unitId);
        if (!exists) {
            log.error("Order unit doesn't exist:: {}", unitId);
            throw new IllegalStateException(
                    "OrderUnit with ID " + unitId + "does not exist!"
            );
        }

        orderUnitRepository.deleteById(unitId);
    }

}
