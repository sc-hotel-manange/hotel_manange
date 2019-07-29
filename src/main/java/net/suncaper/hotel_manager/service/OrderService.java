package net.suncaper.hotel_manager.service;

import net.suncaper.hotel_manager.domain.H_Order;
import net.suncaper.hotel_manager.domain.H_OrderExample;
import net.suncaper.hotel_manager.mapper.H_OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    H_OrderMapper h_orderMapper;

    //插入订单
    public void placeOrder(H_Order h_order) {
        h_orderMapper.insert(h_order);
    }

    //展示用户订单列表
    public List<H_Order> listOrder(int r_id) {
        H_OrderExample example = new H_OrderExample();
        example.createCriteria().andRIdEqualTo(r_id);

        return h_orderMapper.selectByExample(example);
    }

    //修改用户订单状态
    public void updateOrder(H_Order h_order) {
        h_orderMapper.updateByPrimaryKey(h_order);
    }
}
