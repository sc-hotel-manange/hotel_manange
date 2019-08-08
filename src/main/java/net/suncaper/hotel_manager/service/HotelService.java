package net.suncaper.hotel_manager.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.suncaper.hotel_manager.domain.H_Hotel;
import net.suncaper.hotel_manager.domain.H_HotelExample;
import net.suncaper.hotel_manager.mapper.CustomizeMapper;
import net.suncaper.hotel_manager.mapper.H_HotelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HotelService {
    @Autowired
    H_HotelMapper h_hotelMapper;
    @Autowired
    CustomizeMapper customizeMapper;

    //酒店列表
    public List<H_Hotel> selectHotelList() {
        H_HotelExample example = new H_HotelExample();

        List<H_Hotel> h_hotels = h_hotelMapper.selectByExample(example);
//        List<H_Hotel> h_hotels = h_hotelMapper.selectHotelList();
        return h_hotels;
    }

    //某酒店详细信息
    public H_Hotel selectHotelInfo(int hotel_id) {
        return h_hotelMapper.selectByPrimaryKey(hotel_id);
    }

    public List<H_Hotel> selectByTransName(String content){
        H_HotelExample example = new H_HotelExample();
        example.createCriteria().andHotelTranslatedNameLike("%"+content+"%");
        return h_hotelMapper.selectByExample(example);
    }

    public List<H_Hotel> selectByAddress(String content){
        H_HotelExample example = new H_HotelExample();
        example.createCriteria().andCityLike("%"+content+"%");
        return h_hotelMapper.selectByExample(example);
    }

    //筛选五星级酒店
    public List<H_Hotel> selectFiveStarHotels() {
//        H_HotelExample example = new H_HotelExample();
//        example.createCriteria().andStarRatingEqualTo("5");

        return customizeMapper.selectFiveStarHotels();
    }

    //筛选评分高的酒店
    public List<H_Hotel> selectTopRatedHotels() {
//        H_HotelExample example = new H_HotelExample();
//        example.createCriteria().andRatingAverageGreaterThanOrEqualTo("8");
//        example.setOrderByClause("rating_average DESC");

        return customizeMapper.selectTopRatedHotels();
    }


    public List<H_Hotel> selectAround(String lng,String lat){
        DecimalFormat df = new DecimalFormat("###.#####");
        String s_lng_b = df.format(Float.parseFloat(lng)+(float)0.5);
        String s_lng_l = df.format(Float.parseFloat(lng)-(float)0.5);

        String s_lat_b = df.format(Float.parseFloat(lat)+(float)0.5);
        String s_lat_l = df.format(Float.parseFloat(lat)-(float)0.5);

        H_HotelExample example = new H_HotelExample();
        example.createCriteria().andLongitudeBetween(s_lng_l,s_lng_b)
                                .andLatitudeBetween(s_lat_l,s_lat_b);
        List<H_Hotel> hotelList = h_hotelMapper.selectByExample(example);
        return hotelList;
    }

    //根据前端传回的地点名如春熙路，从腾讯api获得春熙路的经纬度，返回经纬度
    public Map<String, String> selectByaddressName(String hotel_address){
        String address = "https://apis.map.qq.com/ws/geocoder/v1/?address=四川省成都市"+hotel_address+"&key=RITBZ-BCACV-PE2PM-UCR75-3UAP2-CUBNM";
        String jsonStr = "";
        String lng ;
        String lat ;
        String [] lngAndLat;
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
        Map<String, String> map = new HashMap<String, String>();
        map.put("lng",lng);
        map.put("lat",lat);
        return map;
    }

    //根据酒店名称从酒店列表中筛选
    public  List<H_Hotel> hotelFitName(List<H_Hotel> hotellist, String hotel_translated_name){
        for (int i = 0; i < hotellist.size(); i++) {
            String name = hotellist.get(i).getHotelTranslatedName();
            if (!name.contains(hotel_translated_name)) {
                hotellist.remove(i);
                i--;
            }
        }
        return hotellist;
    }

    //根据酒店星级从酒店列表中筛选
    public List<H_Hotel> hotelFitStar(List<H_Hotel> hotelList, String star_rating) {
        String star = "0";
        switch (star_rating) {
            case "五星级":
                star = "5";
                break;
            case "四星级":
                star = "4";
                break;
            case "三星级":
                star = "3";
                break;
            case "二星级":
                star = "2";
                break;
            case "一星级":
                star = "1";
                break;
        }
        for(int i=0; i<hotelList.size(); i++) {
            if(!star.equals(hotelList.get(i).getStarRating())) {
                hotelList.remove(i);
                i--;
            }
        }
        return hotelList;
    }
}

