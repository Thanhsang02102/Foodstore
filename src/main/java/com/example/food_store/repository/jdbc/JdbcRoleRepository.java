package com.example.food_store.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.food_store.constant.AppConstant;
import com.example.food_store.domain.Role;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JdbcRoleRepository {
    private static final RowMapper<Role> ROLE_ROW_MAPPER = JdbcRoleRepository::mapRole;

    private final JdbcTemplate jdbcTemplate;

    public long count() {
        Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM roles", Long.class);
        return count == null ? 0L : count;
    }

    public Role findByName(String name) {
        List<Role> roles = jdbcTemplate.query(
                "SELECT id, name, description, create_day_time, update_day_time FROM roles WHERE name = ? LIMIT 1",
                ROLE_ROW_MAPPER,
                name);
        return roles.isEmpty() ? null : roles.get(0);
    }

    public Role save(Role role) {
        if (role.getId() != null && role.getId() > 0) {
            jdbcTemplate.update(
                    """
                            UPDATE roles
                            SET name = ?, description = ?, update_day_time = ?
                            WHERE id = ?
                            """,
                    role.getName(),
                    role.getDescription(),
                    now(),
                    role.getId());
            return role;
        }

        String timestamp = now();
        role.setCreateDayTime(timestamp);
        role.setUpdateDayTime(timestamp);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            var statement = connection.prepareStatement(
                    """
                            INSERT INTO roles (name, description, create_day_time, update_day_time)
                            VALUES (?, ?, ?, ?)
                            """,
                    new String[] { "id" });
            statement.setString(1, role.getName());
            statement.setString(2, role.getDescription());
            statement.setString(3, role.getCreateDayTime());
            statement.setString(4, role.getUpdateDayTime());
            return statement;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            role.setId(key.intValue());
        }
        return role;
    }

    private static Role mapRole(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();
        role.setId(rs.getInt("id"));
        role.setName(rs.getString("name"));
        role.setDescription(rs.getString("description"));
        role.setCreateDayTime(rs.getString("create_day_time"));
        role.setUpdateDayTime(rs.getString("update_day_time"));
        return role;
    }

    private static String now() {
        return java.time.LocalDateTime.now().format(AppConstant.formatter);
    }
}
