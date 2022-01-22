package com.ecommerce.shop.Service;

import com.ecommerce.shop.Model.ProductCategoriesModel;
import com.ecommerce.shop.Model.ProductModel;
import com.ecommerce.shop.Repository.PaginationAndSorting.ProductPaginationAndSorting;
import com.ecommerce.shop.Repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPaginationAndSorting productPaging;

    public List<ProductModel> findAll() {
        log.info("Find all products");
        return productRepository.findAll();
    }

    public List<ProductModel> getAllProducts(Integer pageNo, Integer pageSize, String sortBy) {
        log.info("Find all products with pageNo {}, pagesize {}, sortby {}", pageNo, pageSize, sortBy);
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<ProductModel> pagedResult = productPaging.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<ProductModel>();
        }
    }

    public Optional<ProductModel> findById(Long productId) {
        log.info("Find by id {}", productId);
        return productRepository.findById(productId);
    }

    public ProductModel addNewProduct(ProductModel product) {
        log.info("Add new product:: {}", product);
        return productRepository.save(product);
    }

    @Transactional
    public ProductModel updateProduct(Long productId, ProductModel product) {
        log.info("Update Product id {}, ", productId);
        ProductModel existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException(
                        "Product with ID " + productId + "does not exist!"
                ));
        existingProduct.setName(product.getName());
        existingProduct.setDefaultPrice(product.getDefaultPrice());
        existingProduct.setStock(product.getStock());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());

        return existingProduct;
    }

    public void deleteProduct(Long productId) {
        log.info("Delete product {}", productId);
        boolean exists = productRepository.existsById(productId);
        if (!exists) {
            log.error("Product with ID {} does not exist", productId);
            throw new IllegalStateException(
                    "Product with ID " + productId + "does not exist!"
            );
        }
        productRepository.deleteById(productId);
    }

}
