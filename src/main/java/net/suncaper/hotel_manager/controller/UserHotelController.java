package net.suncaper.hotel_manager.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import net.suncaper.hotel_manager.domain.H_Hotel;
import net.suncaper.hotel_manager.service.HotelService;
import net.suncaper.hotel_manager.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.AttributedString;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserHotelController {
    @Autowired
    HotelService hotelService;
    @Autowired
    RoomTypeService roomTypeService;

    @RequestMapping("/hotelList")
    public ModelAndView hotelList(@RequestParam(value = "id",required = false) Integer id,
                                  @RequestParam(value = "longitude", required = false) String longitude) {
        System.out.println("经度：" + longitude);

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
            String address = "https://apis.map.qq.com/ws/geocoder/v1/?address=四川省成都市"+hotel_address+"&key=RITBZ-BCACV-PE2PM-UCR75-3UAP2-CUBNM";
            String jsonStr = "";
            String lng ;
            String lat ;
            try {
                //创建一个URL实例
                URL url = new URL(address);

                try {
                    //通过URL的openStrean方法获取URL对象所表示的自愿字节输入流
                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is,"utf-8");

                    //为字符输入流添加缓冲
                    BufferedReader br = new BufferedReader(isr);
                    String data = br.readLine();//读取数据
                    int i = 7;
                    while (data!=null){//循环读取数据
                        System.out.println(data);//输出数据
                        jsonStr = jsonStr+data;
                        data = br.readLine();
                    }
                    br.close();
                    isr.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            JSONObject obj = JSON.parseObject(jsonStr);
            lng = obj.getJSONObject("result").getJSONObject("location").get("lng").toString();
            lat = obj.getJSONObject("result").getJSONObject("location").get("lat").toString();
            hotellist = hotelService.selectAround(lng,lat);
        }
        if(hotel_translated_name!=""){
            for (int i = 0; i < hotellist.size(); i++) {
                String name = hotellist.get(i).getHotelTranslatedName();
                if (!name.contains(hotel_translated_name)) {
                    hotellist.remove(i);
                    i--;
                }
            }
        }
        System.out.println(hotellist.size());
        model.addAttribute("hotelList",hotellist);
        model.addAttribute("id",0);
        return "user/hotellist";
    }
}
