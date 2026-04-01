package com.example.food_store.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email(message = "Email khong hop le", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotNull
    @Size(min = 6, message = "Password phai co toi thieu 6 ky tu!")
    private String password;

    @NotNull
    @Size(min = 3, message = "Full Name phai co toi thieu 3 ky tu!")
    private String fullName;

    private String address;
    private String phone;
    private String avatar;
    private String provider;

    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @Transient
    private List<String> selectedRoleNames = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (this.provider == null) {
            this.provider = "LOCAL";
        }
    }

    @OneToMany(mappedBy = "user")
    List<Order> orders;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    public void setRoles(List<Role> roles) {
        this.roles = roles == null ? new ArrayList<>() : new ArrayList<>(roles);
        syncSelectedRoleNames();
    }

    public void setSelectedRoleNames(List<String> selectedRoleNames) {
        this.selectedRoleNames = selectedRoleNames == null ? new ArrayList<>() : new ArrayList<>(selectedRoleNames);
    }

    public Role getRole() {
        return roles.isEmpty() ? null : roles.get(0);
    }

    public void setRole(Role role) {
        this.roles = new ArrayList<>();
        if (role != null) {
            this.roles.add(role);
        }
        syncSelectedRoleNames();
    }

    public List<Role> getRoles() {
        return roles == null ? Collections.emptyList() : roles;
    }

    public List<String> getSelectedRoleNames() {
        if ((selectedRoleNames == null || selectedRoleNames.isEmpty()) && roles != null) {
            syncSelectedRoleNames();
        }
        return selectedRoleNames == null ? Collections.emptyList() : selectedRoleNames;
    }

    public String getRoleNamesDisplay() {
        if (roles == null || roles.isEmpty()) {
            return "";
        }
        return roles.stream().map(Role::getName).collect(Collectors.joining(", "));
    }

    private void syncSelectedRoleNames() {
        this.selectedRoleNames = roles == null ? new ArrayList<>()
                : roles.stream().map(Role::getName).collect(Collectors.toCollection(ArrayList::new));
    }
}
