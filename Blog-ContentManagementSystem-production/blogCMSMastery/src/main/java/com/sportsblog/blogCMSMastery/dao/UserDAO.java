package com.sportsblog.blogCMSMastery.dao;

import java.util.List;

import com.sportsblog.blogCMSMastery.dto.Role;
import com.sportsblog.blogCMSMastery.dto.User;

/**
 * 
 * 
 * @author Tudor Coroian, Sreedevi Suresh
 * */
public interface UserDAO {
    public User createUser (User user);
    
    public List<User> readAllUsers ();
    public User readUserById (int id);
    List<Role> getRoleForUser(int userId);
    User getUserByUsername(String username);
    
    public void updateUser (User user);
    public void deleteUser (int id);
}
