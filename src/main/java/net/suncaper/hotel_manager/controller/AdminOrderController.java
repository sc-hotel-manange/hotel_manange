package net.suncaper.hotel_manager.controller;

import net.suncaper.hotel_manager.domain.H_Order;
import net.suncaper.hotel_manager.domain.H_Room;
import net.suncaper.hotel_manager.domain.H_Roomtype;
import net.suncaper.hotel_manager.service.OrderService;
import net.suncaper.hotel_manager.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminOrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    RoomTypeService roomTypeService;

    //展示所有订单列表   返回订单List
    @GetMapping("/orderList")
    public ModelAndView listOrder() {
        ModelAndView mav = new ModelAndView("admin/orderList");
        List<H_Order> h_orders = orderService.listOrder();

        mav.addObject("h_orders", h_orders);
        return mav;
    }

    //根据订单号搜索订单
    @RequestMapping("/orderSearch")
    public ModelAndView orderSearch(@RequestParam(value = "content") String content) {
        ModelAndView mav = new ModelAndView("admin/orderList");
        mav.addObject("h_orders", orderService.orderSearch(Integer.parseInt(content)));

        return mav;
    }

    //管理员处理退房
    @PutMapping("/order")
    public String updateOrder(@RequestParam(value = "o_id") H_Order h_order) {
        orderService.finishOrder(h_order);
        return "orderInfo";
    }

}
