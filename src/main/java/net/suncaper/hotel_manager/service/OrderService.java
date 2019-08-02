package net.suncaper.hotel_manager.service;

import net.suncaper.hotel_manager.domain.*;
import net.suncaper.hotel_manager.mapper.H_OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单状态说明：
 * 0 - 待支付
 * 1 - 已确认（还未入住）
 * 2 - 已成交（入住后成功退房完成订单）
 * 3 - 已取消（提前取消订单）
 */
@Service
public class OrderService {
    @Autowired
    H_OrderMapper h_orderMapper;

    //订单详情
    public H_Order orderInfo(int o_id){
        return h_orderMapper.selectByPrimaryKey(o_id);
    }

    //查找订单
    public H_Order selectOrder(int o_id, int u_id) {
        H_OrderExample example = new H_OrderExample();
        example.createCriteria().andOIdEqualTo(o_id).andUIdEqualTo(u_id);

        return h_orderMapper.selectByExample(example).get(0);
    }

    //插入订单
    public void insertOrder(H_Order h_order) {
        h_orderMapper.insert(h_order);
    }

    //展示用户订单列表
    public List<H_Order> listOrder(int u_id) {
        H_OrderExample example = new H_OrderExample();
        example.createCriteria().andUIdEqualTo(u_id);

        return h_orderMapper.selectByExample(example);
    }

    //展示所有订单
    public List<H_Order> listOrder() {
        H_OrderExample example = new H_OrderExample();

        return h_orderMapper.selectByExample(example);
    }

    //用户取消订单, 订单状态置为3
    public void deleteOrder(int o_id, int u_id) {
        H_OrderExample example = new H_OrderExample();
        example.createCriteria().andOIdEqualTo(o_id).andUIdEqualTo(u_id).andOStatusEqualTo("0");

        //更新订单状态
        H_Order h_order = h_orderMapper.selectByExample(example).get(0);
        h_order.setoStatus("3");

        h_orderMapper.updateByExample(h_order, example);
    }

    //管理员处理退房
    public void finishOrder(H_Order h_order) {
        h_orderMapper.updateByPrimaryKey(h_order);
    }
}
