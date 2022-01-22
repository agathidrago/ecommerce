package com.ecommerce.shop.Service;

import com.ecommerce.shop.Model.RoleModel;
import com.ecommerce.shop.Model.UserModel;
import com.ecommerce.shop.Repository.PaginationAndSorting.UserPaginationAndSorting;
import com.ecommerce.shop.Repository.RoleRepository;
import com.ecommerce.shop.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserPaginationAndSorting userPaging;

    private final static String USER_NOT_FOUND = "User with the email '%s' not found!";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserModel user = userRepository.findByEmail(email);

        if (user == null) {
            log.error("User not found with username:: {}", email);
            throw new UsernameNotFoundException("User not found!");
        } else {
            log.info("User found with username:: {}", email);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    public UserModel findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public UserModel getById(Long id){
        return userRepository.getById(id);
    }

    public List<UserModel> getAllUsers(Integer pageNo, Integer pageSize, String sortBy) {
        log.info("Fetching all users");

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<UserModel> pagedResult = userPaging.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<UserModel>();
        }
    }

    public UserModel signUpUser(UserModel userModel) {

        log.info("Sign up user:: {}", userModel);

        if (userRepository.findExistByEmail(userModel.getEmail())){
            log.error("Email already exists:: {}", userModel.getEmail());
            throw new IllegalStateException("Email taken!");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(userModel.getPassword());
        userModel.setPassword(encodedPassword);
        userModel.setFullName(userModel.getFullName());

        if (userModel.getRole().isEmpty()) {
            RoleModel role = roleRepository.findByName("Customer");
            userModel.addRole(role);
        }

        userRepository.save(userModel);
        return userModel;
    }

    @Transactional
    public UserModel editUser(Long id, UserModel user) {
        UserModel existingUser = userRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "User with ID " + id + "does not exist!"
        ));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        if (userRepository.findExistByEmail(user.getEmail()) && !Objects.equals(existingUser.getEmail(), user.getEmail())){
            log.error("Email already exists:: {}", user.getEmail());
            throw new IllegalStateException("Email taken!");
        }
        else
            existingUser.setEmail(user.getEmail());
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        existingUser.setPassword(encodedPassword);

        return existingUser;
    }

    public List<RoleModel> getRoles() {
        return roleRepository.findAll();
    }

    public void deleteUser(UserModel user){
        userRepository.delete(user);
    }
}
