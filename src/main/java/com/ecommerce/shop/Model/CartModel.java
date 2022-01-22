package com.ecommerce.shop.Model;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CartModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private int quantity;

    public void ToCart(ProductModel product){
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getDefaultPrice();
        this.quantity = 1;
    }

}
