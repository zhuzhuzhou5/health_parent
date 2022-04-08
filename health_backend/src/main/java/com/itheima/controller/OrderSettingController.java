package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 周科港
 * @title: OrderSettingController
 * @projectName health_parent
 * @date 2022.3.29  21:03
 */

/**
 * <el-upload action="/ordersetting/upload.do"
             name="excelFile"
             :show-file-list="false"
             :on-success="handleSuccess"
             :before-upload="beforeUpload">
 *  <el-button type="primary">上传文件</el-button>
 * </el-upload>
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {


    @Reference
    private OrderSettingService orderSettingService;

    /**
     * Excel文件上传，并解析文件内容保存到数据库
     * //文件上传，实现预约设置数据批量导入
     * @param excelFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile")MultipartFile excelFile){
            try {
                /**
                 * @RequestParam("excelFile")MultipartFile excelFile
                 * 通过上面这句获取前端发送过来的文件，然后用工具解析表格数据
                 * 解析完之后把数据装到集合里面去
                 */
                List<String[]> list = POIUtils.readExcel(excelFile);
                //把数据保存到数据库，下面这句就要调用接口了
                // List<String[]> 这种类型的数据不符合,不直观
                //需要处理成List<OrderSetting>这种格式

                List<OrderSetting> data = new ArrayList<>();
                for (String[] strings : list) {

                    //看表格和看OrderSetting去理解，
                    // 每一个数组对象对应一条数据，每一条数据就要封装成实体类
                    //获取表格中的日期
                    String orderDate = strings[0];
                    //获取表格中可预约人数的的数量，具体看OrderSetting实体类
                    String number = strings[1];
                    //因为一个是日期类型，一个是int类型，所有在参数里面转换类型
                    OrderSetting orderSetting = new OrderSetting(new Date(orderDate), Integer.parseInt(number));
                    data.add(orderSetting);
                }

                //通过dubbo远程调用服务实现数据批量导入
                orderSettingService.add(data);
                return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);

            } catch (Exception e) {
                e.printStackTrace();
                //文件解析失败
                return new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
            }
    }
    /**
     * this.leftobj = [
    { date: 1, number: 120, reservations: 1 },
    { date: 3, number: 120, reservations: 1 },
    { date: 4, number: 120, reservations: 120 },
    { date: 6, number: 120, reservations: 1 },
    { date: 8, number: 120, reservations: 1 }
     axios.post(
     "/ordersetting/getOrderSettingByMonth.do?date="+this.currentYear+'-'+
     this.currentMonth
     * private Date orderDate;//预约设置日期
     * 这个日期格式是年月日类型
     *    而页面想要的是上面类型数据，显然不符合要求，
     *    格式对应不上。所以用map,而且比较灵活
     *    注意date
     *    注意date
     *    注意date
     *    注意date
     *    你又写成data错的
     */
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){//参数格式为2019-03
        try {
            List<Map> orderSettingList = orderSettingService.getOrderSettingByMonth(date);
            //获取预约设置数据成功
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,orderSettingList);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }

    }
    /**
//发送ajax请求根据日期修改可预约人数
  axios.post("/ordersetting/editNumberByDate.do",{
    orderDate:this.formatDate(day.getFullYear(),day.getMonth()+1,day.getDate()), //日期
     */
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try{
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_SUCCESS);
        }
    }
}
