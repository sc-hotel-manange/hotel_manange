package net.suncaper.hotel_manager.service;

import net.suncaper.hotel_manager.domain.*;
import net.suncaper.hotel_manager.mapper.H_OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    //将前端传过来的日期转化为Date类型
    public Date[] parseDates(String dates) throws ParseException {
        String[] splitdate = dates.split(" > ");
        String checkin = splitdate[0];  //入住时间
        String checkout = splitdate[1]; //退房时间

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
        Date o_checkin = sdf.parse(checkin);
        Date o_checkout = sdf.parse(checkout);

        Date[] normalDates = {o_checkin, o_checkout};

        return normalDates;
    }

    //订单详情
    public H_Order orderInfo(int o_id){
        return h_orderMapper.selectByPrimaryKey(o_id);
    }

    //为了安全性，根据订单id和用户id查找订单
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

    //更新订单
    public void updateOrder(H_Order h_order) {
        H_OrderExample example = new H_OrderExample();
        example.createCriteria().andOIdEqualTo(h_order.getoId()).andUIdEqualTo(h_order.getuId());

        h_orderMapper.updateByExample(h_order, example);
    }

    //用户完成支付，订单状态置为1
    public void pay(int o_id, int u_id) {
        H_Order h_order = selectOrder(o_id, u_id);
        h_order.setoStatus("1");

        updateOrder(h_order);
    }

    //用户取消订单, 订单状态置为3
    public void deleteOrder(int o_id, int u_id) {
        H_Order h_order = selectOrder(o_id, u_id);
        h_order.setoStatus("3");

        updateOrder(h_order);
    }

    //管理员处理退房
    public void finishOrder(H_Order h_order) {
        h_orderMapper.updateByPrimaryKey(h_order);
    }
}
