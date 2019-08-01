//package net.suncaper.hotel_manager.service;
//
//import net.suncaper.hotel_manager.domain.*;
//import net.suncaper.hotel_manager.mapper.H_OrderMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * 订单状态说明：
// * 0 - 未入住
// * 1 - 已入住
// * 2 - 已退房
// * 3 - 取消订单
// */
//@Service
//public class OrderService {
//    @Autowired
//    H_OrderMapper h_orderMapper;
//
//    //查找订单
//    public H_Order findOrder(int o_id, int u_id) {
//        H_OrderExample example = new H_OrderExample();
//        example.createCriteria().andOIdEqualTo(o_id).andUIdEqualTo(u_id);
//
//        return h_orderMapper.selectByExample(example).get(0);
//    }
//
//    //插入订单
//    public void placeOrder(H_Order h_order) {
//        h_orderMapper.insert(h_order);
//    }
//
//    //展示用户订单列表
//    public List<H_Order> listOrder(int u_id) {
//        H_OrderExample example = new H_OrderExample();
//        example.createCriteria().andUIdEqualTo(u_id).andOStatusNotEqualTo("3");
//
//        return h_orderMapper.selectByExample(example);
//    }
//
//    //用户取消订单, 订单状态置为3
//    public void cancelOrder(int o_id, int u_id) {
//        H_OrderExample example = new H_OrderExample();
//        example.createCriteria().andOIdEqualTo(o_id).andUIdEqualTo(u_id).andOStatusEqualTo("0");
//
//        //更新订单状态
//        H_Order h_order = h_orderMapper.selectByExample(example).get(0);
//        h_order.setoStatus("3");
//
//        h_orderMapper.updateByExample(h_order, example);
//    }
//
//    //管理员处理退房
//    public void finishOrder(H_Order h_order) {
//        h_orderMapper.updateByPrimaryKey(h_order);
//    }
//}
