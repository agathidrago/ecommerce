package com.ecommerce.shop.Repository.PaginationAndSorting;

import com.ecommerce.shop.Model.OrderModel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPaginationAndSorting extends PagingAndSortingRepository<OrderModel, Long> {
}
