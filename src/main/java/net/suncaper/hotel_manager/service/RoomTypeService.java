package net.suncaper.hotel_manager.service;

import net.suncaper.hotel_manager.domain.H_RoomExample;
import net.suncaper.hotel_manager.domain.H_Roomtype;
import net.suncaper.hotel_manager.domain.H_RoomtypeExample;
import net.suncaper.hotel_manager.mapper.H_RoomtypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomTypeService {
    @Autowired
    H_RoomtypeMapper h_roomtypeMapper;

    //查找房型
    public H_Roomtype selectRoomType(String rt_type, int hotel_id) {
        H_RoomtypeExample example = new H_RoomtypeExample();
        example.createCriteria().andRtTypeEqualTo(rt_type).andHotelIdEqualTo(hotel_id);
        return h_roomtypeMapper.selectByExample(example).get(0);
    }

    //查找该酒店有哪些房型
    public List<H_Roomtype> selectRoomType(int hotel_id) {
        H_RoomtypeExample example = new H_RoomtypeExample();
        example.createCriteria().andHotelIdEqualTo(hotel_id);

        List<H_Roomtype> h_roomtypes = h_roomtypeMapper.selectByExample(example);
        return h_roomtypes;
    }

    /* 不需要对库存进行操作，库存仅用于展示
    //增加库存
    public void addStock(int rt_id) {
        H_Roomtype h_roomtype = h_roomtypeMapper.selectByPrimaryKey(rt_id);
        int stock = h_roomtype.getRtStock();
        h_roomtype.setRtStock(++ stock);

        h_roomtypeMapper.updateByPrimaryKey(h_roomtype);
    }

    //减少库存
    public void reduceStock(int rt_id) {
        H_Roomtype h_roomtype = h_roomtypeMapper.selectByPrimaryKey(rt_id);
        int stock = h_roomtype.getRtStock();
        h_roomtype.setRtStock(-- stock);

        h_roomtypeMapper.updateByPrimaryKey(h_roomtype);
    }
    */
}
