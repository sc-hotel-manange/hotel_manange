package net.suncaper.hotel_manager.controller;

import net.suncaper.hotel_manager.domain.H_Hotel;
import net.suncaper.hotel_manager.domain.H_HotelExample;
import net.suncaper.hotel_manager.service.HotelService;
import net.suncaper.hotel_manager.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminHotelController {
    @Autowired
    HotelService hotelService;
    @Autowired
    RoomTypeService roomTypeService;

    @RequestMapping("/hotelList")
    public String listHotel(Model model) {
        System.out.println("进入");
        model.addAttribute("hotels", hotelService.selectHotelList());
        return "admin/hotelList";
    }

    @RequestMapping("/hotelInfo")
    public String hotelInfo(@RequestParam("hotel_id") int hotel_id, Model model) {
        model.addAttribute("hotel", hotelService.selectHotelInfo(hotel_id));
        model.addAttribute("roomTypes", roomTypeService.selectRoomType(hotel_id));
        return "admin/hotelInfo";
    }

    @RequestMapping("/hotelMap")
    public String hotelMap(@PathParam(value = "hotel_id") int hotel_id,Model model){
        H_Hotel h_hotel =hotelService.selectHotelInfo(hotel_id);
        model.addAttribute("longitude",h_hotel.getLongitude());
        model.addAttribute("latitude",h_hotel.getLatitude());
        return "hotelMap";
    }

    @RequestMapping("/hotelSearch")
    public String hotelSearch(@PathParam(value = "type") String type,
                              @PathParam(value = "content")String content,
                              Model model){
        List<H_Hotel> h_hotelList ;
        if(type.equals("按名称搜索")){
            h_hotelList = hotelService.selectByTransName(content);
        }
        else{
            h_hotelList = hotelService.selectByAddress(content);
        }
        model.addAttribute("hotels",h_hotelList);
        return "admin/hotelList";
    }

}
