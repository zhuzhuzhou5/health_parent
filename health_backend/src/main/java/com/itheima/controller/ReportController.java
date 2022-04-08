package com.itheima.controller;

/**
 * @author 周科港
 * @title: ReportController
 * @projectName health_parent
 * @date 2022.3.30  14:54
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.service.ReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * //发送ajax请求获取动态数据
 *   axios.get("/report/getMemberReport.do").then((res)=>{
 *   统计报表
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;
    //会员数量统计
    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        Map<String, Object> map = new HashMap<>();
        /**
         “data”:{
         "months":["2019.01"],["2019.01"],["2019.02"]
         "memberCount":[3,4,8,10]
         */
         //因为["2019.01"],["2019.01"],["2019.02"]是字符串所以List<String>
        List<String> months = new ArrayList<>();
        //需求，动态获取过去的年月
        //欲展示过去一年的数据量须先获取日历
        //获得日历对象，默认时间是当前系统时间
        Calendar calendar = Calendar.getInstance();
        //计算过去一年的12个月，有正值和负值，负值往前推
        //下面这句话的意思是是计算过去四年的时间
        calendar.add(Calendar.MONTH,-48);
        for (int i = 0; i < 12; i++) {
            //获得当前时间往后推一个月日期
            calendar.add(Calendar.MONTH,1);
            Date date = calendar.getTime();
            months.add(new SimpleDateFormat("yyyy.MM").format(date));
        }
        map.put("months",months);
        //根据月份查询会员数量
        //因为[3,4,8,10]是int所以是Integer
        List<Integer> memberCount = memberService.findMemberCountByMonths(months);
        map.put("memberCount",memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,memberCount);

    }

}
