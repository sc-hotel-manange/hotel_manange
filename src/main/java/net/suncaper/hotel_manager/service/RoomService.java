//package net.suncaper.hotel_manager.service;
//
//import net.suncaper.hotel_manager.domain.H_Room;
//import net.suncaper.hotel_manager.domain.H_RoomExample;
//import net.suncaper.hotel_manager.mapper.H_RoomMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * 房间状态说明：
// * 0 - 空
// * 1 - 已预定
// */
//@Service
//public class RoomService {
//    @Autowired
//    H_RoomMapper h_roomMapper;
//
//    //根据房型给用户分配一个房间
//    public H_Room findRoom(String rt_type, int hotel_id) {
//        H_RoomExample example = new H_RoomExample();
//        example.createCriteria().andRtTypeEqualTo(rt_type).andHotelIdEqualTo(hotel_id).andRStatusEqualTo("0");
//
//        List<H_Room> h_rooms = h_roomMapper.selectByExample(example);
//        return h_rooms.get(0);
//    }
//
//    //根据订单信息找到房间
//    public H_Room findRoom(int hotel_id, String r_number) {
//        H_RoomExample example = new H_RoomExample();
//        example.createCriteria().andHotelIdEqualTo(hotel_id).andRNumberEqualTo(r_number);
//
//        return h_roomMapper.selectByExample(example).get(0);
//    }
//
//    //更新房间状态
//    public void updateRoom(String r_number, int hotel_id) {
//        H_RoomExample example = new H_RoomExample();
//        example.createCriteria().andRNumberEqualTo(r_number).andHotelIdEqualTo(hotel_id);
//        H_Room h_room = h_roomMapper.selectByExample(example).get(0);
//
//        String status = h_room.getrStatus();
//        //更新房间状态
//        if("0".equals(status))
//            status = "1";
//        else
//            status = "0";
//        h_room.setrStatus(status);
//
//        h_roomMapper.updateByPrimaryKey(h_room);
//    }
//}
