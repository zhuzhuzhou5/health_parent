package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * @author 周科港
 * @title: orderSettingServiceImpl
 * @projectName health_parent
 * @date 2022.3.29  21:13
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    //批量导入预约设置
    @Override
    public void add(List<OrderSetting> list) {
        //判断当前日期是否已设置
        if (list!=null && list.size()>0){
            for (OrderSetting orderSetting : list) {
                long countByOrderDate = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if (countByOrderDate>0){
                    //已经进行预约设置，执行更新操作
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else {
                    //没有进行预约设置
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {//格式是yyyy-MM
        //格式是yyyy-MM 即2022-3 下面是拼接
        //不再判断某个月是否有31号，因为系统根据表格上传的数据
        //自动查出数据并且匹配
        String begin = date + "-1";
        String end = date + "-31";
        Map<String, String> map = new HashMap<>();
        map.put("begin", begin);
        map.put("end", end);
        //调用Dao,根据日期范围查询预约设置数据
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        //查到之后不要直接返回，因为类型上要的是Map 而提供的是List<OrderSetting>
        List<Map> result = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (OrderSetting orderSetting : list) {
                Map<String, Object> m = new HashMap<>();
                //结合页面来看{ date: 1, number: 120, reservations: 1 }
                //获取日期数字（几号），页面需要什么数据，我们就要想方设法构造什么数据
                m.put("date", orderSetting.getOrderDate().getDate());
                m.put("number", orderSetting.getNumber());
                m.put("reservations", orderSetting.getReservations());
                result.add(m);
            }
        }
        return result;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        //考虑到有可能没有设置好预约人数，也有可能设置好了预约人数
        //1.先获取日期
        Date orderDate = orderSetting.getOrderDate();
        //2.根据日期查询是否已经进行了预约设置
        long count = orderSettingDao.findCountByOrderDate(orderDate);
        if (count>0){
            //当前已经进行预约设置，需要执行更新操作
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else {
            //当前日期没有进行预约设置，需要执行插入操作
            orderSettingDao.add(orderSetting);
        }
    }


}
