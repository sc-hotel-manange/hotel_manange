package net.suncaper.hotel_manager.controller;

import net.suncaper.hotel_manager.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @RequestMapping("")
    public String index() {
        return "redirect:/user/index";
    }

    @RequestMapping("/")
    public String index2() {
        return "redirect:/user/index";
    }

}
