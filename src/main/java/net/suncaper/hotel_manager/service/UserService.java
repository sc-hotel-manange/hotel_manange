package net.suncaper.hotel_manager.service;

import net.suncaper.hotel_manager.domain.H_User;
import net.suncaper.hotel_manager.mapper.H_UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private H_UserMapper h_userMapper;

    public int getIdByAccount(String u_account,String u_password){
        H_User h_user = h_userMapper.selectByAccount(u_account,u_password);
        return h_user!=null ? h_user.getuId() : -1;
    }
}
