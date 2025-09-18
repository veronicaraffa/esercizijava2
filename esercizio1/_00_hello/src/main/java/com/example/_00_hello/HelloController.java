package com.example._00_hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class HelloController
{
    //localhost:8080/hellostart
    @RequestMapping (path = "/hellostart")
    public @ResponseBody
    String hellostart() {
        return "Hello World ;-)";
    }

    //localhost:8080/hellostart
    @RequestMapping (path = "/hellostart/{name}")
    public @ResponseBody
    String hellostart(@PathVariable String name) {
        return "Hello World " + name + "!!";
    }
}
