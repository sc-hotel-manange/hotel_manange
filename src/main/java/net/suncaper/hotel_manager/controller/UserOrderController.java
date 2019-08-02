package net.suncaper.hotel_manager.controller;

import net.suncaper.hotel_manager.domain.*;
import net.suncaper.hotel_manager.service.HotelService;
import net.suncaper.hotel_manager.domain.H_Order;
import net.suncaper.hotel_manager.domain.Session;
import net.suncaper.hotel_manager.service.OrderService;
import net.suncaper.hotel_manager.service.RoomService;
import net.suncaper.hotel_manager.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserOrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    RoomTypeService roomTypeService;
    @Autowired
    RoomService roomService;
    @Autowired
    HotelService hotelService;

    //用户自己的订单列表
    @RequestMapping("/orderList")
    public ModelAndView listOrder(@RequestParam(value = "id") Integer id,  HttpServletRequest request){
        Session session = (Session)request.getSession().getAttribute("u_id");

        ModelAndView mav = new ModelAndView("user/orderList");
        List<H_Order> h_orders = orderService.listOrder(session.getId());

        mav.addObject("h_orders", h_orders);

        if(id == null)
            mav.addObject("id", 0);
        else
            mav.addObject("id", id);

        return mav;
    }

    //用户的订单详情
    @RequestMapping("/orderInfo")

    public ModelAndView orderInfo(@RequestParam(value = "o_id") int o_id){
        ModelAndView mav = new ModelAndView("user/orderInfo");
        mav.addObject("h_order", orderService.orderInfo(o_id));
        return mav;
    }

    //下订单
    @PostMapping("/order")
    public ModelAndView placeOrder(@RequestParam(value = "rt_type") String rt_type,
                                   @RequestParam(value = "hotel_id") int hotel_id,
                                   @RequestParam(value = "o_checkin") Date o_checkin,
                                   @RequestParam(value = "o_checkout") Date o_checkout,
                                   @RequestParam(value = "o_tel") String o_tel,
                                   HttpServletRequest request) {

        Session session = (Session)request.getSession().getAttribute("u_id");     //这里使用session

        //根据房型分配一个房间
        H_Room h_room = roomService.findRoom(rt_type, hotel_id);
        //查找该房型
        H_Roomtype h_roomtype = roomTypeService.selectRoomType(rt_type, hotel_id);
        //得到该酒店对象
        H_Hotel h_hotel = hotelService.selectHotelInfo(hotel_id);

        //更新该房间状态
        roomService.updateRoom(h_room.getrNumber(), hotel_id);
        //减少该房型库存
        roomTypeService.reduceStock(h_roomtype.getRtId());

        //计算用户住的天数
        int stay = (int)(o_checkout.getTime()-o_checkin.getTime()) / (1000*3600*24);

        H_Order h_order = new H_Order();
        h_order.setrNumber(h_room.getrNumber());
        h_order.setuId(session.getId());
        h_order.setHotelId(hotel_id);
        h_order.setoPrice(h_roomtype.getRtPrice() * stay); //总价 = 单价 * 天数
        h_order.setoOrdertime(new Date());
        h_order.setoCheckin(o_checkin);
        h_order.setoCheckout(o_checkout);
        h_order.setoStatus("0"); //未支付状态
        h_order.setoTel(o_tel);
        orderService.insertOrder(h_order);
        h_order.setHotelTranslatedName(h_hotel.getHotelTranslatedName());
        h_order.setRtType(rt_type);
        h_order.setPhoto(h_hotel.getPhoto1());

        ModelAndView mav = new ModelAndView("user/payPage");
        mav.addObject("h_order", h_order);

        return mav;
    }


    //用户取消订单
    @GetMapping("/cancelOrder")
    public String cancelOrder(@RequestParam(value = "o_id") int o_id, HttpServletRequest request) {
        //通过session获取用户id
        Session userSession = (Session)request.getSession().getAttribute("u_id");
        int u_id = userSession.getId();

        H_Order h_order = orderService.selectOrder(o_id, u_id);
        H_Room h_room = roomService.findRoom(h_order.getHotelId(), h_order.getrNumber());
        H_Roomtype h_roomtype = roomTypeService.selectRoomType(h_room.getRtType(), h_room.getHotelId());

        //取消订单，置订单状态为3
        orderService.deleteOrder(o_id, u_id);
        //更新房间状态
        roomService.updateRoom(h_order.getrNumber(), h_order.getHotelId());
        //增加该房型库存
        roomTypeService.addStock(h_roomtype.getRtId());

        return "redirect:/user/orderList";
    }


}

