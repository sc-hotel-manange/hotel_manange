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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    public String placeOrder(@RequestParam(value = "rt_type") String rt_type,
                                   @RequestParam(value = "hotel_id") int hotel_id,
                                   @RequestParam(value = "o_checkin") Date o_checkin,
                                   @RequestParam(value = "o_checkout") Date o_checkout,
                                   @RequestParam(value = "o_tel") String o_tel,
                                   HttpServletRequest request) {

        UserSession userSession = (UserSession)request.getSession().getAttribute("u_id");     //这里使用session

        //根据房型分配一个房间
        H_Room h_room = roomService.findRoom(rt_type, hotel_id);
        //查找该房型
        H_Roomtype h_roomtype = roomTypeService.findRoomType(rt_type, hotel_id);
        //更新该房间状态
        roomService.updateRoom(h_room.getrNumber(), hotel_id);
        //减少该房型库存
        roomTypeService.reduceStock(h_roomtype.getRtId());

        //计算用户住的天数
        int stay = (int)(o_checkout.getTime()-o_checkin.getTime()) / (1000*3600*24);

        H_Order h_order = new H_Order();
        h_order.setrNumber(h_room.getrNumber());
        h_order.setuId(userSession.getId());
        h_order.setHotelId(hotel_id);
        h_order.setoPrice(h_roomtype.getRtPrice() * stay); //总价 = 单价 * 天数
        h_order.setoOrdertime(new Date());
        h_order.setoCheckin(o_checkin);
        h_order.setoCheckout(o_checkout);
        h_order.setoStatus("0"); //未入住状态
        h_order.setoTel(o_tel);
        orderService.placeOrder(h_order);

//        ModelAndView mav = new ModelAndView("orderList");
//        mav.addObject("h_order", h_order);
//
//        return mav;

        return "redirect:orderList";
    }

    //展示用户订单列表   返回订单List
    @GetMapping("/orderList")
    public ModelAndView listOrder(HttpServletRequest request) {
        UserSession userSession = (UserSession)request.getSession().getAttribute("u_id");      //这里现在是使用session
        int u_id = userSession.getId();
        ModelAndView mav = new ModelAndView("orderList");
        List<H_Order> h_orders = orderService.listOrder(u_id);

        mav.addObject("h_orders", h_orders);
        return mav;
    }

    //用户取消订单
    @GetMapping("/cancelOrder")
    public String cancelOrder(@RequestParam(value = "o_id") int o_id, HttpServletRequest request) {
        //通过session获取用户id
        UserSession userSession = (UserSession)request.getSession().getAttribute("u_id");
        int u_id = userSession.getId();

        H_Order h_order = orderService.findOrder(o_id, u_id);
        H_Room h_room = roomService.findRoom(h_order.getHotelId(), h_order.getrNumber());
        H_Roomtype h_roomtype = roomTypeService.findRoomType(h_room.getRtType(), h_room.getHotelId());

        //取消订单，置订单状态为3
        orderService.cancelOrder(o_id, u_id);
        //更新房间状态
        roomService.updateRoom(h_order.getrNumber(), h_order.getHotelId());
        //增加该房型库存
        roomTypeService.addStock(h_roomtype.getRtId());

        return "redirect:orderList";
    }

    //管理员处理退房
    @PutMapping("/order")
    public String updateOrder(@RequestParam(value = "o_id") H_Order h_order) {
        orderService.finishOrder(h_order);
        return "orderInfo";
    }
}
