package com.ecommerce.shop.Model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CartItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private ProductModel product;

    private Long userId;

    private Integer quantity;

    @Transient
    public BigDecimal getSubtotal(){
        return this.product.getDefaultPrice().multiply(BigDecimal.valueOf(quantity));
    }

}
