package com.ecommerce.shop.Repository;

import com.ecommerce.shop.Model.OrderUnitModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderUnitRepository extends JpaRepository<OrderUnitModel, Long> {

    public List<OrderUnitModel> findByOrderId(Long orderId);
}
