package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 周科港
 * @title: CheckItemController
 * @projectName health_parent
 * @date 2022.3.28  16:06
 */

/**
 *  //表单数据校验通过，发送ajax请求将表单数据提交到后台
 *
 axios.post("/checkitem/add.do",this.formData).then((response)=> {
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    //因为formData所以用实例类型解析
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
            try{
                checkItemService.add(checkItem);
            }catch (Exception e){
                e.printStackTrace();
                return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
            }
            return  new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     *
     *  var param = {
     *       currentPage: this.pagination.currentPage,
     *        pageSize: this.pagination.pageSize,
     *         queryString: this.pagination.queryString
     *  }
     * axios.post("/checkitem/findPage.do",param).then((res)=>{
     *   // 解析Controller响应回的数据，为模型数据赋值
     *   this.pagination.total = res.data.total; // 总条数
     *   this.dataList = res.data.rows; // 页面展示内容
     */

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkItemService.pageQuery(queryPageBean);
        return pageResult;
    }

    /**
     * //点击确定按钮时只需此处代码
     *     //alert('用户点击的是确定按钮');
     *     axios.get("/checkitem/delete.do?id=" + row.id).then((res)=> {
     */
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            checkItemService.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }


    /**
     *  //表单校验通过，发送请求
 axios.post("/checkitem/edit.do",this.formData).then((response)=> {
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){

        try{
            checkItemService.edit(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);

    }
    /**
     *   //发送请求获取检查项信息
     *   axios.get("/checkitem/findById.do?id=" + row.id).then((res)=>{
     *   用于编辑完之后回显数据
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
           CheckItem checkItem = checkItemService.findById(id);
           return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);

        }

    }
    /**
     *   //发送ajax请求查询所有检查项信息
     *   axios.get("/checkitem/findAll.do").then((res)=> {
     *
     */
    @RequestMapping("/findAll")
    public Result findAll(){

        try {
            List<CheckItem> checkItemlist = checkItemService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkItemlist);

        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }
}
