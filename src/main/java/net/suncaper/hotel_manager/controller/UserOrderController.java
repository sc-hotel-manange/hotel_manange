package net.suncaper.hotel_manager.controller;

import net.suncaper.hotel_manager.domain.*;
import net.suncaper.hotel_manager.service.*;
import net.suncaper.hotel_manager.domain.H_Order;
import net.suncaper.hotel_manager.domain.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.websocket.server.PathParam;
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
    @Autowired
    UserService userService;

    //用户自己的订单列表
    @RequestMapping("/orderList")
    public ModelAndView listOrder(@RequestParam(value = "id",required = false) Integer id,  HttpServletRequest request){
        Session session = (Session)request.getSession().getAttribute("u_id");
        int u_id = session.getId();

        ModelAndView mav = new ModelAndView("user/orderList");
        List<H_Order> h_orders = orderService.listOrder(u_id);

        mav.addObject("h_orders", h_orders);

        if(id == null)
            mav.addObject("id", 0);
        else
            mav.addObject("id", id);

        return mav;
    }

    //用户的订单详情
    @RequestMapping("/orderInfo")
    public ModelAndView orderInfo(@RequestParam(value = "o_id") int o_id, HttpServletRequest request){
        Session session = (Session)request.getSession().getAttribute("u_id");
        if (session == null)
            return new ModelAndView("user/loginPage");

        ModelAndView mav = new ModelAndView("user/orderInfo");
        mav.addObject("h_order", orderService.selectOrder(o_id, session.getId()));  //订单信息
        mav.addObject("h_user", userService.getUserInfo(session.getId()));  //用户信息
        mav.addObject("h_hotel", hotelService.selectHotelInfo(orderService.selectOrder(o_id).getHotelId()));   //酒店信息
        return mav;
    }

    @RequestMapping("/orderSearch")
    public String orderSearch(@PathParam(value = "dates") String  dates,
                              @PathParam(value = "hotel_translated_name") String hotel_translated_name,
                              @PathParam(value = "o_status") String o_status,
                              HttpServletRequest request,
                              Model model) throws ParseException {
         Session session = (Session) request.getSession().getAttribute("u_id");
         int u_id = session.getId();
         List<H_Order> orders = orderService.listOrder(u_id);


        if(!"".equals(dates)) {
            Date[] dataArray = orderService.parseDates(dates);
            Date dateStart = dataArray[0];
            Date dateEnd = dataArray[1];
            orders = orderService.orderFitData(orders,dateStart,dateEnd);
        }
        if(!"".equals(hotel_translated_name)){
            orders = orderService.orderFitName(orders,hotel_translated_name);
        }

        orders = orderService.orderFitStatus(orders,o_status);
        model.addAttribute("h_orders",orders);
        return "user/orderList";
    }

    //下订单
    @PostMapping("/order")
    public ModelAndView insertOrder(@RequestParam(value = "rt_type") String rt_type,
                                    @RequestParam(value = "hotel_id") int hotel_id,
                                    @RequestParam(value = "dates") String dates,
                                    @RequestParam(value = "o_tel") String o_tel,
                                    HttpServletRequest request) throws ParseException {


        ModelAndView mav = new ModelAndView("user/invoice");
        Session session = (Session)request.getSession().getAttribute("u_id");

        Date[] normalDates = orderService.parseDates(dates);
        Date o_checkin = normalDates[0];    //入住时间
        Date o_checkout = normalDates[1];   //退房时间

        //根据房型筛选出符合条件的房间列表
        List<H_Room> rooms = roomService.findRooms(rt_type, hotel_id);
        //查找是否有可分配的房间
        H_Room h_room = orderService.checkOrder(rooms, o_checkin, o_checkout);
        //若没有找到房间，返回到酒店列表页
        if(h_room == null) {
            return new ModelAndView("redirect:/user/hotelList");
        }

        //若找到符合条件的房间
        //查找该房型
        H_Roomtype h_roomtype = roomTypeService.selectRoomType(rt_type, hotel_id);
        //得到该酒店对象
        H_Hotel h_hotel = hotelService.selectHotelInfo(hotel_id);
        //更新该房间状态为预订
        orderService.orderRoom(h_room);
        //计算用户住的天数
        int stay = orderService.calculateStay(o_checkin, o_checkout);

        H_Order h_order = new H_Order();
        h_order.setrNumber(h_room.getrNumber());
        h_order.setuId(session.getId());
        h_order.setHotelId(hotel_id);
        h_order.setoCheckin(o_checkin);
        h_order.setoCheckout(o_checkout);
        h_order.setoPrice(h_roomtype.getRtPrice() * stay); //总价 = 单价 * 天数
        h_order.setoOrdertime(new Date());
        h_order.setoStatus("0"); //未支付状态
        h_order.setoTel(o_tel);
        h_order.setHotelTranslatedName(h_hotel.getHotelTranslatedName());
        h_order.setRtType(rt_type);
        h_order.setPhoto(h_hotel.getPhoto1());
        orderService.insertOrder(h_order);

        mav.addObject("h_order", h_order);  //订单信息
        mav.addObject("h_user", userService.getUserInfo(session.getId()));  //用户信息
        mav.addObject("h_hotel", hotelService.selectHotelInfo(hotel_id));   //酒店信息

        return mav;
    }

    //用户的支付回执
    @RequestMapping("/orderReceipt")
    public ModelAndView orderReceipt(@RequestParam(value = "o_id") int o_id, HttpServletRequest request) {
        Session session = (Session)request.getSession().getAttribute("u_id");
        if (session == null)
            return new ModelAndView("user/loginPage");

        H_Order h_order = orderService.selectOrder(o_id, session.getId());
        orderService.payOrder(h_order);

        ModelAndView mav = new ModelAndView("user/orderInfo");
        mav.addObject("h_order", h_order);  //订单信息
        mav.addObject("h_user", userService.getUserInfo(session.getId()));  //用户信息
        mav.addObject("h_hotel", hotelService.selectHotelInfo(orderService.selectOrder(o_id).getHotelId()));   //酒店信息
        return mav;
    }

    @RequestMapping("/pay")
    public ModelAndView pay(@RequestParam(value = "o_id") int o_id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("user/invoice");
        Session session = (Session)request.getSession().getAttribute("u_id");

        H_Order h_order = orderService.selectOrder(o_id);

        mav.addObject("h_order", h_order);  //订单信息
        mav.addObject("h_user", userService.getUserInfo(session.getId()));  //用户信息
        mav.addObject("h_hotel", hotelService.selectHotelInfo(h_order.getHotelId()));   //酒店信息

        return mav;
    }


    //用户取消订单
    @RequestMapping("/cancelOrder")
    public String cancelOrder(@RequestParam(value = "o_id") int o_id, HttpServletRequest request) {
        //通过session获取用户id
        Session session = (Session)request.getSession().getAttribute("u_id");

        H_Order h_order = orderService.selectOrder(o_id, session.getId());
        H_Room h_room = roomService.findRoom(h_order.getHotelId(), h_order.getrNumber());

        //取消订单，置订单状态为3
        orderService.cancelOrder(o_id, session.getId());
        //更新房间状态
        orderService.leaveRoom(h_room);

        return "redirect:/user/orderList";
    }

}