package com.omni.code.mapper;
import com.omni.code.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users")
    List<User> getAllUsers();

    @Select("SELECT * FROM users WHERE user_id = #{userId}")
    User getUserById(Integer userId);

    @Insert("INSERT INTO users (name, email, password, address, role, created_at) " +
            "VALUES (#{name}, #{email}, #{password}, #{address}, #{role}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void createUser(User user);

    @Update("UPDATE users SET name = #{name}, email = #{email}, password = #{password}, " +
            "address = #{address}, role = #{role} WHERE user_id = #{userId}")
    void updateUser(User user);

    @Delete("DELETE FROM users WHERE user_id = #{userId}")
    void deleteUser(Integer userId);

    @Insert("INSERT INTO users (name, email, password, role,address, created_at) VALUES (#{name}, #{email}, #{password}, #{role},#{address}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void registerUser(User user);

    @Select("SELECT * FROM users WHERE email = #{email}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "createdAt", column = "created_at")
    })
    User findUserByEmail(String email);

    // Check if user exists by email
    @Select("SELECT COUNT(*) FROM users WHERE email = #{email}")
    boolean existsByEmail(String email);

    // Get user by email
    @Select("SELECT * FROM users WHERE email = #{email}")
    User findByEmail(String email);

    // Update user password
    @Update("UPDATE users SET password = #{password} WHERE email = #{email}")
    void updatePassword(@Param("email") String email, @Param("password") String password);
}
