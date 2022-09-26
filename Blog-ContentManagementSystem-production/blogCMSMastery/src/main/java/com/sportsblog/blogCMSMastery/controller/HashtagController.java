package com.sportsblog.blogCMSMastery.controller;

import com.sportsblog.blogCMSMastery.dao.BlogPostDAO;
import com.sportsblog.blogCMSMastery.dao.CommentDAO;
import com.sportsblog.blogCMSMastery.dao.HashtagDAO;
import com.sportsblog.blogCMSMastery.dao.RoleDAO;
import com.sportsblog.blogCMSMastery.dao.UserDAO;
import com.sportsblog.blogCMSMastery.dto.Blogpost;
import com.sportsblog.blogCMSMastery.dto.Hashtag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

/**
 * 
 * 
 * @author Aidan Loughran, Yu Lee 
 * */
@Controller
public class HashtagController {
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

    @PostMapping("/createCategory")
    public String createTag (HttpServletRequest request, Model model) {
        List<Hashtag> tagList = hashtagDao.readAllHashtags();
        model.addAttribute("tagList", tagList);

        Hashtag tag = new Hashtag();
        tag.setName(request.getParameter("tag"));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Hashtag>> errors = validate.validate(tag);
        model.addAttribute("errors", errors);

        if (!errors.isEmpty()) {
            List<Blogpost> staticList = blogpostDao.getBlogpostByType("static");
            model.addAttribute("staticList", staticList);

            return "categoryManager";
        }

        hashtagDao.createHashtag(tag);

        return "redirect:/categoryManager";
    }
    
    @GetMapping("/categoryManager")
    public String displayCategoryManager (Model model) {
        List<Blogpost> staticList = blogpostDao.getBlogpostByType("static");
        model.addAttribute("staticList", staticList);

        List<Hashtag> tagList = hashtagDao.readAllHashtags();
        model.addAttribute("tagList", tagList);

        return "categoryManager";
    }

    @GetMapping("/editCategory")
    public String editCategory(HttpServletRequest request, Model model) {
        List<Blogpost> staticList = blogpostDao.getBlogpostByType("static");
        model.addAttribute("staticList", staticList);

        int id = Integer.parseInt(request.getParameter("id"));
        Hashtag hashtag = hashtagDao.readHashtagById(id);

        model.addAttribute("hashtag", hashtag);

        return "editCategory";
    }

    @PostMapping("/editCategory")
    public String performEditUser(HttpServletRequest request, Model model) {

        int tagId = Integer.parseInt(request.getParameter("id"));
        Hashtag hashtag = hashtagDao.readHashtagById(tagId);
        hashtag.setName(request.getParameter("name"));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Hashtag>> errors = validate.validate(hashtag);
        model.addAttribute("errors", errors);

        if (!errors.isEmpty()) {

            List<Blogpost> staticList = blogpostDao.getBlogpostByType("static");
            model.addAttribute("staticList", staticList);

            hashtag.setName(request.getParameter("name"));

            model.addAttribute("hashtag", hashtag);

            return "editCategory";
        }

        hashtagDao.updateHashtag(hashtag);

        return "redirect:/categoryManager";
    }

    @GetMapping("deleteCategory")
    public String deleteTag (HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        hashtagDao.deleteHashtag(id);

        List<Hashtag> tagList = hashtagDao.readAllHashtags();
        model.addAttribute("tagList", tagList);

        return "redirect:/categoryManager";
    }


}

