package org.example.mapper;

/**
 * @author simple
 */

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.domain.User;

@Mapper
public interface UserMapper {
    @Insert("insert into users(id,name,age) values(#{id},#{name},#{age})")
    void insertUser(User user);

    @Select("select * from users where name = #{name}")
    User user(String name);
}
