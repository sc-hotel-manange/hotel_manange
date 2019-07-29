package net.suncaper.hotel_manager.service;

import net.suncaper.hotel_manager.domain.*;
import net.suncaper.hotel_manager.mapper.H_OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单状态说明：
 * 0 - 未入住
 * 1 - 已入住
 * 2 - 已退房
 */
@Service
public class OrderService {
    @Autowired
    H_OrderMapper h_orderMapper;

    //插入订单
    public void placeOrder(H_Order h_order) {
        h_orderMapper.insert(h_order);
    }

    //展示用户订单列表
    public List<H_Order> listOrder(int u_id) {
        H_OrderExample example = new H_OrderExample();
        example.createCriteria().andUIdEqualTo(u_id);

        return h_orderMapper.selectByExample(example);
    }

    //(管理员)修改用户订单状态
    public void updateOrder(H_Order h_order) {
        h_orderMapper.updateByPrimaryKey(h_order);
    }
}
