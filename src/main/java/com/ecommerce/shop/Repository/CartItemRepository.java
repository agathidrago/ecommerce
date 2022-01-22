package com.ecommerce.shop.Repository;

import com.ecommerce.shop.Model.CartItemModel;
import com.ecommerce.shop.Model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemModel, Long> {

    List<CartItemModel> findByUserId(Long userId);

    public CartItemModel findByUserIdAndProduct(Long userId, ProductModel product);

    @Query("UPDATE CartItemModel c SET c.quantity = ?1 WHERE c.product.id = ?2 AND c.userId = ?3")
    @Modifying
    public void updateQuantity(Integer quantity, Long id, Long userId);

    @Query("DELETE FROM CartItemModel c WHERE c.userId = ?1 AND c.product.id = ?2 ")
    @Modifying
    public void deleteByUserIdAndProduct(Long userId, Long productId);
}
