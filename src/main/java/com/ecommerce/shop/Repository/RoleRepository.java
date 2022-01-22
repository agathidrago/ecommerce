package com.ecommerce.shop.Repository;

import com.ecommerce.shop.Model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {

    @Query(value = "SELECT r FROM RoleModel r WHERE r.name = ?1")
    public RoleModel findByName(String name);

}
