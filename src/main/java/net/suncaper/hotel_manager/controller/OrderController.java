package net.suncaper.hotel_manager.controller;

import net.suncaper.hotel_manager.domain.H_Order;
import net.suncaper.hotel_manager.domain.H_Room;
import net.suncaper.hotel_manager.domain.H_Roomtype;
import net.suncaper.hotel_manager.domain.UserSession;
import net.suncaper.hotel_manager.service.OrderService;
import net.suncaper.hotel_manager.service.RoomService;
import net.suncaper.hotel_manager.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    RoomService roomService;
    @Autowired
    RoomTypeService roomTypeService;

    //下订单
    @PostMapping("/order")
    public ModelAndView placeOrder(@RequestParam(value = "rt_type") String rt_type,
                                   @RequestParam(value = "hotel_id") int hotel_id,
                                   @RequestParam(value = "o_checkin") Date o_checkin,
                                   @RequestParam(value = "o_checkout") Date o_checkout,
                                   @RequestParam(value = "o_tel") String o_tel,
                                   HttpServletRequest request) {

        UserSession userSession = (UserSession)request.getSession().getAttribute("u_id");     //这里使用session
        System.out.println("这是u_id: " + userSession.getId());

        System.out.println("这是hotelid: " + hotel_id);

        System.out.println("这是rt_type: " + rt_type);
        System.out.println("这是o_checkin: " + o_checkin);
        System.out.println("这是o_checkout: " + o_checkout);
        System.out.println("这是o_tel: " + o_tel);

        //根据房型分配一个房间
        H_Room h_room = roomService.findRoom(rt_type, hotel_id);
        //查找该房型
        H_Roomtype h_roomtype = roomTypeService.findRoomTypeByRtAndHid(rt_type, hotel_id);
        //更新该房间状态
        roomService.updateRoom(h_room.getrId());
        //减少该房型库存
        roomTypeService.reduceStock(h_roomtype.getRtId());

        H_Order h_order = new H_Order();
        h_order.setrId(h_room.getrId());
        h_order.setuId(userSession.getId());
        h_order.setoPrice(h_roomtype.getRtPrice());
        h_order.setoOrdertime(new Date());
        h_order.setoCheckin(o_checkin);
        h_order.setoCheckout(o_checkout);
        h_order.setoStatus("0"); //未入住状态
        h_order.setoTel(o_tel);
        orderService.placeOrder(h_order);

        ModelAndView mav = new ModelAndView("orderList");
        mav.addObject("h_order", h_order);

        return mav;
    }

    //展示用户订单列表
    @GetMapping("/orderList")
    public ModelAndView listOrder(HttpServletRequest request) {
        UserSession userSession = (UserSession)request.getSession().getAttribute("u_id");
        ModelAndView mav = new ModelAndView("orderList");
        List<H_Order> h_orders = orderService.listOrder(userSession.getId());

        mav.addObject("h_orders", h_orders);
        return mav;
    }

    //(管理员)修改订单状态
    @PutMapping("/order")
    public String updateOrder(@RequestParam(value = "o_id") H_Order h_order) {
        orderService.updateOrder(h_order);
        return "orderInfo";
    }
}
