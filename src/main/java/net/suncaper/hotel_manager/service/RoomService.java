package net.suncaper.hotel_manager.service;

import net.suncaper.hotel_manager.domain.H_Room;
import net.suncaper.hotel_manager.domain.H_RoomExample;
import net.suncaper.hotel_manager.mapper.H_RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 房间状态说明：
 * 0 - 在未来的时间无用户预定
 * 1 - 在未来的时间有用户预定
 */
@Service
public class RoomService {
    @Autowired
    H_RoomMapper h_roomMapper;

    //根据房型给用户分配可选择的房间列表
    public List<H_Room> findRooms(String rt_type, int hotel_id) {
        H_RoomExample example = new H_RoomExample();
        example.createCriteria().andRtTypeEqualTo(rt_type).andHotelIdEqualTo(hotel_id);
        example.setOrderByClause("r_status ASC"); //使筛选出的房间状态为0的在前

        List<H_Room> rooms = h_roomMapper.selectByExample(example);
        return rooms;
    }

    //根据订单信息找到房间
    public H_Room findRoom(int hotel_id, String r_number) {
        H_RoomExample example = new H_RoomExample();
        example.createCriteria().andHotelIdEqualTo(hotel_id).andRNumberEqualTo(r_number);

        return h_roomMapper.selectByExample(example).get(0);
    }
}
