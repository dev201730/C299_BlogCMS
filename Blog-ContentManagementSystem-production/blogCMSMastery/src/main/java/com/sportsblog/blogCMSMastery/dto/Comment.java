package com.sportsblog.blogCMSMastery.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * 
 * @author Eneinta Veliai
 * */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Comment {
    private int commentId;
    private LocalDateTime timePosted;
    private String content;
    private User user;
    private Blogpost blogpost;

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", timePosted=" + timePosted +
                ", comment='" + content + '\'' +
                ", user=" + user +
                ", blogpost=" + blogpost +
                '}';
    }

	@Override
	public int hashCode() {
		return Objects.hash(blogpost, commentId, content, timePosted, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		return Objects.equals(blogpost, other.blogpost) && commentId == other.commentId
				&& Objects.equals(content, other.content) && Objects.equals(timePosted, other.timePosted)
				&& Objects.equals(user, other.user);
	}
}
