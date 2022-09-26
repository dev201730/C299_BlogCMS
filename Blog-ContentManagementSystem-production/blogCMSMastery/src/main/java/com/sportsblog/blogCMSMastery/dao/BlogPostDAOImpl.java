package com.sportsblog.blogCMSMastery.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sportsblog.blogCMSMastery.dto.Blogpost;
import com.sportsblog.blogCMSMastery.dto.Hashtag;
import com.sportsblog.blogCMSMastery.dto.Role;
import com.sportsblog.blogCMSMastery.dto.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author Scott Hollas, Tamas Maul
 * */
@Repository
public class BlogPostDAOImpl implements BlogPostDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Blogpost createBlogpost(Blogpost blogpost) {
        final String INSERT_BLOGPOST = "INSERT INTO BLOGPOSTS (timePosted, blogTitle, blogType, blogStatus, blogPhoto, blogContent, userId) VALUES (?,?,?,?,?,?,?)";
        jdbc.update(INSERT_BLOGPOST, blogpost.getTimePosted(), blogpost.getTitle(), blogpost.getType(), blogpost.getStatus(), blogpost.getContent(), blogpost.getPhotoFileName(), blogpost.getUser().getUserId());

        int newId = jdbc.queryForObject("SELECT LAST()", Integer.class);
        blogpost.setBlogpostId(newId);

        insertIntoBlogpostTag(blogpost);

        return blogpost;
    }

    @Override
    public List<Blogpost> readAllBlogposts() {
        final String SELECT_ALL_BLOGPOSTS = "SELECT * FROM BLOGPOSTS";
        List<Blogpost> blogpostList = jdbc.query(SELECT_ALL_BLOGPOSTS, new BlogpostMapper());
        associateUserBlogpost(blogpostList);
        associateBlogpostTag(blogpostList);

        return blogpostList;
    }

    @Override
    public Blogpost readBlogpostById(int id) {
        final String SELECT_BLOGPOST_BY_ID = "SELECT * FROM BLOGPOSTS WHERE blogpostId = ?";
        Blogpost blogpost = jdbc.queryForObject(SELECT_BLOGPOST_BY_ID, new BlogpostMapper(), id);

        blogpost.setUser(getUserForBlogpost(blogpost.getBlogpostId()));
        blogpost.setHashtags(getTagsForBlogpost(blogpost.getBlogpostId()));

        return blogpost;
    }

    @Override
    public User getUserForBlogpost(int blogpostId) {
        final String SELECT_USER_BY_BLOGPOST_ID = "SELECT * FROM USERS u " +
                "JOIN BLOGPOSTS b ON u.userId = b.userId " +
                "WHERE b.blogpostId = ?";
        User user = jdbc.queryForObject(SELECT_USER_BY_BLOGPOST_ID, new UserDAOImpl.UserMapper(), blogpostId);
        user.setRoles(getRoleForUser(user.getUserId()));
        return user;
    }

    @Override
    public List<Hashtag> getTagsForBlogpost(int blogpostId) {
        final String SELECT_TAG_BY_BLOGPOST_ID = "SELECT h.* FROM HASHTAGS h " +
                "JOIN blogpost_hashtag bh ON h.hashtagId = bh.hashtagId " +
                "WHERE bh.blogpostId = ?";
        return jdbc.query(SELECT_TAG_BY_BLOGPOST_ID, new HashtagDAOImpl.HashtagMapper(), blogpostId);
    }

    public List<Role> getRoleForUser(int userId) {
        final String SELECT_ROLE_BY_USER_ID = "SELECT * FROM ROLES r " +
                "JOIN user_role ur ON r.roleId = ur.roleId " +
                "WHERE ur.userId = ?";
        return jdbc.query(SELECT_ROLE_BY_USER_ID, new RoleDAOImpl.RoleMapper(), userId);
    }

    @Override
    public List<Blogpost> getBlogpostByType(String type) {
        List<Blogpost> blogpostList = this.readAllBlogposts();
        List<Blogpost> typeList = new ArrayList<Blogpost>();
        for (Blogpost blogpost : blogpostList) {
            if (blogpost.getType().equals(type) && blogpost.getStatus().equals("public")) {
                typeList.add(blogpost);
            }
        }
        return typeList;
    }

    @Override
    public List<Blogpost> getBlogpostByTag(int tagId) {
        final String SELECT_BLOGPOST_BY_TAG = "SELECT b.* FROM BLOGPOSTS b " +
                "JOIN blogpost_hashtag bh ON bh.blogpostId = b.blogpostId " +
                "WHERE bh.hashtagId = ?";
        List<Blogpost> blogpostList =  jdbc.query(SELECT_BLOGPOST_BY_TAG, new BlogpostMapper(), tagId);
        associateBlogpostTag(blogpostList);
        associateUserBlogpost(blogpostList);

        List<Blogpost> sortedPublicList = new ArrayList<Blogpost>();
        for (Blogpost blogpost : blogpostList) {
            if (blogpost.getStatus().equals("public")) {
                sortedPublicList.add(blogpost);
            }
        }
        return sortedPublicList;
    }

    @Override
    public List<Blogpost> getBlogpostBySearchTitle(String searchText) {
        searchText = "%" + searchText + "%";
        final String SELECT_BLOGPOST_BY_SEARCH_TITLE = "SELECT * FROM BLOGPOSTS WHERE blogTitle LIKE ?";
        List<Blogpost> blogpostList = jdbc.query(SELECT_BLOGPOST_BY_SEARCH_TITLE, new BlogpostMapper(), searchText);
        associateBlogpostTag(blogpostList);
        associateUserBlogpost(blogpostList);

        List<Blogpost> sortedPublicList = new ArrayList<Blogpost>();
        for (Blogpost blogpost : blogpostList) {
            if (blogpost.getStatus().equals("public")) {
                sortedPublicList.add(blogpost);
            }
        }
        return sortedPublicList;
    }

    @Override
    @Transactional
    public void updateBlogpost(Blogpost blogpost) {
        final String UPDATE_BLOGPOST = "UPDATE BLOGPOSTS SET " +
                "timePosted = ?, " +
                "blogTitle = ?, " +
                "blogType = ?, " +
                "blogStatus = ?, " +
                "blogPhoto = ?, " +
                "blogContent = ?, " +
                "userId = ? " +
                "WHERE blogpostId = ?";
        jdbc.update(UPDATE_BLOGPOST, blogpost.getTimePosted(), blogpost.getTitle(), blogpost.getType(), blogpost.getStatus(), blogpost.getPhotoFileName(), blogpost.getContent(), blogpost.getUser().getUserId(), blogpost.getBlogpostId());

        final String DELETE_BLOGPOST_TAG = "DELETE FROM blogpost_hashtag WHERE blogpostId = ?";
        jdbc.update(DELETE_BLOGPOST_TAG, blogpost.getBlogpostId());

        insertIntoBlogpostTag(blogpost);

    }

    @Override
    @Transactional
    public void deleteBlogpost(int id) {
        final String DELETE_BLOGPOST_TAG = "DELETE FROM blogpost_hashtag WHERE blogpostId = ?";
        jdbc.update(DELETE_BLOGPOST_TAG, id);

        final String DELETE_COMMENT = "DELETE FROM BLOGCOMMENTS WHERE blogpostId = ?";
        jdbc.update(DELETE_COMMENT, id);

        final String DELETE_BLOGPOST = "DELETE FROM BLOGPOSTS WHERE blogpostId = ?";
        jdbc.update(DELETE_BLOGPOST, id);
    }
    
    private void insertIntoBlogpostTag (Blogpost blogpost) {
        for(Hashtag tag : blogpost.getHashtags()) {
            final String INSERT_BLOGPOST_TAG = "INSERT INTO blogpost_hashtag (blogpostId, hashtagId) VALUES (?,?)";
            jdbc.update(INSERT_BLOGPOST_TAG, blogpost.getBlogpostId(), tag.getHashtagId());
        }
    }
    
    private void associateUserBlogpost(List<Blogpost> blogpostList) {
        for (Blogpost blogpost : blogpostList) {
            User user = getUserForBlogpost(blogpost.getBlogpostId());
            blogpost.setUser(user);
        }
    }
    
    private void associateBlogpostTag(List<Blogpost> blogpostList) {
        for (Blogpost blogpost : blogpostList) {
            List<Hashtag> tagList = getTagsForBlogpost(blogpost.getBlogpostId());
            blogpost.setHashtags(tagList);
        }
    }

    public static class BlogpostMapper implements RowMapper<Blogpost> {
        @Override
        public Blogpost mapRow(ResultSet resultSet, int i) throws SQLException {
            Blogpost blogpost = new Blogpost();

            blogpost.setBlogpostId(resultSet.getInt("blogpostId"));
            blogpost.setTimePosted(resultSet.getTimestamp("timePosted").toLocalDateTime());
            blogpost.setTitle(resultSet.getString("blogTitle"));
            blogpost.setType(resultSet.getString("blogType"));
            blogpost.setStatus(resultSet.getString("blogStatus"));
            blogpost.setPhotoFileName(resultSet.getString("blogPhoto"));
            blogpost.setContent(resultSet.getString("blogContent"));

            return blogpost;
        }
    }
}
