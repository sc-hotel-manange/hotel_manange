package net.suncaper.hotel_manager.service;

import net.suncaper.hotel_manager.domain.*;
import net.suncaper.hotel_manager.mapper.H_OrderMapper;
import net.suncaper.hotel_manager.mapper.H_RoomMapper;
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
    @Autowired
    H_RoomMapper h_roomMapper;

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

    //计算住宿天数
    public int calculateStay(Date o_checkin, Date o_checkout) {
        return (int)(o_checkout.getTime() - o_checkin.getTime()) / (1000*3600*24);
    }

    //判断用户预选房是否和其他订单冲突
    public H_Room checkOrder(List<H_Room> rooms, Date o_checkin, Date o_checkout) {
        for(H_Room room : rooms) {
            //筛选出有相同酒店和房间的订单
            List<H_Order> orders = selectOrder(room.getHotelId(), room.getrNumber());
            //假如没有相同酒店和房间的订单
            System.out.println(orders.size());

            boolean flag = true; //标识

            if(orders == null) return room;

            for(H_Order order : orders) {
                Date otherCheckin = order.getoCheckin();
                System.out.println(otherCheckin);
                Date otherCheckout = order.getoCheckout();
                System.out.println(otherCheckout);
                if((o_checkin.before(otherCheckout)&&o_checkin.after(otherCheckin)) ||
                        (o_checkout.after(otherCheckin)&&o_checkout.before(otherCheckout)) ||
                        o_checkin.equals(otherCheckin) || o_checkout.equals(otherCheckout)) {
                    flag = false;
                    break;
                }
            }

            if(flag)
                return room;
        }
        return null;
    }

    //订房时更新房间状态
    public void orderRoom(H_Room h_room) {
        String status = h_room.getrStatus();
        //更新房间状态
        if("0".equals(status))
            status = "1";
        else
            return;
        h_room.setrStatus(status);

        h_roomMapper.updateByPrimaryKey(h_room);
    }

    //退房时更新房间状态
    public void leaveRoom(H_Room h_room) {
        List<H_Order> orders = selectOrder(h_room.getHotelId(), h_room.getrNumber());
        //若没有预订该房间的订单，将房间状态置为无人预订
        if(orders==null || orders.size()==0) {
            h_room.setrStatus("0");
            h_roomMapper.updateByPrimaryKey(h_room);
        }
    }

    //保留订单数组中符合前端传回的下订单时间的订单
    public List<H_Order> orderFitData(List<H_Order> orders,Date dateStart,Date dateEnd){
        for (int i = 0; i < orders.size(); i++) {
            Date time = orders.get(i).getoOrdertime();
            if (dateStart.compareTo(time) == 1 || dateEnd.compareTo(time) == -1) {
                System.out.println(time);
                orders.remove(i);
                i--;
            }
        }
        return orders;
    }

    //保留订单数组中包含符合前端传回的酒店名称的订单
    public List<H_Order> orderFitName(List<H_Order> orders,String hotel_translated_name){
        for (int i = 0; i < orders.size(); i++) {
            String name = orders.get(i).getHotelTranslatedName();
            if (!name.contains(hotel_translated_name)) {
                orders.remove(i);
                i--;
            }
        }
        return orders;
    }

    //保留订单数组中符合前端传回的订单状态的订单
    public List<H_Order> orderFitStatus(List<H_Order> orders,String o_status){
        String status = "-1";
        if ("全部".equals(o_status)){status = "-1";}
        if ("待支付".equals(o_status)){status = "0";}
        if ("已确认".equals(o_status)){status = "1";}
        if ("已成交".equals(o_status)){status = "2";}
        if ("已取消".equals(o_status)){status = "3";}
        if ("-1".equals(status)){
            return orders;
        }
        else{
            for (int i = 0; i < orders.size(); i++) {
                String RealStatus = orders.get(i).getoStatus();
                if (!status.equals(RealStatus)) {
                    orders.remove(i);
                    i--;
                }
            }
            return orders;
        }
    }

    //根据订单号查找订单
    public H_Order selectOrder(int o_id){
        return h_orderMapper.selectByPrimaryKey(o_id);
    }

    //为了安全性，根据订单id和用户id查找订单
    public H_Order selectOrder(int o_id, int u_id) {
        H_OrderExample example = new H_OrderExample();
        example.createCriteria().andOIdEqualTo(o_id).andUIdEqualTo(u_id);

        return h_orderMapper.selectByExample(example).get(0);
    }

    //查找相同酒店和房间的订单
    public List<H_Order> selectOrder(int hotel_id, String r_number) {
        H_OrderExample example = new H_OrderExample();
        example.createCriteria().andHotelIdEqualTo(hotel_id).andRNumberEqualTo(r_number);

        return h_orderMapper.selectByExample(example);
    }

    //插入订单
    public void insertOrder(H_Order h_order) {
        h_orderMapper.insert(h_order);
    }

    //展示用户订单列表
    public List<H_Order> listOrder(int u_id) {
        H_OrderExample example = new H_OrderExample();
        example.createCriteria().andUIdEqualTo(u_id);
        example.setOrderByClause("o_id DESC"); //按订单时间降序排列，最新的订单在最前面

        return h_orderMapper.selectByExample(example);
    }

    //管理员查看所属酒店订单，超级管理员查看所有订单
    public List<H_Order> hotelOrder(int hotel_id) {
        H_OrderExample example = new H_OrderExample();
        if(hotel_id == 0) { //超级管理员
            example.setOrderByClause("o_id DESC"); //最新订单在最前面
            return h_orderMapper.selectByExample(example);
        }else { //酒店普通管理员
            example.createCriteria().andHotelIdEqualTo(hotel_id);
            example.setOrderByClause("o_id DESC");
            return h_orderMapper.selectByExample(example);
        }
    }

    //根据订单号搜索订单
    public List<H_Order> orderSearch(int o_id) {
        H_OrderExample example = new H_OrderExample();
        example.createCriteria().andOIdEqualTo(o_id);

        return h_orderMapper.selectByExample(example);
    }

    //更新订单
    public void updateOrder(H_Order h_order) {
        H_OrderExample example = new H_OrderExample();
        example.createCriteria().andOIdEqualTo(h_order.getoId()).andUIdEqualTo(h_order.getuId());

        h_orderMapper.updateByExample(h_order, example);
    }

    //用户完成支付，订单状态置为1
    public void payOrder(H_Order h_order) {
        h_order.setoStatus("1");
        updateOrder(h_order);
    }

    //用户退房，完成订单，订单状态置为2
    public H_Order finishOrder(H_Order h_order) {
        h_order.setoStatus("2");
        updateOrder(h_order);

        return h_order;
    }

    //用户取消订单, 订单状态置为3
    public void cancelOrder(int o_id, int u_id) {
        H_Order h_order = selectOrder(o_id, u_id);
        h_order.setoStatus("3");

        updateOrder(h_order);
    }
}
