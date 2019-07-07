package com.ycool.webservice;

import com.ycool.exception.MyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Controller
public class WebServiceController {

    @Value("${com.ycool.demo.name}")
    private String mode;

    @RequestMapping("/getRValue")
    public String getRandomValue()throws Exception{
//        return "Random value is:" + this.value;
//        return "error";
        throw new Exception("参数错误");
    }

    @RequestMapping("/getRunMode")
    public String getRunMode(){
        return mode;
    }

    @RequestMapping("/json")
    public String getJson()throws MyException{
        throw new MyException("参数错误");
    }

    @RequestMapping("/getHello")
    public String hello(){
        return "Hello World!";
    }

    @RequestMapping("/index")
    public String index(ModelMap map){
        map.addAttribute("host","https://www.google.com");
        return  "index";

    }

    public void setMode(String mode) {
        this.mode = mode;
    }

}
