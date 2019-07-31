package net.suncaper.hotel_manager.controller;


import net.suncaper.hotel_manager.domain.H_User;
import net.suncaper.hotel_manager.domain.UserSession;
import net.suncaper.hotel_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping("/")   //登陆
    public String home() {
        return "user/login";
    }

    @RequestMapping("/userinfo")  //用户信息
    public String userinfo(HttpServletRequest request, Model model) {
        UserSession User = (UserSession)request.getSession().getAttribute("u_id");     //这里使用session
        model.addAttribute("userInfo",userService.getUserInfo(User.getId()));
        return "userInfo";
    }

    @GetMapping("/alterinfo")  //用户信息
    public String getAlterInfo(HttpServletRequest request,Model model) {

        UserSession User = (UserSession)request.getSession().getAttribute("u_id");      //这里现在是使用session
        int u_id = User.getId();
        model.addAttribute("userInfo",userService.getUserInfo(u_id));
        return "alterinfo";
    }

    @RequestMapping("/InsertAlterinfo")
    public String postAlterInfo(@PathParam(value = "u_nickName") String u_nickName,
                                @PathParam(value = "u_account") String u_account,
                                @PathParam(value = "u_name")String u_name,
                                @PathParam(value = "u_tel") String u_tel,
                                @PathParam(value = "u_password") String u_password,
                                @PathParam(value = "u_idNumber") String u_idNumber,
                                HttpServletRequest request){
        UserSession User = (UserSession)request.getSession().getAttribute("u_id");      //这里现在是使用session
        int u_id = User.getId();
        H_User h_user = new H_User();
        h_user.setuId(u_id);
        h_user.setuAccount(u_account);
        h_user.setuName(u_name);
        h_user.setuTel(u_tel);
        h_user.setuNickname(u_nickName);
        h_user.setuPassword(u_password);
        h_user.setuIdnumber(u_idNumber);
        userService.updateInfo(h_user);
        return "redirect:userinfo";        //修改完信息后重新跳转到个人信息界面
    }

    @RequestMapping("/starter")   //首页
    public String starter() {
        return "starter";
    }

    @RequestMapping("/term")   //条款
    public String term() {
        return "term";
    }

    @RequestMapping("/login")
    public String login(@PathParam(value = "u_account") String u_account,
                        @PathParam(value = "u_password") String u_password,
                        HttpServletResponse response,
                        HttpServletRequest request,
                        Model model){
        int u_id = userService.getIdByAccountAndPwd(u_account,u_password);

        if(u_id == -1){
            return "login";
        }
        else {

            UserSession u = new UserSession(u_id);
            request.getSession().setAttribute("u_id",new UserSession(u_id));

            return  "starter";
        }
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
//            System.out.println("账号已存在");
            return "register";
        }
//        System.out.println("注册成功");
        return "login";
    }


}

