package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 周科港
 * @title: MemberServiceImpl
 * @projectName health_parent
 * @date 2022.3.30  15:12
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;
    //根据月份查询会员数量
    @Override
    public List<Integer> findMemberCountByMonths(List<String> months) {
        List<Integer> memberCount = new ArrayList<>();
        for (String month : months) {
            String data = month + ".31";//2022.03.30
            //统计数量截至到某个日期前
            Integer count = memberDao.findMemberCountBeforeDate(data);
            memberCount.add(count);
        }
        return memberCount;

    }
}
