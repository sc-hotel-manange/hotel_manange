package net.suncaper.hotel_manager.security;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url= request.getRequestURL().toString();
        Object u_id = request.getSession().getAttribute("u_id");
        Object a_id = request.getSession().getAttribute("a_id");
//        System.out.println(u_id);
        if (a_id != null && url.indexOf("/admin")!=-1){//是管理员且访问管理员界面，允许登陆
            return true;
        }
        else if(a_id == null && url.indexOf("/admin")!=-1){//不是管理员且访问管理员界面，跳转至管理员登陆界面
            response.sendRedirect("/admin/");
            return false;
        }
        else if(u_id !=null && url.indexOf("/admin")==-1){//是用户且访问用户界面，允许登陆
            return true;
        }
        else if(u_id ==null && url.indexOf("/admin")==-1){//不是用户且访问用户界面，跳转至用户登陆界面
            response.sendRedirect("/user/loginPage");
            return false;
        }
        else{
            return false;
        }
    }
}


