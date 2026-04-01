package com.example.food_store.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.food_store.domain.Role;
import com.example.food_store.domain.User;
import com.example.food_store.domain.dto.RegisterDTO;

public interface IUserService {
    void saveUser(User user);
    void deleteUserById(long id);
    long countUser();
    long countOrder();
    boolean checkEmailExist(String email);
    Role getRoleByName(String name);
    List<Role> getRolesByNames(List<String> names);
    User registerDTOtoUser(RegisterDTO registerDTO);
    User handleSaveUser(User user);
    User getUserById(long id);
    User getUserByEmail(String email);
    Page<User> getAllUsers(Pageable pageable);

}
