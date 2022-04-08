package com.itheima.dao;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 周科港
 * @title: OrderSettingDao
 * @projectName health_parent
 * @date 2022.3.29  21:14
 */
public interface OrderSettingDao {
    //上传成功写入数据库失败，这里写错了需求类型
//    long findCountByOrderDate(OrderSetting orderSetting);//错的
    long findCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(Map map);
}
