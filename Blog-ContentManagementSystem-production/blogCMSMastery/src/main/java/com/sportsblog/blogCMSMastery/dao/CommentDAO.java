package com.sportsblog.blogCMSMastery.dao;

import java.util.List;

import com.sportsblog.blogCMSMastery.dto.Blogpost;
import com.sportsblog.blogCMSMastery.dto.Comment;
import com.sportsblog.blogCMSMastery.dto.User;

/**
 * 
 * 
 * @author Eneinta Veliai
 * */
public interface CommentDAO {
    public Comment createComment (Comment comment);
    
    public List<Comment> readAllComments ();
    public Comment readCommentById (int id);
    User getUserForComment(int commentId);
    Blogpost getBlogpostForComment(int commentId);
    List<Comment> getCommentByBlogpostId(int blogpostId);
    
    public void updateComment (Comment comment);
    
    public void deleteComment (int id);
}
