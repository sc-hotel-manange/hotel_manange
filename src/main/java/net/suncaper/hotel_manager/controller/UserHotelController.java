package net.suncaper.hotel_manager.controller;

import net.suncaper.hotel_manager.domain.H_Hotel;
import net.suncaper.hotel_manager.service.HotelService;
import net.suncaper.hotel_manager.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("/hotelSearch")
    public String hotelSearch(@PathParam(value = "hotel_translated_name") String hotel_translated_name,
                              @PathParam(value = "hotel_address") String  hotel_address,
                              Model model){
        List<H_Hotel> hotellist = null;
        if (hotel_address!=""){
            Map<String, String> map = hotelService.selectByaddressName(hotel_address);
            hotellist = hotelService.selectAround(map.get("lng"),map.get("lat"));
        }
         else if(hotel_translated_name!=""){
            hotellist =  hotelService.selectHotelList();
            hotellist = hotelService.hotelFitName(hotellist,hotel_translated_name);
        }
         else {
            hotellist = hotelService.hotelFitName(hotellist,hotel_translated_name);
        }
        System.out.println(hotellist.size());
        model.addAttribute("hotelList",hotellist);
        model.addAttribute("id",0);
        return "user/hotellist";
    }

    @RequestMapping(value = "recommend")
    @ResponseBody
    public List<H_Hotel>  recommend(@RequestParam(value = "longitude")String longitude,
                                        @RequestParam(value = "latitude") String latitude,
                                        Model model){
        List<H_Hotel> hotelList = hotelService.selectAround(longitude,latitude);
        if (hotelList.size()<5){
            return hotelList;
        }
        else {
            while(hotelList.size()>=5){
                hotelList.remove(hotelList.size()-1);
            }
            return hotelList;
        }



    }
}
