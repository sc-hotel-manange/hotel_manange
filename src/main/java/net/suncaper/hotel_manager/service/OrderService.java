package net.suncaper.hotel_manager.service;

import net.suncaper.hotel_manager.domain.H_Order;
import net.suncaper.hotel_manager.mapper.H_OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    H_OrderMapper h_orderMapper;

    //插入订单
    public void placeOrder(H_Order h_order) {
        h_orderMapper.insert(h_order);
    }
}
