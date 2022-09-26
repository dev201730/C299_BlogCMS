package com.sportsblog.blogCMSMastery.controller;

import com.sportsblog.blogCMSMastery.dao.BlogPostDAO;
import com.sportsblog.blogCMSMastery.dao.CommentDAO;
import com.sportsblog.blogCMSMastery.dao.HashtagDAO;
import com.sportsblog.blogCMSMastery.dao.RoleDAO;
import com.sportsblog.blogCMSMastery.dao.UserDAO;
import com.sportsblog.blogCMSMastery.dto.Blogpost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 
 * 
 * @author Yu Lee
 * */
@Controller
public class LoginController {
    @Autowired
    RoleDAO roleDao;

    @Autowired
    UserDAO userDao;

    @Autowired
    BlogPostDAO blogpostDao;

    @Autowired
    HashtagDAO hashtagDao;

    @Autowired
    CommentDAO commentDao;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        List<Blogpost> staticList = blogpostDao.getBlogpostByType("static");
        model.addAttribute("staticList", staticList);
        return "login";
    }
}
