package net.suncaper.hotel_manager.controller;

import net.suncaper.hotel_manager.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @Autowired
    HotelService hotelService;

    @RequestMapping("")     //首页
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("user/index");
        mav.addObject("fiveStarHotels", hotelService.selectFiveStarHotels());
        mav.addObject("topRatedHotels", hotelService.selectTopRatedHotels());

        return mav;
    }

    @RequestMapping("/about")   //关于
    public String about() {
        return "user/about";
    }

}
