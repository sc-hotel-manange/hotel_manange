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

    @RequestMapping("/register")
    public String register(@RequestParam(value = "a_account") String a_account,
                           @RequestParam(value = "a_password") String a_password,
                           @RequestParam(value = "hotel_id") int hotel_id,
                           @RequestParam(value = "a_permission") String a_permission) {
        int a_id = adminService.getIdByAccount(a_account);
        if(a_id == -1) {
            H_Admin h_admin = new H_Admin();
            h_admin.setaAccount(a_account);
            h_admin.setaPassword(a_password);
            h_admin.setHotelId(hotel_id);
            h_admin.setaPermission(a_permission);
            adminService.insertAdmin(h_admin);
        }else {
            return "admin/register";
        }
        return "redirect:/admin/adminList";
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
                        HttpServletRequest request,
                        Model model){
        Integer a_id = adminService.getIdByAccountAndPwd(a_account,a_password);

        if(a_id == -1){
            model.addAttribute("no",false);
            return "redirect:/admin/";
        }
        else {
            Session a = new Session(a_id);
            request.getSession().setAttribute("a_id",new Session(a_id));
            return  "redirect:/admin/hotelList";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("a_id");

        return "redirect:/admin/";
    }

    //管理员列表，区别超级管理员和普通酒店管理员
    @RequestMapping("/adminList")
    public ModelAndView adminList(HttpServletRequest request) {
        Session session = (Session)request.getSession().getAttribute("a_id");

        ModelAndView mav = new ModelAndView("admin/adminList");
        mav.addObject("adminList", adminService.getAdminList(session.getId()));
        mav.addObject("a_permission", Integer.parseInt(adminService.getAdminInfo(session.getId()).getaPermission()));

        return mav;
    }

    //管理员信息
    @RequestMapping("/adminInfo")
    public String adminInfo(@RequestParam(value = "a_id") int a_id, Model model) {
        model.addAttribute("adminInfo", adminService.getAdminInfo(a_id));
        model.addAttribute("a_permission", Integer.parseInt(adminService.getAdminInfo(a_id).getaPermission()));
        return "admin/adminInfo";
    }

    //更新管理员信息
    @RequestMapping("/adminUpdate")
    public ModelAndView adminUpdate(@RequestParam(value = "a_id") int a_id,
                                    @RequestParam(value = "hotel_id") int hotel_id,
                                    @RequestParam(value = "a_account") String a_account,
                                    @RequestParam(value = "a_password") String a_password,
                                    @RequestParam(value = "a_permission") String a_permission) {

        H_Admin h_admin = new H_Admin();
        h_admin.setaId(a_id);
        h_admin.setHotelId(hotel_id);
        h_admin.setaAccount(a_account);
        h_admin.setaPassword(a_password);
        h_admin.setaPermission(a_permission);
        adminService.updateInfo(h_admin);

        ModelAndView mav = new ModelAndView("redirect:/admin/adminInfo?a_id=" + a_id);
        mav.addObject("adminInfo", h_admin);

        return mav;
    }

    @RequestMapping("/adminSearch")
    public ModelAndView adminSearch(@RequestParam(value = "content") String content) {
        ModelAndView mav = new ModelAndView("admin/adminList");
        mav.addObject("adminList", adminService.searchAdmin(content));

        return mav;
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
        mav.addObject("users", adminService.searchUser(content));

        return mav;
    }

}

