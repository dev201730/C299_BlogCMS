package com.sportsblog.blogCMSMastery.dao;

import java.util.List;

import com.sportsblog.blogCMSMastery.dto.Hashtag;

/**
 * 
 * 
 * @author Aidan Loughran, Yu Lee 
 * */
public interface HashtagDAO {
    public Hashtag createHashtag (Hashtag hashtag);
    
    public List<Hashtag> readAllHashtags ();
    public Hashtag readHashtagById (int id);
    
    public void updateHashtag (Hashtag hashtag);
    
    public void deleteHashtag(int id);
}
