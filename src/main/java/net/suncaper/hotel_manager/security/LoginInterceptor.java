package net.suncaper.hotel_manager.security;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object u_id = request.getSession().getAttribute("u_id");
        System.out.println(u_id);
        if (u_id != null){
            return true;
        }
        else {
            response.sendRedirect("/user/");
            return false;
        }
    }
}
