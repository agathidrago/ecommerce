package com.ecommerce.shop.Model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ROLE_MODEL")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public RoleModel(Long id) {
        this.id = id;
    }

    public RoleModel(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }

}
