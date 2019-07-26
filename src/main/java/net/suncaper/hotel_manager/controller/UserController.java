package net.suncaper.hotel_manager.controller;


import net.suncaper.hotel_manager.domain.H_User;
import net.suncaper.hotel_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
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
        return "login";
    }
    @RequestMapping("/hotelinfo")   //酒店信息界面
    public String hotelinfo() {
        
        return "hotelList";
    }
    @RequestMapping("/userinfo")  //用户信息
    public String userinfo(HttpServletRequest request,Model model) {
        Cookie[] cookies =  request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("u_id")){        //检测cookie名称是否等于u_id
//                    return cookie.getValue();
//                    System.out.println(cookie.getValue());
                    int u_id = Integer.parseInt(cookie.getValue());
                    model.addAttribute("userInfo",userService.getUserInfo(u_id));
                }
            }
        }
        return "userInfo";
    }
    @RequestMapping("/starter")   //首页
    public String starter() {
        return "starter";
    }
    @RequestMapping("/alterinfo")   //修改个人信息界面
    public String alterinfo() {
        return "alterInfo";
    }
    @RequestMapping("/term")   //条款
    public String term() {
        return "term";
    }
    @RequestMapping("/login")  //
    public String login(@PathParam(value = "u_account") String u_account,
                        @PathParam(value = "u_password") String u_password,
                        HttpServletResponse response,
                        Model model){
        int u_id = userService.getIdByAccountAndPwd(u_account,u_password);
//        System.out.print(u_account+u_password);
        if(u_id == -1){
            return "login";
        }
        else {
            Cookie cookie = new Cookie("u_id", String.valueOf(u_id));         //这里设置cookie
            response.addCookie(cookie);
//            model.addAttribute("id", u_id);
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

