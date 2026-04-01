package com.example.food_store.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.food_store.domain.Role;
import com.example.food_store.domain.User;
import com.example.food_store.domain.dto.RegisterDTO;
import com.example.food_store.repository.OrderRepository;
import com.example.food_store.repository.jdbc.JdbcRoleRepository;
import com.example.food_store.repository.jdbc.JdbcUserRepository;
import com.example.food_store.service.IUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final JdbcUserRepository userRepository;
    private final JdbcRoleRepository roleRepository;
    private final OrderRepository orderRepository;

    @Override
    public User handleSaveUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

    @Override
    public List<Role> getRolesByNames(List<String> names) {
        if (names == null || names.isEmpty()) {
            return new ArrayList<>();
        }

        Map<String, Role> rolesByName = new LinkedHashMap<>();
        for (String name : names) {
            if (name == null || name.isBlank()) {
                continue;
            }
            Role role = this.roleRepository.findByName(name);
            if (role != null) {
                rolesByName.put(role.getName(), role);
            }
        }
        return new ArrayList<>(rolesByName.values());
    }

    @Override
    public User registerDTOtoUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setFullName(registerDTO.getFullName());
        user.setPassword(registerDTO.getPassword());
        user.setEmail(registerDTO.getEmail());
        return user;

    }

    @Override
    public boolean checkEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public long countUser() {
        return this.userRepository.count();
    }

    @Override
    public long countOrder() {
        return this.orderRepository.count();
    }

}
