package net.suncaper.hotel_manager.controller;

import net.suncaper.hotel_manager.domain.H_Hotel;
import net.suncaper.hotel_manager.service.HotelService;
import net.suncaper.hotel_manager.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class
HotelController {
    @Autowired
    HotelService hotelService;
    @Autowired
    RoomTypeService roomTypeService;

    @RequestMapping("/hotelList")
    public String listHotel(Model model) {
        model.addAttribute("hotels", hotelService.selectHotelList());
        return "hotelList";
    }

    @RequestMapping("/hotelInfo")
    public String hotelInfo(@RequestParam("hotel_id") int hotel_id, Model model) {
        model.addAttribute("hotel", hotelService.selectHotelInfo(hotel_id));
        model.addAttribute("roomTypes", roomTypeService.findRoomTypeByHid(hotel_id));
        return "hotelInfo";
    }

}
