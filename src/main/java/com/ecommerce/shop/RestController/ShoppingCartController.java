package com.ecommerce.shop.RestController;

import com.ecommerce.shop.Model.CartItemModel;
import com.ecommerce.shop.Model.UserModel;
import com.ecommerce.shop.Service.ShoppingCartService;
import com.ecommerce.shop.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/shopping/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService ss;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<CartItemModel>> showShoppingCart(Principal principal) {

        UserModel user = userService.findByEmail(principal.getName());
        List<CartItemModel> cartItems = ss.listCartItems(user.getId());

        return ResponseEntity.ok().body(cartItems);
    }

    @PostMapping("/add/{productId}/{quantity}")
    public ResponseEntity<String> addProductToCart(@PathVariable("productId") Long productId,
                                                   @PathVariable("quantity") Integer quantity,
                                                   Principal principal) throws Exception {

        UserModel user = userService.findByEmail(principal.getName());
        Integer addedQuantity = ss.addProduct(productId, quantity, user.getId());

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/shopping/cart/add/{productId}/{quantity}").toUriString());
        return ResponseEntity.created(uri).body(addedQuantity + " added item(s)");
    }

    @PostMapping("/update/{productId}/{quantity}")
    public ResponseEntity<String> updateQuantity(@PathVariable("productId") Long productId,
                                                 @PathVariable("quantity") Integer quantity,
                                                 Principal principal) {

        UserModel user = userService.findByEmail(principal.getName());
        BigDecimal subtotal = ss.updateQuantity(quantity, productId, user.getId());

        return ResponseEntity.ok().body(String.valueOf(subtotal));
    }

    @PostMapping("/remove/{productId}")
    public ResponseEntity<String> removeProductFromCart(@PathVariable("productId") Long productId,
                                                        Principal principal) {

        UserModel user = userService.findByEmail(principal.getName());
        ss.removeProductFromCart(user.getId(), productId);
        return ResponseEntity.ok().body("Product has been removed from your shopping cart!");
    }
}
