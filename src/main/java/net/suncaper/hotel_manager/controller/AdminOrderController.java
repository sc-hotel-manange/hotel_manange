package net.suncaper.hotel_manager.controller;

import net.suncaper.hotel_manager.domain.H_Order;
import net.suncaper.hotel_manager.domain.H_Room;
import net.suncaper.hotel_manager.domain.H_Roomtype;
import net.suncaper.hotel_manager.domain.Session;
import net.suncaper.hotel_manager.service.AdminService;
import net.suncaper.hotel_manager.service.OrderService;
import net.suncaper.hotel_manager.service.RoomService;
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
    @Autowired
    RoomService roomService;
    @Autowired
    AdminService adminService;

    //展示所有订单列表   返回订单List
    @RequestMapping("/orderList")
    public ModelAndView orderList(HttpServletRequest request) {
        //获取登录管理员的hotel_id
        Session session = (Session)request.getSession().getAttribute("a_id");
        int hotel_id = adminService.getAdminInfo(session.getId()).getHotelId();

        ModelAndView mav = new ModelAndView("admin/orderList");
        List<H_Order> h_orders = orderService.hotelOrder(hotel_id);

        mav.addObject("h_orders", h_orders);
        return mav;
    }

    //展示订单详情
    @RequestMapping("/orderInfo")
    public ModelAndView orderInfo(@RequestParam(value = "o_id") int o_id) {
        ModelAndView mav = new ModelAndView("admin/orderInfo");
        mav.addObject("order", orderService.selectOrder(o_id));

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
    @RequestMapping("/leaveRoom")
    public ModelAndView leaveRoom(@RequestParam(value = "o_id") int o_id) {
        H_Order h_order = orderService.selectOrder(o_id);
        H_Room h_room = roomService.findRoom(h_order.getHotelId(), h_order.getrNumber());
        orderService.leaveRoom(h_room); //更新房间状态
        h_order = orderService.finishOrder(h_order); //更新订单状态

        ModelAndView mav = new ModelAndView("admin/orderInfo");
        mav.addObject("order", h_order);

        return mav;
    }

}
