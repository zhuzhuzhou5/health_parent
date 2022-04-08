package com.itheima.dao;

import com.itheima.pojo.Permission;

import java.util.Set;

/**
 * @author 周科港
 * @title: PermissionDao
 * @projectName health_parent
 * @date 2022.3.30  10:57
 */
public interface PermissionDao {
    Set<Permission> findByRoleId(Integer roleId);
}
