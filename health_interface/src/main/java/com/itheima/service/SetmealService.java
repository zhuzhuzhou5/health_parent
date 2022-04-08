package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;

/**
 * @author 周科港
 * @title: SetmealService
 * @projectName health_parent
 * @date 2022.3.29  15:50
 */
public interface SetmealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);


    PageResult pageQuery(QueryPageBean queryPageBean);

    void delete(Integer id);
}
