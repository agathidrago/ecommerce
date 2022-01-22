package com.ecommerce.shop.RestController;

import com.ecommerce.shop.Model.OrderModel;
import com.ecommerce.shop.Model.UserModel;
import com.ecommerce.shop.Service.OrderService;
import com.ecommerce.shop.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<OrderModel>> findAll(@RequestParam(defaultValue = "0") Integer pageNo,
                                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                                    @RequestParam(defaultValue = "id") String sortBy) {

        List<OrderModel> orders = orderService.getAllOrders(pageNo, pageSize, sortBy);
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping(path = "get/{orderId}")
    public Optional<OrderModel> findById(@PathVariable("orderId") Long orderId) {
        return orderService.findById(orderId);
    }

    @PostMapping("/addOrder")
    public ResponseEntity<OrderModel> addOrder(@RequestBody OrderModel order,
                                               Principal principal) {
        UserModel user = userService.findByEmail(principal.getName());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/orders/addOrder").toUriString());
        return ResponseEntity.created(uri).body(orderService.addNewOrder(order, user));
    }

    @PutMapping(path = "{orderId}")
    public void updateOrder(@PathVariable("orderId") Long orderId,
                            @RequestBody(required = false) UserModel user) {

        orderService.updateOrder(orderId, user);
    }

    @DeleteMapping(path = "/delete/{orderId}")
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        orderService.deleteOrder(orderId);
    }

}
