package com.itheima.controller;

/**
 * @author 周科港
 * @title: SetmealController
 * @projectName health_parent
 * @date 2022.3.29  15:16
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * <el-upload
class="avatar-uploader"
action="/setmeal/upload.do"
:auto-upload="autoUpload"
name="imgFile"
:show-file-list="false"
:on-success="handleAvatarSuccess"
:before-upload="beforeAvatarUpload">
 */
@RestController
@RequestMapping("setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    //文件上传
    /**
     * 做文件上传的操作需要配置一个组件，已配好
     * 在参数那声明 MultipartFile 用RequestParam获取知道的上传的文件
     @RequestParam获取前端指定请求参数并赋值给形参
     拿到前端上传文件的数据通过七牛云上传到服务器
     */


    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile")MultipartFile imgFile) {
        System.out.println(imgFile);
        //2.要想获取后缀，先获取原始文件名
        String originalFilename = imgFile.getOriginalFilename();
        //3.获取最后一个点
        int index = originalFilename.lastIndexOf(".");
        //4.从最后一个点开始截器文件名
        String extention = originalFilename.substring(index - 1);//.jpg
        //1.给图片文件起名并且不重复，此时没有后缀
        String fileName  = UUID.randomUUID().toString() + extention;

        try {
            //将文件上传到七牛云服务器
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //将文件上传到七牛云服务器的同时也上传到Redis中，为了方便清理垃圾图片
            //指定上传到那个集合，上传文件的文件名
            //到这里就完成第一项
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        //上传成功并且返回数据给前端
        return  new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
    }
    //axios.post("/setmeal/add.do?checkgroupIds=" + this.checkgroupIds,this.formData).
    //  then((response)=> {
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * //请求后台
     *   axios.post("/setmeal/findPage.do",param).then((response)=>
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
           return setmealService.pageQuery(queryPageBean);
    }
    /**
   axios.get("/setmeal/delete.do?id="+row.id).then((res)=>{
     */
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            setmealService.delete(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();

            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }
}
