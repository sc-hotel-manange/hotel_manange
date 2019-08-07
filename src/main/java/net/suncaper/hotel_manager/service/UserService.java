package net.suncaper.hotel_manager.service;

import net.suncaper.hotel_manager.domain.H_User;
import net.suncaper.hotel_manager.domain.H_UserExample;
import net.suncaper.hotel_manager.mapper.H_UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private H_UserMapper h_userMapper;
    @Autowired
    private JavaMailSender mailSender;

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

    public int getIdByAccountOrEmail(String u_account, String u_email){
        H_UserExample example = new H_UserExample();
        example.createCriteria().andUAccountEqualTo(u_account);
        example.or().andUEmailEqualTo(u_email);

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


    //根据用户id找用户
    public H_User getUserInfo(int u_id){
        return h_userMapper.selectByPrimaryKey(u_id);
    }

    //根据邮箱找用户
    public H_User getUserInfo(String email) {
        H_UserExample example = new H_UserExample();
        example.createCriteria().andUEmailEqualTo(email);

        return h_userMapper.selectByExample(example).get(0);
    }

    public int updateInfo(H_User h_user){
        h_userMapper.updateByPrimaryKey(h_user);
        return 1;
    }

    //发送邮件
    public void sendMail(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1026960839@qq.com");
        message.setTo(toEmail);
        message.setSubject("重置密码验证码");
        message.setText("用于重置密码的验证码为：" + code);

        mailSender.send(message);
    }

    //验证码
    public boolean verify(String code, String content) {
        if(code.equals(content))
            return true;
        else
            return false;
    }

    //重置密码
    public void resetPassword(String password, String email) {
        H_User h_user = getUserInfo(email);
        h_user.setuPassword(password);
        updateInfo(h_user);
    }
}
