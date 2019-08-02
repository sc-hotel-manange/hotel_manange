package net.suncaper.hotel_manager.controller;

import net.suncaper.hotel_manager.domain.H_Order;
import net.suncaper.hotel_manager.domain.Session;
import net.suncaper.hotel_manager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserOrderController {
    @Autowired
    OrderService orderService;

    //用户自己的订单列表
    @RequestMapping("/listOrder")
    public ModelAndView listOrder(HttpServletRequest request){
        Session session = (Session)request.getSession().getAttribute("u_id");
        int u_id = session.getId();

        ModelAndView mav = new ModelAndView("user/orderList");
        List<H_Order> h_orders = orderService.listOrder(u_id);

        mav.addObject("h_orders", h_orders);
        return mav;
    }

    //用户的订单详情
    @RequestMapping("/orderInfo")

    public ModelAndView orderInfo(@RequestParam(value = "o_id") int o_id){
        ModelAndView mav = new ModelAndView("user/orderInfo");
        mav.addObject("h_order", orderService.orderInfo(o_id));
        return mav;
    }

}

