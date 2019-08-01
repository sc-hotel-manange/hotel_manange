package net.suncaper.hotel_manager.controller;

import net.suncaper.hotel_manager.domain.Session;
import net.suncaper.hotel_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String Home(){return "user/login";}

    @RequestMapping("/registerPage")  //注册
    public String register() {
        return "user/register";
    }

    @RequestMapping("/index")   //首页
    public String starter() {
        return "user/index";
    }

    @RequestMapping("/login")
    public String login(@PathParam(value = "u_account") String u_account,
                        @PathParam(value = "u_password") String u_password,
                        HttpServletResponse response,
                        HttpServletRequest request,
                        Model model){
        int u_id = userService.getIdByAccountAndPwd(u_account,u_password);

        if(u_id == -1){
            return "redirect:/user/";
        }
        else {

            Session u = new Session(u_id);
            request.getSession().setAttribute("u_id",new Session(u_id));
            return  "user/index";
        }
    }
}
