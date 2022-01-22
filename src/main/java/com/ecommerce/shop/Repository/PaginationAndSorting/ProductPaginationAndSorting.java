package com.ecommerce.shop.Repository.PaginationAndSorting;

import com.ecommerce.shop.Model.ProductModel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPaginationAndSorting extends PagingAndSortingRepository<ProductModel, Long> {
}
