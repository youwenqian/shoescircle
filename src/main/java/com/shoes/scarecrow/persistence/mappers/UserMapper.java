package com.shoes.scarecrow.persistence.mappers;

import com.shoes.scarecrow.persistence.domain.User;
import com.shoes.scarecrow.persistence.domain.UserCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userMapper")
public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    User getUserByNameAndPassword(@Param("userName") String userName, @Param("passWord") String passWord,@Param("userType") int key);

    int queryCountByCondition(UserCondition userCondition);

    List<User> queryByCondition(UserCondition userCondition);

    User queryById(int id);

    User queryByUserName(@Param("userName") String userName);

    int updateUserById(User user);

    int delById(String id);
}