package com.ecommerce.shop.Repository;

import com.ecommerce.shop.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<UserModel, Long> {

    UserModel findByEmail(String email);

    @Query("select count(u) = 1 from UserModel u where email = ?1")
    public boolean findExistByEmail(String email);
}
