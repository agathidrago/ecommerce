package com.ecommerce.shop.Service;

import com.ecommerce.shop.Model.CartItemModel;
import com.ecommerce.shop.Model.ProductModel;
import com.ecommerce.shop.Repository.CartItemRepository;
import com.ecommerce.shop.Repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ShoppingCartService {

    @Autowired
    private CartItemRepository cr;

    @Autowired
    private ProductRepository productRepository;

    public List<CartItemModel> listCartItems(Long userId) {
        log.info("List User Cart Items");
        return cr.findByUserId(userId);
    }

    public Integer addProduct(Long productId, Integer quantity, Long userId) throws Exception {
        log.info("Add product to cart:: {} by {}", productId, userId);
        Integer addedQuantity = quantity;

        ProductModel product = productRepository.findById(productId).get();
        if (product.getStock() > quantity)
            product.setStock(product.getStock() - quantity);
        else
            throw new Exception("Sorry, there is/are only " + product.getStock() + " " + product.getName() + " left!");

        CartItemModel cartItem = cr.findByUserIdAndProduct(userId, product);

        if (cartItem != null) {
            addedQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(addedQuantity);
        } else {
            cartItem = new CartItemModel();
            cartItem.setQuantity(quantity);
            cartItem.setUserId(userId);
            cartItem.setProduct(product);
        }

        cr.save(cartItem);

        return addedQuantity;
    }

    @Transactional
    public BigDecimal updateQuantity(Integer quantity, Long productId, Long userId) {
        log.info("Update Quantity");
        cr.updateQuantity(quantity, productId, userId);
        ProductModel product = productRepository.findById(productId).get();

        BigDecimal subtotal = product.getDefaultPrice().multiply(BigDecimal.valueOf(quantity));

        return subtotal;
    }

    @Transactional
    public void removeProductFromCart(Long userId, Long productId) {
        log.info("Remove quantity");
        cr.deleteByUserIdAndProduct(userId, productId);
    }

}
