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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
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

    //用户自己的订单列表
    @RequestMapping("/orderList")
    public ModelAndView listOrder(@RequestParam(value = "id") Integer id,  HttpServletRequest request){
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

    public ModelAndView orderInfo(@RequestParam(value = "o_id") int o_id){
        ModelAndView mav = new ModelAndView("user/orderInfo");
        mav.addObject("h_order", orderService.orderInfo(o_id));
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

         String status = "-1";
         if (o_status=="全部"){status = "-1";}
         if (o_status=="待支付"){status = "0";}
         if (o_status=="已确认"){status = "1";}
         if (o_status=="已成交"){status = "2";}
         if (o_status=="已取消"){status = "3";}
        System.out.println(dates);
        if(dates!="") {
            String[] date = dates.split(" > ");

            String[] dateStartArray = date[0].split("-");
            String[] dateEndArray = date[1].split("-");

            String start = "20" + dateStartArray[2] + "-" + dateStartArray[0] + "-" + dateStartArray[1];
            String end = "20" + dateEndArray[2] + "-" + dateEndArray[0] + "-" + dateEndArray[1];

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date dateStart = null;
            Date dateEnd = null;
            System.out.println(start+"+"+end);
            dateStart = simpleDateFormat.parse(start);
            dateEnd = simpleDateFormat.parse(end);

            for (int i = 0; i < orders.size(); i++) {
                Date time = orders.get(i).getoOrdertime();
                if (dateStart.compareTo(time) == 1 || dateEnd.compareTo(time) == -1) {
                    System.out.println(time);
                    orders.remove(i);
                    i--;
                }
            }
        }
        if(hotel_translated_name!=""){
            for (int i = 0; i < orders.size(); i++) {
                String name = orders.get(i).getHotelTranslatedName();
                if (!name.contains(hotel_translated_name)) {
                    orders.remove(i);
                    i--;
                }
            }
        }
        if (orders.size()==0){
            model.addAttribute("h_orders",null);
            System.out.println(666);
        }
        else {
            if (status=="-1"){
                model.addAttribute("h_orders",orders);
                System.out.println(777);
            }
            else{
                System.out.println(8881);
                for (int i = 0; i < orders.size(); i++) {
                    String RealStatus = orders.get(i).getoStatus();
                    if (status!=RealStatus) {
                        orders.remove(i);
                        i--;
                    }
                }
                model.addAttribute("h_orders",orders);
            }
        }
        return "user/orderList";
    }

    //下订单
    @PostMapping("/order")
    public ModelAndView insertOrder(@RequestParam(value = "rt_type") String rt_type,
                                   @RequestParam(value = "hotel_id") int hotel_id,
                                   @RequestParam(value = "dates") String dates,
                                   @RequestParam(value = "o_tel") String o_tel,
                                   HttpServletRequest request) throws ParseException {

        System.out.println("这是前端传过来的日期: " + dates);

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

        Date[] normalDates = orderService.parseDates(dates);
        Date o_checkin = normalDates[0];    //入住时间
        Date o_checkout = normalDates[1];   //退房时间

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
        h_order.setHotelTranslatedName(h_hotel.getHotelTranslatedName());
        h_order.setRtType(rt_type);
        h_order.setPhoto(h_hotel.getPhoto1());
        orderService.insertOrder(h_order);

        ModelAndView mav = new ModelAndView("user/payPage");
        mav.addObject("h_order", h_order);

        return mav;
    }


    //用户取消订单
    @GetMapping("/cancelOrder")
    public String deleteOrder(@RequestParam(value = "o_id") int o_id, HttpServletRequest request) {
        //通过session获取用户id
        Session session = (Session)request.getSession().getAttribute("u_id");

        H_Order h_order = orderService.selectOrder(o_id, session.getId());
        H_Room h_room = roomService.findRoom(h_order.getHotelId(), h_order.getrNumber());
        H_Roomtype h_roomtype = roomTypeService.selectRoomType(h_room.getRtType(), h_room.getHotelId());

        //取消订单，置订单状态为3
        orderService.deleteOrder(o_id, session.getId());
        //更新房间状态
        roomService.updateRoom(h_order.getrNumber(), h_order.getHotelId());
        //增加该房型库存
        roomTypeService.addStock(h_roomtype.getRtId());

        return "redirect:/user/orderList";
    }

}