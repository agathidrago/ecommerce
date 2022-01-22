package com.ecommerce.shop.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ORDER_MODEL")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class OrderModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date orderDate;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="user_id", nullable=false)
    private UserModel user;

    @OneToMany
    @JsonManagedReference
    @Column(name = "unit_id")
    private List<OrderUnitModel> orderUnits;
}
