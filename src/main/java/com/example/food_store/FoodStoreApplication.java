package com.example.food_store;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.food_store.domain.Role;
import com.example.food_store.domain.User;
import com.example.food_store.repository.RoleRepository;
import com.example.food_store.repository.UserRepository;

@SpringBootApplication
public class FoodStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoodStoreApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                Role adminRole = new Role();
                adminRole.setName("ADMIN");
                adminRole.setDescription("");

                Role userRole = new Role();
                userRole.setName("USER");
                userRole.setDescription("");

                roleRepository.save(adminRole);
                roleRepository.save(userRole);

                System.out.println("Imported default roles: ADMIN, USER");
            }
        };
    }

    @Bean
    CommandLineRunner initDatabaseUser(RoleRepository roleRepository, UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            Role adminRole = roleRepository.findByName("ADMIN");
            if (adminRole == null) {
                return;
            }

            String adminEmail = "nguyenthanhsang02102@gmail.com";
            User adminUser = userRepository.findByEmail(adminEmail);
            if (adminUser == null) {
                adminUser = new User();
                adminUser.setEmail(adminEmail);
                adminUser.setFullName("NguyenThanhSang");
                adminUser.setProvider("LOCAL");
                adminUser.setAddress("daklak");
                adminUser.setAvatar("");
                adminUser.setPhone("0336666666");
            }

            adminUser.setRole(adminRole);
            adminUser.setPassword(passwordEncoder.encode("123456"));
            userRepository.save(adminUser);

            System.out.println("Synced default admin user: " + adminEmail + " (password: 123456)");
        };
    }
}
