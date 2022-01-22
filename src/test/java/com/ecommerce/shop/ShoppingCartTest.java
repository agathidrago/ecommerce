package com.ecommerce.shop;

import com.ecommerce.shop.Model.CartItemModel;
import com.ecommerce.shop.Model.ProductModel;
import com.ecommerce.shop.Model.UserModel;
import com.ecommerce.shop.Repository.CartItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ShoppingCartTest {

    @Autowired
    private CartItemRepository cartRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testAddOne(){

        ProductModel productModel = entityManager.find(ProductModel.class, 4L);
        UserModel user = entityManager.find(UserModel.class, 4L);

        CartItemModel newItem = new CartItemModel();
        //newItem.setUserId(user);
        newItem.setProduct(productModel);
        newItem.setQuantity(1);

        CartItemModel savedCart = cartRepo.save(newItem);

        assertTrue(savedCart.getId() > 0);

    }

//    @Test
//    public void testGetItems(){
//
//        UserModel user = new UserModel();
//        user.setId(4L);
//
//        List<CartItemModel> cartItems = cartRepo.findByUser(user);
//
//        assertEquals(2, cartItems.size());
//
//    }

}
