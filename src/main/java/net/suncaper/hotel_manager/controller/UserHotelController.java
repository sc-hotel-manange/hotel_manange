package net.suncaper.hotel_manager.controller;

import net.suncaper.hotel_manager.service.HotelService;
import net.suncaper.hotel_manager.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.AttributedString;

@Controller
@RequestMapping("/user")
public class UserHotelController {
    @Autowired
    HotelService hotelService;
    @Autowired
    RoomTypeService roomTypeService;

    @RequestMapping("/hotelList")
    public ModelAndView hotelList(@RequestParam(value = "id",required = false) Integer id) {
        ModelAndView mav = new ModelAndView("user/hotelList");
        mav.addObject("hotelList", hotelService.selectHotelList());

        if(id == null)
            mav.addObject("id", 0);
        else
            mav.addObject("id", id);

        //这里提供了所有酒店的信息
        return mav;
    }

    @RequestMapping("/hotelInfo")
    public ModelAndView hotelInfo(@RequestParam("hotel_id") int hotel_id) {     //在这里根据酒店的id来提供具体某个酒店的信息
        ModelAndView mav = new ModelAndView("user/hotelInfo");
        mav.addObject("hotelInfo", hotelService.selectHotelInfo(hotel_id));
        mav.addObject("roomTypes", roomTypeService.selectRoomType(hotel_id));    //这里提供酒店的类型type
        return mav;
    }
}
