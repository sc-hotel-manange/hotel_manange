package net.suncaper.hotel_manager.controller;

import net.suncaper.hotel_manager.domain.H_User;
import net.suncaper.hotel_manager.domain.Session;
import net.suncaper.hotel_manager.service.HotelService;
import net.suncaper.hotel_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    HotelService hotelService;
    @Autowired
    private UserService userService;

    @RequestMapping("")
    public String index(){
        return "redirect:/user/index";
    }
    
    @RequestMapping("/loginPage")
    public String loginPage(){return "user/login";}

    @RequestMapping("/registerPage")  //注册
    public String registerPage() {
        return "user/register";
    }

    @RequestMapping("/index")   //首页
    public ModelAndView starter() {

        ModelAndView mav = new ModelAndView("user/index");
        mav.addObject("fiveStarHotels", hotelService.selectFiveStarHotels());
        mav.addObject("topRatedHotels", hotelService.selectTopRatedHotels());
        return mav;
    }

    @RequestMapping("/login")
    public String login(@PathParam(value = "u_account") String u_account,
                        @PathParam(value = "u_password") String u_password,
                        HttpServletResponse response,
                        HttpServletRequest request,
                        Model model){
        int u_id = userService.getIdByAccountAndPwd(u_account,u_password);

        if(u_id == -1){
            return "redirect:/user/login";
        }
        else {

            Session u = new Session(u_id);
           request.getSession().setAttribute("u_id",new Session(u_id));         // 这里设置cookie

            return  "redirect:/user/index";
        }
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
            return "user/register";
        }
        return "user/login";
    }
}
