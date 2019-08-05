package net.suncaper.hotel_manager.controller;


import net.suncaper.hotel_manager.domain.H_Admin;
import net.suncaper.hotel_manager.domain.H_User;
import net.suncaper.hotel_manager.domain.Session;
import net.suncaper.hotel_manager.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;


    @RequestMapping("")   //登录
    public String home() {
        return "admin/login";
    }

    @RequestMapping("/registerPage")  //注册
    public String register() {
        return "admin/register";
    }

    @RequestMapping("/term")   //条款
    public String term() {
        return "admin/term";
    }

    @RequestMapping("/starter")   //首页
    public String starter() {
        return "admin/starter";
    }

    @RequestMapping("/login")
    public String login(@PathParam(value = "a_account") String a_account,
                        @PathParam(value = "a_password") String a_password,
                        HttpServletResponse response,
                        HttpServletRequest request,
                        Model model){
        Integer a_id = adminService.getIdByAccountAndPwd(a_account,a_password);

        if(a_id == -1){
            return "redirect:/admin/";
        }
        else {

            Session a = new Session(a_id);
            request.getSession().setAttribute("a_id",new Session(a_id));
            return  "admin/starter";
        }
    }

//    @RequestMapping("/register")
//    public String register(@PathParam(value = "a_name")String a_name,
//                           @PathParam(value = "a_tel") String a_tel,
//                           @PathParam(value = "a_nickName") String a_nickName,
//                           @PathParam(value = "a_account") String a_account,
//                           @PathParam(value = "a_password") String a_password,
//                           @PathParam(value = "a_idNumber") String a_idNumber) {
//        int a_id = adminService.getIdByAccount(a_account);
//        if(a_id == -1) {
//            H_Admin h_admin = new H_Admin();
//            h_admin.setuName(a_name);
//            h_admin.setuTel(a_tel);
//            h_admin.setuNickname(a_nickName);
//            h_admin.setuAccount(a_account);
//            h_admin.setuPassword(a_password);
//            h_admin.setuIdnumber(a_idNumber);
//
//            adminService.insertAdmin(h_admin);
//        }else {
//            return "admin/register";
//        }
//        return "admin/login";
//    }

    //管理员列表
    @RequestMapping("/adminList")
    public ModelAndView adminList() {
        ModelAndView mav = new ModelAndView("admin/adminList");
        mav.addObject("adminList", adminService.getAdminList());

        return mav;
    }

    @RequestMapping("/adminInfo")  //管理员信息
    public String adminInfo(HttpServletRequest request, Model model) {
        Session Admin = (Session)request.getSession().getAttribute("a_id");     //这里使用session
        model.addAttribute("adminInfo",adminService.getAdminInfo(Admin.getId()));
        return "admin/adminInfo";
    }

    @GetMapping("/alterinfo")  //修改管理员信息
    public String getAlterInfo(HttpServletRequest request,Model model) {

        Session Admin = (Session)request.getSession().getAttribute("a_id");      //这里现在是使用session
        int a_id = Admin.getId();
        model.addAttribute("adminInfo",adminService.getAdminInfo(a_id));
        return "admin/alterinfo";
    }

    @RequestMapping("/InsertAlterInfo")
    public String postAlterInfo(@PathParam(value = "a_password") String a_password,
                                HttpServletRequest request){
        Session Admin = (Session)request.getSession().getAttribute("a_id");      //这里现在是使用session
        int a_id = Admin.getId();
        H_Admin h_admin =  adminService.getAdminInfo(a_id);

        h_admin.setaPassword(a_password);

        adminService.updateInfo(h_admin);
        return "redirect:adminInfo";        //修改完信息后重新跳转到个人信息界面
    }

    @RequestMapping("/userList")
    public ModelAndView userList() {
        ModelAndView mav = new ModelAndView("admin/userList");
        mav.addObject("users", adminService.selectUser());
        return mav;
    }

    @RequestMapping("/userInfo")
    public ModelAndView userInfo(@RequestParam(value = "u_id", required = false) Integer u_id) {
        if(u_id == null) {
            return new ModelAndView("admin/userList");
        }
        ModelAndView mav = new ModelAndView("admin/userInfo");
        mav.addObject("user", adminService.getUserInfo(u_id));

        return mav;
    }

    @RequestMapping("/userUpdate")
    public String userUpdate(@PathParam(value = "u_id") int u_id,
                              @PathParam(value = "u_nickName") String u_nickName,
                              @PathParam(value = "u_account") String u_account,
                              @PathParam(value = "u_name")String u_name,
                              @PathParam(value = "u_tel") String u_tel,
                              @PathParam(value = "u_password") String u_password,
                              @PathParam(value = "u_idNumber") String u_idNumber,
                            Model model) {

        H_User h_user = new H_User();
        h_user.setuId(u_id);
        h_user.setuAccount(u_account);
        h_user.setuName(u_name);
        h_user.setuTel(u_tel);
        h_user.setuNickname(u_nickName);
        h_user.setuPassword(u_password);
        h_user.setuIdnumber(u_idNumber);
        adminService.updateInfo(h_user);

        model.addAttribute("checkUpdate", true);
        return "redirect:/admin/userInfo?u_id="+u_id;
    }

    //根据真实姓名搜索用户
    @RequestMapping("/userSearch")
    public ModelAndView userSearch(@RequestParam(value = "content") String content) {
        ModelAndView mav = new ModelAndView("admin/userList");
        mav.addObject("users", adminService.searchByName(content));

        return mav;
    }

}

