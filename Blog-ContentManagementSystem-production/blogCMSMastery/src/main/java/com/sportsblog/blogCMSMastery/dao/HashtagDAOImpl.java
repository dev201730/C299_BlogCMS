package com.sportsblog.blogCMSMastery.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sportsblog.blogCMSMastery.dto.Hashtag;
import com.sportsblog.blogCMSMastery.dto.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 
 * 
 * @author Aidan Loughran, Yu Lee 
 * */
@Repository
public class HashtagDAOImpl implements HashtagDAO {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Hashtag createHashtag(Hashtag hashtag) {
        final String INSERT_TAG = "INSERT INTO HASHTAGS (hashtagName) VALUES (?)";
        jdbc.update(INSERT_TAG, hashtag.getName());

        int newId = jdbc.queryForObject("SELECT LAST()", Integer.class);
        hashtag.setHashtagId(newId);

        return hashtag;
    }

    @Override
    public List<Hashtag> readAllHashtags() {
        final String SELECT_ALL_TAG = "SELECT * FROM HASHTAGS";
        return jdbc.query(SELECT_ALL_TAG, new HashtagMapper());
    }

    @Override
    public Hashtag readHashtagById(int id) {
        try {
            final String SELECT_TAG_BY_ID = "SELECT * FROM HASHTAGS WHERE hashtagId = ?";
            return jdbc.queryForObject(SELECT_TAG_BY_ID, new HashtagMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public void updateHashtag(Hashtag hashtag) {
        final String UPDATE_TAG = "UPDATE HASHTAGS SET hashtagName = ? WHERE hashtagId = ?";
        jdbc.update(UPDATE_TAG, hashtag.getName(), hashtag.getHashtagId());
    }

    @Override
    @Transactional
    public void deleteHashtag(int id) {
        final String DELETE_BLOGPOST_TAG = "DELETE FROM blogpost_hashtag WHERE hashtagId = ?";
        jdbc.update(DELETE_BLOGPOST_TAG, id);

        final String DELETE_TAG = "DELETE FROM HASHTAGS WHERE hashtagId = ?";
        jdbc.update(DELETE_TAG, id);
    }

    public static class HashtagMapper implements RowMapper<Hashtag> {
        @Override
        public Hashtag mapRow(ResultSet resultSet, int i) throws SQLException {

            Hashtag tag = new Hashtag();

            tag.setHashtagId(resultSet.getInt("hashtagId"));
            tag.setName(resultSet.getString("hashtagName"));

            return tag;
        }
    }
}
