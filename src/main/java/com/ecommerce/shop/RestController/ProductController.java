package com.ecommerce.shop.RestController;

import com.ecommerce.shop.Model.ProductCategoriesModel;
import com.ecommerce.shop.Model.ProductModel;
import com.ecommerce.shop.Service.CategoryService;
import com.ecommerce.shop.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<ProductModel>> findAll(Model model,
                                                      @RequestParam(defaultValue = "0") Integer pageNo,
                                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                                      @RequestParam(defaultValue = "id") String sortBy) {

        List<ProductModel> products = productService.getAllProducts(pageNo, pageSize, sortBy);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping(path = "get/{productId}")
    public Optional<ProductModel> findById(@PathVariable("productId") Long productId) {
        return productService.findById(productId);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<ProductModel> addProduct(@RequestBody ProductModel product) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/products/addProduct").toUriString());
        return ResponseEntity.created(uri).body(productService.addNewProduct(product));
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductModel> updateProduct(@PathVariable("productId") Long productId,
                              @RequestBody ProductModel product) {

        return ResponseEntity.ok().body(productService.updateProduct(productId, product));
    }

    @DeleteMapping(path = "/delete/{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
    }

}
