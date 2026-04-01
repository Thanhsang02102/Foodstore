package com.example.food_store.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.food_store.constant.AppConstant;
import com.example.food_store.domain.Role;
import com.example.food_store.domain.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository {
    private final JdbcTemplate jdbcTemplate;

    public User save(User user) {
        if (user.getId() > 0) {
            update(user);
            return user;
        }

        insert(user);
        return user;
    }

    public Page<User> findAll(Pageable pageable) {
        long total = count();
        List<User> users = aggregateUsers(jdbcTemplate.query(
                """
                        SELECT u.id, u.email, u.password, u.full_name, u.address, u.phone, u.avatar, u.provider,
                               u.create_day_time, u.update_day_time,
                               r.id AS role_id, r.name AS role_name, r.description AS role_description,
                               r.create_day_time AS role_create_day_time, r.update_day_time AS role_update_day_time
                        FROM users u
                        LEFT JOIN user_roles ur ON ur.user_id = u.id
                        LEFT JOIN roles r ON r.id = ur.role_id
                        ORDER BY u.id DESC, r.id ASC
                        LIMIT ? OFFSET ?
                """,
                (rs, rowNum) -> mapRow(rs),
                pageable.getPageSize(),
                pageable.getOffset()));
        return new PageImpl<>(users, pageable, total);
    }

    public User findById(long id) {
        List<User> users = aggregateUsers(jdbcTemplate.query(
                """
                        SELECT u.id, u.email, u.password, u.full_name, u.address, u.phone, u.avatar, u.provider,
                               u.create_day_time, u.update_day_time,
                               r.id AS role_id, r.name AS role_name, r.description AS role_description,
                               r.create_day_time AS role_create_day_time, r.update_day_time AS role_update_day_time
                        FROM users u
                        LEFT JOIN user_roles ur ON ur.user_id = u.id
                        LEFT JOIN roles r ON r.id = ur.role_id
                        WHERE u.id = ?
                        ORDER BY r.id ASC
                """,
                (rs, rowNum) -> mapRow(rs),
                id));
        return users.isEmpty() ? null : users.get(0);
    }

    public void deleteById(long id) {
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id = ?", id);
        jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
    }

    public boolean existsByEmail(String email) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users WHERE email = ?", Integer.class, email);
        return count != null && count > 0;
    }

    public User findByEmail(String email) {
        List<User> users = aggregateUsers(jdbcTemplate.query(
                """
                        SELECT u.id, u.email, u.password, u.full_name, u.address, u.phone, u.avatar, u.provider,
                               u.create_day_time, u.update_day_time,
                               r.id AS role_id, r.name AS role_name, r.description AS role_description,
                               r.create_day_time AS role_create_day_time, r.update_day_time AS role_update_day_time
                        FROM users u
                        LEFT JOIN user_roles ur ON ur.user_id = u.id
                        LEFT JOIN roles r ON r.id = ur.role_id
                        WHERE u.email = ?
                        ORDER BY r.id ASC
                """,
                (rs, rowNum) -> mapRow(rs),
                email));
        return users.isEmpty() ? null : users.get(0);
    }

    public long count() {
        Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Long.class);
        return count == null ? 0L : count;
    }

    private void insert(User user) {
        String timestamp = timestamp();
        if (user.getCreateDayTime() == null || user.getCreateDayTime().isBlank()) {
            user.setCreateDayTime(timestamp);
        }
        user.setUpdateDayTime(timestamp);

        Integer primaryRoleId = user.getRole() != null ? user.getRole().getId() : null;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            var statement = connection.prepareStatement(
                    """
                            INSERT INTO users (
                                email, password, full_name, address, phone, avatar, provider, role_id,
                                create_day_time, update_day_time
                            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                            """,
                    new String[] { "id" });
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFullName());
            statement.setString(4, user.getAddress());
            statement.setString(5, user.getPhone());
            statement.setString(6, user.getAvatar());
            statement.setString(7, user.getProvider() == null || user.getProvider().isBlank() ? "LOCAL" : user.getProvider());
            statement.setObject(8, primaryRoleId);
            statement.setString(9, user.getCreateDayTime());
            statement.setString(10, user.getUpdateDayTime());
            return statement;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            user.setId(key.longValue());
            syncUserRoles(user);
        }
    }

    private void update(User user) {
        User existingUser = findById(user.getId());
        if (existingUser != null && (user.getCreateDayTime() == null || user.getCreateDayTime().isBlank())) {
            user.setCreateDayTime(existingUser.getCreateDayTime());
        }
        if (user.getCreateDayTime() == null || user.getCreateDayTime().isBlank()) {
            user.setCreateDayTime(timestamp());
        }
        user.setUpdateDayTime(timestamp());

        Integer primaryRoleId = user.getRole() != null ? user.getRole().getId() : null;

        jdbcTemplate.update(
                """
                        UPDATE users
                        SET email = ?, password = ?, full_name = ?, address = ?, phone = ?, avatar = ?,
                            provider = ?, role_id = ?, create_day_time = ?, update_day_time = ?
                        WHERE id = ?
                        """,
                user.getEmail(),
                user.getPassword(),
                user.getFullName(),
                user.getAddress(),
                user.getPhone(),
                user.getAvatar(),
                user.getProvider() == null || user.getProvider().isBlank() ? "LOCAL" : user.getProvider(),
                primaryRoleId,
                user.getCreateDayTime(),
                user.getUpdateDayTime(),
                user.getId());

        syncUserRoles(user);
    }

    private void syncUserRoles(User user) {
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id = ?", user.getId());
        for (Role role : user.getRoles()) {
            if (role == null || role.getId() == null) {
                continue;
            }
            jdbcTemplate.update(
                    "INSERT INTO user_roles (user_id, role_id, create_day_time, update_day_time) VALUES (?, ?, ?, ?)",
                    user.getId(),
                    role.getId(),
                    user.getCreateDayTime(),
                    user.getUpdateDayTime());
        }
    }

    private List<User> aggregateUsers(List<UserRoleRow> rows) {
        Map<Long, User> usersById = new LinkedHashMap<>();
        for (UserRoleRow row : rows) {
            User user = usersById.computeIfAbsent(row.userId(), ignored -> mapUserBase(row));
            Role role = mapRole(row);
            if (role != null && user.getRoles().stream().map(Role::getId).filter(Objects::nonNull).noneMatch(role.getId()::equals)) {
                List<Role> roles = new ArrayList<>(user.getRoles());
                roles.add(role);
                user.setRoles(roles);
            }
        }
        return new ArrayList<>(usersById.values());
    }

    private static User mapUserBase(UserRoleRow row) {
        User user = new User();
        user.setId(row.userId());
        user.setEmail(row.email());
        user.setPassword(row.password());
        user.setFullName(row.fullName());
        user.setAddress(row.address());
        user.setPhone(row.phone());
        user.setAvatar(row.avatar());
        user.setProvider(row.provider());
        user.setCreateDayTime(row.createDayTime());
        user.setUpdateDayTime(row.updateDayTime());
        user.setRoles(new ArrayList<>());
        return user;
    }

    private static Role mapRole(UserRoleRow row) {
        if (row.roleId() == null) {
            return null;
        }

        Role role = new Role();
        role.setId(row.roleId());
        role.setName(row.roleName());
        role.setDescription(row.roleDescription());
        role.setCreateDayTime(row.roleCreateDayTime());
        role.setUpdateDayTime(row.roleUpdateDayTime());
        return role;
    }

    private static UserRoleRow mapRow(ResultSet rs) throws SQLException {
        Integer roleId = null;
        int roleIdValue = rs.getInt("role_id");
        if (!rs.wasNull()) {
            roleId = roleIdValue;
        }
        return new UserRoleRow(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("full_name"),
                rs.getString("address"),
                rs.getString("phone"),
                rs.getString("avatar"),
                rs.getString("provider"),
                rs.getString("create_day_time"),
                rs.getString("update_day_time"),
                roleId,
                rs.getString("role_name"),
                rs.getString("role_description"),
                rs.getString("role_create_day_time"),
                rs.getString("role_update_day_time"));
    }

    private record UserRoleRow(
            long userId,
            String email,
            String password,
            String fullName,
            String address,
            String phone,
            String avatar,
            String provider,
            String createDayTime,
            String updateDayTime,
            Integer roleId,
            String roleName,
            String roleDescription,
            String roleCreateDayTime,
            String roleUpdateDayTime) {
    }

    private static String timestamp() {
        return LocalDateTime.now().format(AppConstant.formatter);
    }
}
