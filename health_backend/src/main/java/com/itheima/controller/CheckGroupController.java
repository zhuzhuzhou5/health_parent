package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 周科港
 * @title: CheckGroupController
 * @projectName health_parent
 * @date 2022.3.29  9:28
 */

/**
 * 	//检查项id数组（对应的模型数据为checkitemIds
"/checkgroup/add.do?checkitemIds=" + this.checkitemIds,
this.formData
 从前端接收俩参数
 checkitemIds
 formData
 */

/**
 * //默认切换到第一个标签页（基本信息）
 *   this.activeName='first';
 *   //重置
 *   this.checkitemIds = [];
 *   //发送ajax请求查询所有检查项信息
 *   axios.get("/checkitem/findAll.do").then((res)=> {
 *     if(res.data.flag){
 *       //将检查项列表数据赋值给模型数据用于页面表格展示
 *       this.tableData = res.data.data;
 *  因为上面，所有checkitemIds已拿到数据
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;


    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try{
            checkGroupService.add(checkGroup,checkitemIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }


    /**
     * //请求后台
     *   axios.post("/checkgroup/findPage.do",param).then((response)=> {
     *     //为模型数据赋值，基于VUE的双向绑定展示到页面
        分页查询
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

           return checkGroupService.pageQuery(queryPageBean);

    }
    /** 当用户点击编辑的时候，
     *   //发送ajax请求根据id查询检查组信息，用于基本信息回显
     *   axios.get("/checkgroup/findById.do?id=" + row.id).then((res)=>{
     *
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
           CheckGroup checkGroup = checkGroupService.findById(id);
           return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
    /**
     * //当用户点击检查项的时候
     *  axios.get("/checkgroup/findCheckItemIdsByCheckGroupId.do?id=" + row.id).then((res)=> {
    //为模型数据赋值，通过VUE数据双向绑定进行信息的回显
     */
    //根据检查组合id查询对应的所有检查项id
    //传过来是检查组id
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id){
        try{
            List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkItemIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
    /**
     *   //发送ajax请求，提交模型数据
     *   axios.post("/checkgroup/edit.do?checkitemIds="+this.checkitemIds,this.formData).
     *   编辑数据
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try {
            checkGroupService.edit(checkGroup,checkitemIds);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }
    // axios.get("/checkgroup/delete.do?id="+row.id).then((res)=>{
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try{
            checkGroupService.delete(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }
    // axios.get("/checkgroup/findAll.do").then((res)=> {
    @RequestMapping("/findAll")
    public Result findAll(){

        try {
            List<CheckGroup> checkGroupList = checkGroupService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroupList);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }

}
