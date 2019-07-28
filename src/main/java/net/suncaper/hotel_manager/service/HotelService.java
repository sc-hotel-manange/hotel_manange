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
    public H_Hotel selectHotelInfo(int hotel_id) {
        H_Hotel h_hotel = h_hotelMapper.selectByPrimaryKey(hotel_id);
        return h_hotel;
//        H_Hotel h_hotel = h_hotelMapper.selectHotelInfo(hotel_id);
//        return h_hotel;
    }

}
