package net.suncaper.hotel_manager.controller;


import net.suncaper.hotel_manager.domain.H_User;
import net.suncaper.hotel_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping("/")   //登陆
    public String home() {
        return "login";
    }
    @RequestMapping("/userinfo")  //用户信息
    public String userinfo() {
        return "userinfo";
    }
    @RequestMapping("/starter")   //首页
    public String starter() {
        return "starter";
    }
    @RequestMapping("/alterinfo")   //修改个人信息界面
    public String alterinfo() {
        return "alterinfo";
    }
    @RequestMapping("/term")   //条款
    public String term() {
        return "term";
    }
    @RequestMapping("/login")  //
    public String login(@PathParam(value = "u_account") String u_account,
                        @PathParam(value = "u_password") String u_password,
                        Model model){
        int u_id = userService.getIdByAccountAndPwd(u_account,u_password);
        System.out.print(u_account+u_password);

        model.addAttribute("id", u_id);
        return u_id!=-1 ? "starter" : "login";
    }

    @RequestMapping("/registerPage")
    public String register() {
        return "register";
    }

    @RequestMapping("/register")
    public String register(@PathParam(value = "u_name")String u_name,
                           @PathParam(value = "u_tel") String u_tel,
                           @PathParam(value = "u_nickName") String u_nickName,
                           @PathParam(value = "u_account") String u_account,
                           @PathParam(value = "u_password") String u_password,
                           @PathParam(value = "u_idNumber") String u_idNumber) {
        int u_id = userService.getIdByAccount(u_account);
        System.out.print(u_account);
        if(u_id == -1) {
            H_User h_user = new H_User();
            h_user.setuName(u_name);
            h_user.setuTel(u_tel);
            h_user.setuNickname(u_nickName);
            h_user.setuAccount(u_account);
            h_user.setuPassword(u_password);
            h_user.setuIdnumber(u_idNumber);

            userService.insertUser(h_user);
        }else {
            System.out.println("账号已存在");
            return "register";
        }

        return "login";
    }

    @RequestMapping("info")
    @ResponseBody
    public H_User getUserInfo(int u_id){
        H_User h_user = userService.getUserInfo(u_id);
        return h_user;
    }

}

