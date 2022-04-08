package com.itheima.service;

import java.util.List;

/**
 * @author 周科港
 * @title: MemberService
 * @projectName health_parent
 * @date 2022.3.30  15:10
 */
public interface MemberService {
    // 通过月份查询会员用户
    List<Integer> findMemberCountByMonths(List<String> months);
}
