package net.suncaper.hotel_manager.security;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object u_id = request.getSession().getAttribute("u_id");
        Object a_id = request.getSession().getAttribute("a_id");
//        System.out.println(u_id);
        if (a_id != null){
            return true;
        }
        else if(1==1){
            return true;
        }
        else {
            response.sendRedirect("/admin/");
            return false;
        }
    }
}
