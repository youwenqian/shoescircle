package com.shoes.scarecrow.persistence.service;

import com.shoes.scarecrow.persistence.domain.User;
import com.shoes.scarecrow.persistence.domain.UserCondition;
import com.shoes.scarecrow.persistence.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangyucheng
 * @description
 * @create 2018/2/12 10:17
 */
@Service
public class UserService {

    @Autowired
    protected UserMapper userMapper;


    public int saveUser(User user){
        return userMapper.insert(user);
    }


    public User getUser(String userName, String passWord, int key) {
        return userMapper.getUserByNameAndPassword(userName,passWord,key);
    }

    public int queryCountByCondition(UserCondition userCondition){
        return userMapper.queryCountByCondition(userCondition);
    }

    public List<User> queryByCondition(UserCondition userCondition){
        return userMapper.queryByCondition(userCondition);
    }

    public User queryById(int id){
        return userMapper.queryById(id);
    }

    public User queryByUserName(String userName){
        return userMapper.queryByUserName(userName);
    }

    public int updateUser(User user){
        return userMapper.updateUserById(user);
    }

    public int delUser(String id){
        return userMapper.delById(id);
    }
}