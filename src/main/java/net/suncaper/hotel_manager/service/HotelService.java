package net.suncaper.hotel_manager.service;

import net.suncaper.hotel_manager.domain.H_Hotel;
import net.suncaper.hotel_manager.domain.H_HotelExample;
import net.suncaper.hotel_manager.mapper.CustomizeMapper;
import net.suncaper.hotel_manager.mapper.H_HotelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

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
        String s_lng_b = df.format(Float.parseFloat(lng)+(float)0.01);
        String s_lng_l = df.format(Float.parseFloat(lng)-(float)0.01);

        String s_lat_b = df.format(Float.parseFloat(lat)+(float)0.01);
        String s_lat_l = df.format(Float.parseFloat(lat)-(float)0.01);

        H_HotelExample example = new H_HotelExample();
        example.createCriteria().andLongitudeBetween(s_lng_l,s_lng_b)
                                .andLatitudeBetween(s_lat_l,s_lat_b);
        List<H_Hotel> hotelList = h_hotelMapper.selectByExample(example);
        return hotelList;
    }
}

