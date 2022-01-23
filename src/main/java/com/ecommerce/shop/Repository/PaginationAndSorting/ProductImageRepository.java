package com.ecommerce.shop.Repository.PaginationAndSorting;

import com.ecommerce.shop.Model.ProductImagesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImagesModel, Long> {
    
    @Query(value ="select * from product_images pim where pim.product_id=:productModel",
            nativeQuery = true)
    List<ProductImagesModel> getProductImagesByProductId(@Param("productModel") Long productModel);
}
