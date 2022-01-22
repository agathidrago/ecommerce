package com.ecommerce.shop.RestController;

import com.ecommerce.shop.Model.ProductCategoriesModel;
import com.ecommerce.shop.Model.ProductModel;
import com.ecommerce.shop.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<ProductCategoriesModel>> findAll(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                @RequestParam(defaultValue = "10") Integer pageSize,
                                                                @RequestParam(defaultValue = "id") String sortBy) {
        List<ProductCategoriesModel> categories = categoryService.findAll(pageNo, pageSize, sortBy);
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping(path = "get/{categoryId}")
    public Optional<ProductCategoriesModel> findById(@PathVariable("categoryId") Long categoryId) {
        return categoryService.findById(categoryId);
    }

    @PostMapping("/addCategory")
    public ResponseEntity<ProductCategoriesModel> addCategory(@RequestBody ProductCategoriesModel category) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/categories/addCategory").toUriString());
        return ResponseEntity.created(uri).body(categoryService.addNewCategory(category));
    }

    @PutMapping(path = "/update/{categoryId}")
    public ResponseEntity<ProductCategoriesModel> updateCategory(@PathVariable("categoryId") Long categoryId,
                               @RequestBody ProductCategoriesModel categoriesModel) {

        return ResponseEntity.ok().body(categoryService.updateCategory(categoryId, categoriesModel));
    }

    @DeleteMapping(path = "/delete/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }


}
