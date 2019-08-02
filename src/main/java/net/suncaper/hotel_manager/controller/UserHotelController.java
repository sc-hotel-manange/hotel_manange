package net.suncaper.hotel_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserHotelController {
    @RequestMapping("/hotelList")
    public String hotelList() {
        return "user/hotelList";
    }
}
