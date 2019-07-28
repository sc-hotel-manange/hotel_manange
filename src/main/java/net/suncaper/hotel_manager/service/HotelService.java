package net.suncaper.hotel_manager.service;

import net.suncaper.hotel_manager.domain.H_Hotel;
import net.suncaper.hotel_manager.domain.H_HotelExample;
import net.suncaper.hotel_manager.mapper.H_HotelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    @Autowired
    H_HotelMapper h_hotelMapper;

    //酒店列表
    public List<H_Hotel> selectHotelList() {
        H_HotelExample example = new H_HotelExample();

        List<H_Hotel> h_hotels = h_hotelMapper.selectByExample(example);
//        List<H_Hotel> h_hotels = h_hotelMapper.selectHotelList();
        return h_hotels;
    }

    //某酒店详细信息
    //H_HotelMapper.xml中没有生成selectByPrimaryKey...
    public H_Hotel selectHotelInfo(int hotel_id) {
        H_HotelExample example = new H_HotelExample();
        example.createCriteria().andHotelIdEqualTo(hotel_id);

        List<H_Hotel> h_hotels = h_hotelMapper.selectByExample(example);
        return h_hotels.get(0);
//        H_Hotel h_hotel = h_hotelMapper.selectHotelInfo(hotel_id);
//        return h_hotel;
    }

}