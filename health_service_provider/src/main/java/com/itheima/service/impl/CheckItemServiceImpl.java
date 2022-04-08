package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 周科港
 * @title: CheckItemServiceImpl
 * @projectName health_parent
 * @date 2022.3.28  16:13
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService{

   @Autowired
   private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        //获取当前页面
        Integer currentPage = queryPageBean.getCurrentPage();
        //获取每页记录数
        Integer pageSize = queryPageBean.getPageSize();
        //过滤条件即查询条件
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        //还有一步获取前端的过滤条件 Page<CheckItem> 有mybatis分页助手插件完成封装
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        //分页助手会有方法会帮我们统计总的数据量
        long total = page.getTotal();
        //封装总的行数数据
        List<CheckItem> rows = page.getResult();
        //因为前端需要一个总的数据量，总条数，和总的详细数据（每行显示的数据）
        return  new PageResult(total,rows);
    }

    @Override
    public void delete(Integer id) {
        //删除之前查询当前检查项是否和检查组关联
        //下面这句话意思是，接收来自前端的参数id,
        //然后拿着这个id去查数据库是否已关联检查组
        //如果关联，即这个id出现的次数大于0，那就不能删除
        long count = checkItemDao.findCountByCheckItemId(id);
        if (count>0){
            //当前检查项被引用，不能删除
            throw new RuntimeException("当前检查项被引用，不能删除");
        }
        checkItemDao.deleteById(id);
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }


}
