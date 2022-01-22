package com.ecommerce.shop.Model;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PRODUCT_IMAGES")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ProductImagesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imagePath;
    
    @OneToMany
    @JoinColumn(name = "product_id") // we need to duplicate the physical information
    private List<ProductModel> productModel;
}
