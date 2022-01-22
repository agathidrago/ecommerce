package com.ecommerce.shop.RestController;

import com.ecommerce.shop.Model.UserModel;
import com.ecommerce.shop.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserModel>> listUsers(@RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                     @RequestParam(defaultValue = "id") String sortBy) {

        List<UserModel> userList = userService.getAllUsers(pageNo, pageSize, sortBy);
        return ResponseEntity.ok().body(userList);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.getById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<UserModel> register(@RequestBody UserModel user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/add").toUriString());
        return ResponseEntity.created(uri).body(userService.signUpUser(user));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserModel> editUser(@PathVariable("id") Long id,
                                              @RequestBody UserModel user){
        return ResponseEntity.ok().body(userService.editUser(id, user));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        UserModel user = userService.getById(id);
        userService.deleteUser(user);
    }
}
