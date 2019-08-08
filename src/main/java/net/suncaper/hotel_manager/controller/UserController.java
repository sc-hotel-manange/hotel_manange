package net.suncaper.hotel_manager.controller;

import net.suncaper.hotel_manager.domain.H_User;
import net.suncaper.hotel_manager.domain.Session;
import net.suncaper.hotel_manager.service.HotelService;
import net.suncaper.hotel_manager.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    public static String code;

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

    @RequestMapping("/aboutUs")
    public String aboutUs() {
        return "user/aboutUs";
    }

    @RequestMapping("/foodInfo")
    public String foodInfo() {
        return "user/foodInfo";
    }

    @RequestMapping("/login")
    public String login(@PathParam(value = "u_account") String u_account,
                        @PathParam(value = "u_password") String u_password,
                        HttpServletRequest request,
                        Model model){
        System.out.println(u_account);
        System.out.println(u_password);
        int u_id = userService.getIdByAccountAndPwd(u_account,u_password);

        if(u_id == -1){
            model.addAttribute("no",true);        //用于账号或密码错误时显示反馈
            return "user/login";
        }
        else {

            Session u = new Session(u_id);
           request.getSession().setAttribute("u_id",new Session(u_id));         // 这里设置session

            return  "redirect:/user/index";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("u_id");

        return "redirect:/user/index";
    }

    @RequestMapping("/register")
    public String register(@PathParam(value = "u_name")String u_name,
                           @PathParam(value = "u_tel") String u_tel,
                           @PathParam(value = "u_nickName") String u_nickName,
                           @PathParam(value = "u_account") String u_account,
                           @PathParam(value = "u_password") String u_password,
                           @PathParam(value = "u_idNumber") String u_idNumber,
                           @PathParam(value = "u_email") String u_email,
                           Model model) {
        int u_id = userService.getIdByAccountOrEmail(u_account, u_email);
        if(u_id == -1) {
            H_User h_user = new H_User();
            h_user.setuName(u_name);
            h_user.setuTel(u_tel);
            h_user.setuNickname(u_nickName);
            h_user.setuAccount(u_account);
            h_user.setuPassword(u_password);
            h_user.setuIdnumber(u_idNumber);
            h_user.setuEmail(u_email);

            userService.insertUser(h_user);
        }else {
            model.addAttribute("no",true);
            return "user/register";
        }
        return "user/login";
    }

    //用户信息
    @RequestMapping("/userInfo")
    public ModelAndView userInfo(HttpServletRequest request) {
        Session session = (Session)request.getSession().getAttribute("u_id");
        ModelAndView mav = new ModelAndView("user/userInfo");
        mav.addObject("userInfo", userService.getUserInfo(session.getId()));

        return mav;
    }

    @RequestMapping("/updateInfo")
    public String postAlterInfo(@PathParam(value = "u_nickName") String u_nickName,
                                @PathParam(value = "u_account") String u_account,
                                @PathParam(value = "u_name")String u_name,
                                @PathParam(value = "u_tel") String u_tel,
                                @PathParam(value = "u_password") String u_password,
                                @PathParam(value = "u_idNumber") String u_idNumber,
                                @PathParam(value = "u_email") String u_email,
                                HttpServletRequest request){
        Session session = (Session)request.getSession().getAttribute("u_id");
        int u_id = session.getId();
        H_User h_user = new H_User();
        h_user.setuId(u_id);
        h_user.setuAccount(u_account);
        h_user.setuName(u_name);
        h_user.setuTel(u_tel);
        h_user.setuNickname(u_nickName);
        h_user.setuPassword(u_password);
        h_user.setuIdnumber(u_idNumber);
        h_user.setuEmail(u_email);
        userService.updateInfo(h_user);
        return "redirect:/user/userInfo";        //修改完信息后重新跳转到个人信息界面
    }

    //发送重置密码的邮件
    @RequestMapping("/sendEmail")
    public ModelAndView sendEmail(@RequestParam(value = "email") String email) {
        ModelAndView mav = new ModelAndView();
        //验证该邮箱是否存在
        H_User h_user = userService.getUserInfo(email);
        if(h_user == null) {
            mav.addObject("failed", false);
            return mav;
        }
        code = RandomStringUtils.randomAlphanumeric(4);

        userService.sendMail(email, code);
        mav.setViewName("user/verify");
        mav.addObject("code", code);
        mav.addObject("email", email);

        return mav;
    }

    //判断用户输入验证码是否正确
    @RequestMapping("/verify")
    public ModelAndView verify(@RequestParam(value = "content") String content,
                               @RequestParam(value = "email") String email) {
        ModelAndView mav = new ModelAndView();
        if(userService.verify(code, content)){
            mav.setViewName("user/resetPassword");   //跳转到resetPassword界面
            mav.addObject("email", email);
            return mav;
        }else {
            mav.setViewName("user/verify");     //跳转到verify界面
            mav.addObject("failed", false);
            mav.addObject("email", email);
            return mav;
        }
    }
    @RequestMapping("forgetPassword")       //点击忘记密码跳转！！！！！！！！！
    public ModelAndView resetPassword( ) {

        return new ModelAndView("user/forgetPassword");
    }
    @RequestMapping("resetPassword")                //这个页面验证完成后重置了
    public ModelAndView resetPassword(@RequestParam(value = "password") String password,
                                      @RequestParam(value = "email") String email) {
        userService.resetPassword(password, email);
        return new ModelAndView("user/login");
    }
}
