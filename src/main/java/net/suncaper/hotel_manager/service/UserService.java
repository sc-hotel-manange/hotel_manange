package net.suncaper.hotel_manager.service;

import net.suncaper.hotel_manager.domain.H_User;
import net.suncaper.hotel_manager.domain.H_HotelExample;
import net.suncaper.hotel_manager.domain.H_Hotel;
import net.suncaper.hotel_manager.domain.H_UserExample;
import net.suncaper.hotel_manager.mapper.H_UserMapper;
import net.suncaper.hotel_manager.mapper.H_UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private H_UserMapper userMapper;

    public List<User> listUser() {
        return userMapper.selectByExample(new UserExample());
    }

    public Integer saveUser(User user) {
        if(user.getId() == null) {
            return userMapper.insert(user);
        } else {
            return userMapper.updateByPrimaryKey(user);
        }

    }

    public User findUserByPrimaryKey(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public void deleteUserById(String id) {
        userMapper.deleteByPrimaryKey(id);
    }
}
