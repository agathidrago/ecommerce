package com.ecommerce.shop.Service;

import com.ecommerce.shop.Model.OrderModel;
import com.ecommerce.shop.Model.OrderUnitModel;
import com.ecommerce.shop.Model.UserModel;
import com.ecommerce.shop.Repository.OrderRepository;
import com.ecommerce.shop.Repository.OrderUnitRepository;
import com.ecommerce.shop.Repository.PaginationAndSorting.OrderPaginationAndSorting;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderUnitRepository orderUnitRepository;

    @Autowired
    private OrderPaginationAndSorting orderPaging;

    public List<OrderModel> getAllOrders(Integer pageNo, Integer pageSize, String sortBy) {
        log.info("Get all orders");

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<OrderModel> pagedResult = orderPaging.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<OrderModel>();
        }
    }

    public Optional<OrderModel> findById(Long orderId) {
        log.info("Get order:: {}", orderId);
        return orderRepository.findById(orderId);
    }

    public OrderModel addNewOrder(OrderModel order, UserModel user) {
        log.info("Add new order:: {}, {}", order, user);
        order.setOrderDate(new Date());
        order.setUser(user);
        return orderRepository.save(order);
    }

    @Transactional
    public void updateOrder(Long orderId, UserModel user) {
        log.info("Update order {} by user {}", orderId, user);
        OrderModel orderModel = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException(
                        "Order with ID " + orderId + "does not exist!"
                ));
        orderModel.setUser(user);
    }

    public void deleteOrder(Long orderId) {
        log.info("Delete order:: {}", orderId);
        boolean exists = orderRepository.existsById(orderId);
        if (!exists) {
            throw new IllegalStateException(
                    "Order with ID " + orderId + " does not exist!"
            );
        } else {
            List<OrderUnitModel> units  = orderUnitRepository.findByOrderId(orderId);

            for(OrderUnitModel unit : units){
                orderUnitRepository.delete(unit);
            }

            orderRepository.deleteById(orderId);
        }
    }
}
