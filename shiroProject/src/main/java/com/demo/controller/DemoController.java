package com.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    @RequestMapping(value="demo/index", method=RequestMethod.GET)
	@ResponseBody
	public String index(){
        System.out.println( "DemoController index..." );
        return "DemoController index";
    }

}
