package net.suncaper.hotel_manager.controller;

import net.suncaper.hotel_manager.domain.H_Order;
import net.suncaper.hotel_manager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class OrderController {
    @Autowired
    OrderService orderService;

    //下订单
    @PostMapping("/order")
    public ModelAndView placeOrder(@RequestParam(value = "r_id") int r_id,
                                   @RequestParam(value = "o_price") int o_price,
                                   @RequestParam(value = "o_orderTime") Date o_orderTime,
                                   @RequestParam(value = "o_checkin") Date o_checkin,
                                   @RequestParam(value = "o_checkout") Date o_checkout,
//                                   @RequestParam(value = "o_status") String o_status,
                                   @RequestParam(value = "o_tel") String o_tel) {
        H_Order h_order = new H_Order();
        h_order.setrId(r_id);
        h_order.setoPrice(o_price);
        h_order.setoOrdertime(o_orderTime);
        h_order.setoCheckin(o_checkin);
        h_order.setoCheckout(o_checkout);
        h_order.setoStatus("0"); //未入住状态
        h_order.setoTel(o_tel);

        orderService.placeOrder(h_order);

        ModelAndView mav = new ModelAndView("orderInfo");
        mav.addObject("h_order", h_order);

        return mav;
    }

    //展示用户订单列表
    @GetMapping("/order")
    public ModelAndView listOrder(@RequestParam(value = "r_id") int r_id) {
        ModelAndView mav = new ModelAndView("listOrder");
        List<H_Order> h_orders = orderService.listOrder(r_id);

        mav.addObject("h_orders", h_orders);
        return mav;
    }

    //修改订单状态
    @PutMapping("/order")
    public String updateOrder(@RequestParam(value = "o_id") H_Order h_order) {
        orderService.updateOrder(h_order);
        return "orderInfo";
    }
}
