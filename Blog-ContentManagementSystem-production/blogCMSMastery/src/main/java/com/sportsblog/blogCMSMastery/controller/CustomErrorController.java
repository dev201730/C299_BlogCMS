package com.sportsblog.blogCMSMastery.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 
 * @author Silviu Badica
 * */
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error() {
        return "customError";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

}
