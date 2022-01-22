package com.ecommerce.shop.Repository.PaginationAndSorting;

import com.ecommerce.shop.Model.UserModel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPaginationAndSorting extends PagingAndSortingRepository<UserModel, Long> {
}
