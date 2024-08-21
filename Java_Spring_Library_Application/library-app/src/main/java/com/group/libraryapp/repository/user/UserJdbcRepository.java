package com.group.libraryapp.repository.user;

import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveUser(String name, Integer age) {
        String sql = "INSERT INTO user (name, age) VALUES (?, ?)";
        jdbcTemplate.update(sql, name, age);
    }

    public List<UserResponse> getUsers() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, new RowMapper<UserResponse>() {
            // 아래는 sql의 결과로 담겨 온 유저 정보를 UserResponse로 바꿔주는 역할
            @Override
            public UserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                return new UserResponse(id, name, age);
            }
        });
    }


    public boolean isUserNotExist(long id) {
        // 데이터 존재 확인
        String exists = "SELECT * FROM user WHERE id = ?";
        // 만약 조회했는데 유저가 없다면 list에 0이 들어가지 않음. 즉 비어있는 리스트가 오게됨. -> isEmpty()가 true == 비어있음 == 유저 없음
        return jdbcTemplate.query(exists, (rs, rowNum) -> 0, id).isEmpty();
    }

    public void updateName(String name, long id) {
        String sql = "UPDATE user SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, id);
    }

    public boolean isUserNotExist(String name) {
        // 데이터 존재 확인
        String exists = "SELECT * FROM user WHERE name = ?";
        // 만약 조회했는데 유저가 없다면 list에 0이 들어가지 않음. 즉 비어있는 리스트가 오게됨. -> isEmpty()가 true == 비어있음 == 유저 없음
        return jdbcTemplate.query(exists, (rs, rowNum) -> 0, name).isEmpty();
    }

    public void deleteUser(String name) {
        String sql = "DELETE FROM user WHERE name = ?";
        jdbcTemplate.update(sql,name);
    }
}
