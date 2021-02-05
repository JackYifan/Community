package com.isee.community.mapper;

import com.isee.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    //当参数是一个类时可以用#{}获得属性
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,avatar_url) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    public void insert(User user);

    //参数不是类需要用@Param指定
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Long id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name = #{name},token = #{token},gmt_modified = #{gmtModified},avatar_url = #{avatarUrl} where id = #{id}")
    void update(User dbUser);
    /**
     *     private Long id;
     *     private String accountId;
     *     private String name;
     *     private String token;
     *     private Long gmtCreate;
     *     private Long gmtModified;
     *     private String bio;
     *     private String avatarUrl;
     */
}
