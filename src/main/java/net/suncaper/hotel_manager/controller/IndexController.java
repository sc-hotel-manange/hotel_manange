package net.suncaper.hotel_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("")
    public String index() {

        return "user/index";
    }

}
