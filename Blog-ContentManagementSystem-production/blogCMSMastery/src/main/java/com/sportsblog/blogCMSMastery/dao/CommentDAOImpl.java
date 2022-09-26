package com.sportsblog.blogCMSMastery.dao;

import com.sportsblog.blogCMSMastery.dto.Blogpost;
import com.sportsblog.blogCMSMastery.dto.Comment;
import com.sportsblog.blogCMSMastery.dto.Hashtag;
import com.sportsblog.blogCMSMastery.dto.Role;
import com.sportsblog.blogCMSMastery.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 
 * 
 * @author Eneinta Veliai
 * */
@Repository
public class CommentDAOImpl implements CommentDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Comment createComment(Comment comment) {
        final String INSERT_COMMENT = "INSERT INTO BLOGCOMMENTS (timePosted, commentsContent, userId, blogpostId) VALUES (?,?,?,?)";
        jdbc.update(INSERT_COMMENT, comment.getTimePosted(), comment.getContent(), comment.getUser().getUserId(), comment.getBlogpost().getBlogpostId());

        int newId = jdbc.queryForObject("SELECT LAST()", Integer.class);
        comment.setCommentId(newId);

        return comment;
    }

    @Override
    public List<Comment> readAllComments() {
        final String SELECT_ALL_COMMENT = "SELECT * FROM BLOGCOMMENTS";
        List<Comment> commentList = jdbc.query(SELECT_ALL_COMMENT, new CommentMapper());

        associateUserComment(commentList);
        associateBlogpostComment(commentList);

        return commentList;
    }

    @Override
    public Comment readCommentById(int id) {
        final String SELECT_COMMENT_BY_ID = "SELECT * FROM BLOGCOMMENTS WHERE commentId = ?";
        Comment comment = jdbc.queryForObject(SELECT_COMMENT_BY_ID, new CommentMapper(), id);

        comment.setUser(getUserForComment(comment.getCommentId()));
        comment.setBlogpost(getBlogpostForComment(comment.getCommentId()));

        return comment;
    }

    @Override
    public User getUserForComment(int commentId) {
        final String SELECT_USER_BY_COMMENT_ID = "SELECT u.* FROM USERS u " +
                "JOIN BLOGCOMMENTS c ON u.userId = c.userId " +
                "WHERE c.commentId = ?";
        User user = jdbc.queryForObject(SELECT_USER_BY_COMMENT_ID, new UserDAOImpl.UserMapper(), commentId);
        user.setRoles(getRoleForUser(user.getUserId()));
        return user;
    }

    @Override
    public Blogpost getBlogpostForComment(int commentId) {
        final String SELECT_BLOGPOST_BY_COMMENT_ID = "SELECT b.* FROM BLOGPOSTS b " +
                "JOIN BLOGCOMMENTS cm ON b.blogpostId = cm.blogpostId " +
                "WHERE cm.commentId = ?";
        Blogpost blogpost = jdbc.queryForObject(SELECT_BLOGPOST_BY_COMMENT_ID, new BlogPostDAOImpl.BlogpostMapper(), commentId);
        blogpost.setUser(getUserForBlogpost(blogpost.getBlogpostId()));
        blogpost.setHashtags(getTagsForBlogpost(blogpost.getBlogpostId()));
        return blogpost;
    }

    public List<Role> getRoleForUser(int userId) {
        final String SELECT_ROLE_BY_USER_ID = "SELECT r.* FROM ROLES r " +
                "JOIN user_role ur ON r.roleId = ur.roleId " +
                "WHERE ur.userId = ?";
        return jdbc.query(SELECT_ROLE_BY_USER_ID, new RoleDAOImpl.RoleMapper(), userId);
    }

    public User getUserForBlogpost(int blogpostId) {
        final String SELECT_USER_BY_BLOGPOST_ID = "SELECT u.* FROM USERS u " +
                "JOIN BLOGPOSTS b ON u.userId = b.userId " +
                "WHERE b.blogpostId = ?";
        User user = jdbc.queryForObject(SELECT_USER_BY_BLOGPOST_ID, new UserDAOImpl.UserMapper(), blogpostId);
        user.setRoles(getRoleForUser(user.getUserId()));
        return user;
    }

    public List<Hashtag> getTagsForBlogpost(int blogpostId) {
        final String SELECT_TAG_BY_BLOGPOST_ID = "SELECT h.* FROM HASHTAGS h " +
                "JOIN blogpost_hashtag bh ON h.hashtagId = bh.hashtagId " +
                "WHERE bh.blogpostId = ?";
        return jdbc.query(SELECT_TAG_BY_BLOGPOST_ID, new HashtagDAOImpl.HashtagMapper(), blogpostId);
    }

    @Override
    public List<Comment> getCommentByBlogpostId(int blogpostId) {
        final String SELECT_COMMENT_BY_BLOGPOST = "SELECT * FROM BLOGCOMMENTS WHERE blogpostId = ?";
        List<Comment> commentList =  jdbc.query(SELECT_COMMENT_BY_BLOGPOST, new CommentMapper(), blogpostId);
        associateBlogpostComment(commentList);
        associateUserComment(commentList);

        return commentList;
    }
    
    @Override
    @Transactional
    public void updateComment(Comment comment) {
        final String UPDATE_COMMENT = "UPDATE BLOGCOMMENTS SET " +
                "timePosted = ?, " +
                "commentsContent = ?, " +
                "userId = ?, " +
                "blogpostId = ? " +
                "WHERE commentId = ?";
        jdbc.update(UPDATE_COMMENT,  comment.getTimePosted(), comment.getContent(), comment.getUser().getUserId(), comment.getBlogpost().getBlogpostId(), comment.getCommentId());
    }

    @Override
    @Transactional
    public void deleteComment(int id) {
        final String DELETE_COMMENT = "DELETE FROM BLOGCOMMENTS WHERE commentId = ?";
        jdbc.update(DELETE_COMMENT, id);
    }
    
    private void associateUserComment(List<Comment> commentList) {
        for(Comment comment : commentList) {
            User user = getUserForComment(comment.getCommentId());
            comment.setUser(user);
        }
    }
    
    private void associateBlogpostComment(List<Comment> commentList) {
        for(Comment comment : commentList) {
            Blogpost blogpost = getBlogpostForComment(comment.getCommentId());
        }
    }

    public class CommentMapper implements RowMapper<Comment> {
        @Override
        public Comment mapRow(ResultSet resultSet, int i) throws SQLException {
            Comment comment = new Comment();

            comment.setCommentId(resultSet.getInt("commentId"));
            comment.setTimePosted(resultSet.getTimestamp("timePosted").toLocalDateTime());
            comment.setContent(resultSet.getString("commentsContent"));

            return comment;
        }

    }
}
