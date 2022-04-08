package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 周科港
 * @title: UserController
 * @projectName health_parent
 * @date 2022.3.30  14:26
 */

/**
  created(){
     //发送请求获取当前登录用户的用户名
      axios.get('/user/getUsername.do').then((response)=>{
 */
@RestController
@RequestMapping("/user")
public class UserController {

    //获取当前登录用户的用户名
    @RequestMapping("/getUsername")
    public Result getUsername(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user);
        if (user != null){
            String username = user.getUsername();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,username);
        }
        return new Result(false,MessageConstant.GET_USERNAME_FAIL);
    }

}
