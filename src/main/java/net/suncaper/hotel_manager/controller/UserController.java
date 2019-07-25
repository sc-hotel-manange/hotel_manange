package net.suncaper.hotel_manager.controller;

import net.suncaper.hotel_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(@PathParam(value = "u_account") String u_account,
                        @PathParam(value = "u_password") String u_password){
        int u_id = userService.getIdByAccount(u_account,u_password);
        return u_id!=-1 ? "starter" : "login";
    }

}