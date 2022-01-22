package com.ecommerce.shop.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "PRODUCT_CATEGORIES")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ProductCategoriesModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    /*@OneToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private List<ProductModel> products;*/

    @Override
    public String toString() {
        return getName();
    }
}
