package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;

import java.util.HashMap;

/**
 * @author 周科港
 * @title: SetmealDao
 * @projectName health_parent
 * @date 2022.3.29  15:52
 */
public interface SetmealDao {
    void add(Setmeal setmeal);

    void setSetmealAndCheckGroup(HashMap<String, Integer> map);

    Page<Setmeal> findByCondition(String queryString);

    void delete(Integer id);
}
