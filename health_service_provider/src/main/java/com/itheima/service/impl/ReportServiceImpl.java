package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.service.ReportService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 周科港
 * @title: ReportServiceImpl
 * @projectName health_parent
 * @date 2022.3.30  14:57
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl {
}
