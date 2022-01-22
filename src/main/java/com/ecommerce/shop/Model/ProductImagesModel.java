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
    
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductModel productModel;
}
