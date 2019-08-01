package net.suncaper.hotel_manager.service;

import net.suncaper.hotel_manager.domain.H_User;
import net.suncaper.hotel_manager.domain.H_UserExample;
import net.suncaper.hotel_manager.mapper.H_UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private H_UserMapper h_userMapper;

    public int getIdByAccountAndPwd(String u_account,String u_password){
        H_UserExample example = new H_UserExample();
        example.createCriteria().andUAccountEqualTo(u_account).andUPasswordEqualTo(u_password);

        List<H_User> h_users = h_userMapper.selectByExample(example);
        for(H_User h_user : h_users) {
            return h_user.getuId();
        }
        return -1;

//        H_User h_user = h_userMapper.selectByAccountAndPwd(u_account,u_password);
//        return h_user!=null ? h_user.getuId() : -1;
    }

    public int getIdByAccount(String u_account){
        H_UserExample example = new H_UserExample();
        example.createCriteria().andUAccountEqualTo(u_account);

        List<H_User> h_users = h_userMapper.selectByExample(example);
        for(H_User h_user : h_users) {
            return h_user.getuId();
        }
//        H_User h_user = h_userMapper.selectByAccount(u_account);
//        return h_user!=null ? h_user.getuId() : -1;
        return -1;
    }

    public void insertUser(H_User h_user) {
        h_userMapper.insert(h_user);
    }

    public H_User getUserInfo(int u_id){
        return h_userMapper.selectByPrimaryKey(u_id);
    }

//    public  int getUserIdByCookie(HttpServletRequest request){
//        Cookie[] cookies =  request.getCookies();
//        if(cookies != null){
//            for(Cookie cookie : cookies){
//                if(cookie.getName().equals("u_id")){        //检测cookie名称是否等于u_id
//                    int u_id = Integer.parseInt(cookie.getValue());
//                    return u_id;
//                }
//            }
//        }
//        return -1;
//    }

    public int updateInfo(H_User h_user){
        h_userMapper.updateByPrimaryKey(h_user);
        return 1;
    }
}
