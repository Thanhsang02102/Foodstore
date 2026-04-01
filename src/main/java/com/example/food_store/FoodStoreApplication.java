package com.example.food_store;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.food_store.domain.Role;
import com.example.food_store.domain.User;
import com.example.food_store.repository.jdbc.JdbcRoleRepository;
import com.example.food_store.service.impl.UserService;

@SpringBootApplication
public class FoodStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoodStoreApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(JdbcRoleRepository roleRepository) {
        return args -> {
            Role adminRole = roleRepository.findByName("ADMIN");
            if (adminRole == null) {
                Role newAdminRole = new Role();
                newAdminRole.setName("ADMIN");
                newAdminRole.setDescription("");
                roleRepository.save(newAdminRole);
                System.out.println("Imported default role: ADMIN");
            }

            Role userRole = roleRepository.findByName("USER");
            if (userRole == null) {
                Role newUserRole = new Role();
                newUserRole.setName("USER");
                newUserRole.setDescription("");
                roleRepository.save(newUserRole);
                System.out.println("Imported default role: USER");
            }

            Role staffRole = roleRepository.findByName("STAFF");
            if (staffRole == null) {
                Role newStaffRole = new Role();
                newStaffRole.setName("STAFF");
                newStaffRole.setDescription("");
                roleRepository.save(newStaffRole);
                System.out.println("Imported default role: STAFF");
            }
        };
    }

    @Bean
    CommandLineRunner initDatabaseUser(JdbcRoleRepository roleRepository, UserService userService,
            PasswordEncoder passwordEncoder) {
        return args -> {
            Role adminRole = roleRepository.findByName("ADMIN");
            if (adminRole == null) {
                return;
            }

            String adminEmail = "nguyenthanhsang02102@gmail.com";
            User adminUser = userService.getUserByEmail(adminEmail);
            if (adminUser == null) {
                adminUser = new User();
                adminUser.setEmail(adminEmail);
                adminUser.setFullName("NguyenThanhSang");
                adminUser.setProvider("LOCAL");
                adminUser.setAddress("daklak");
                adminUser.setAvatar("");
                adminUser.setPhone("0336666666");
            }

            adminUser.setRoles(java.util.List.of(adminRole));
            adminUser.setPassword(passwordEncoder.encode("123456"));
            userService.handleSaveUser(adminUser);

            System.out.println("Synced default admin user: " + adminEmail + " (password: 123456)");

            Role userRole = roleRepository.findByName("USER");
            if (userRole == null) {
                return;
            }

            String defaultUserEmail = "user1@foodstore.com";
            User defaultUser = userService.getUserByEmail(defaultUserEmail);
            if (defaultUser == null) {
                defaultUser = new User();
                defaultUser.setEmail(defaultUserEmail);
                defaultUser.setFullName("Food Store User");
                defaultUser.setProvider("LOCAL");
                defaultUser.setAddress("Ho Chi Minh City");
                defaultUser.setAvatar("");
                defaultUser.setPhone("0900000001");
            }

            defaultUser.setRoles(java.util.List.of(userRole));
            defaultUser.setPassword(passwordEncoder.encode("123456"));
            userService.handleSaveUser(defaultUser);

            System.out.println("Synced default user: " + defaultUserEmail + " (password: 123456)");
        };
    }
}
