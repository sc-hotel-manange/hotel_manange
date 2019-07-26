package net.suncaper.hotel_manager.service;

import net.suncaper.hotel_manager.domain.H_Hotel;
import net.suncaper.hotel_manager.mapper.H_HotelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    @Autowired
    H_HotelMapper h_hotelMapper;

    public List<H_Hotel> selectHotelList() {
        List<H_Hotel> h_hotels = h_hotelMapper.selectHotelList();
        return h_hotels;
    }

    public H_Hotel selectHotelInfo(int hotel_id) {
        H_Hotel h_hotel = h_hotelMapper.selectHotelInfo(hotel_id);
        return h_hotel;
    }

}
