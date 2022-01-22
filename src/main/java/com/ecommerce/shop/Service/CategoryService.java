package com.ecommerce.shop.Service;

import com.ecommerce.shop.Model.ProductCategoriesModel;
import com.ecommerce.shop.Model.ProductModel;
import com.ecommerce.shop.Repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<ProductCategoriesModel> findAll(Integer pageNo, Integer pageSize, String sortBy) {
        log.info("Find all products");

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<ProductCategoriesModel> pagedResult = categoryRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<ProductCategoriesModel>();
        }
    }

    public Optional<ProductCategoriesModel> findById(Long productId) {
        log.info("Find product with id:: {}", productId);
        return categoryRepository.findById(productId);
    }

    public ProductCategoriesModel addNewCategory(ProductCategoriesModel category) {
        log.info("Add new Category:: {}", category);
        return categoryRepository.save(category);
    }

    @Transactional
    public ProductCategoriesModel updateCategory(Long categoryId, ProductCategoriesModel updatedCategory) {
        //log.info("Update Category id {}, name {}, products {}", categoryId,
              //  updatedCategory.getName(), updatedCategory.getProducts());
        ProductCategoriesModel categoryModel = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalStateException(
                        "Category with ID " + categoryId + "does not exist!"
                ));

        categoryModel.setName(updatedCategory.getName());
      //  categoryModel.setProducts(updatedCategory.getProducts());

        return categoryModel;
    }

    public void deleteCategory(Long categoryId) {
        log.error("Delete category:: {}", categoryId);
        boolean exists = categoryRepository.existsById(categoryId);
        if (!exists) {
            throw new IllegalStateException(
                    "Category with ID " + categoryId + "does not exist!"
            );
        }

        categoryRepository.deleteById(categoryId);
    }

}
