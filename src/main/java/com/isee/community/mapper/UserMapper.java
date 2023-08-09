package com.isee.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isee.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    //当参数是一个类时可以用#{}获得属性
//    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,avatar_url,password) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl},#{password})")
//    public void insert(User user);

    //参数不是类需要用@Param指定
    // @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    // @Select("select * from user where id = #{id}")
    User findById(@Param("id") Long id);

    // @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    // @Select("select * from user where name = #{name}")
    User findByUsername(@Param("name") String name);

    //@Update("update user set name = #{name},token = #{token},gmt_modified = #{gmtModified},avatar_url = #{avatarUrl} where id = #{id}")
    void update(User dbUser);


}
