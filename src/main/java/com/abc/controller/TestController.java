package com.abc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by uEngineYBS on 2016-09-20.
 */
@Controller
public class TestController {

    @RequestMapping(value = "test.do", method = RequestMethod.GET)
    public String getJsp() {

        return "test";
    }
}
