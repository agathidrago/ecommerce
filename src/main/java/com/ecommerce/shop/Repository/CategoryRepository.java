package com.ecommerce.shop.Repository;

import com.ecommerce.shop.Model.ProductCategoriesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<ProductCategoriesModel, Long> {

}
