package com.sportsblog.blogCMSMastery.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 
 * 
 * @author Scott Hollas, Tamas Maul
 * */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Blogpost {
    private int blogpostId;

    private LocalDateTime timePosted;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message= "Invalid title: Please enter title between 1-100 characters")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    @NotBlank(message = "Type cannot be blank")
    private String type;

    @NotBlank(message = "Status cannot be blank")
    private String status;

    private String photoFileName;

    private User user;

    @NotNull(message = "You need to choose at least one category")
    private List<Hashtag> hashtags;
    
    @Override
    public String toString() {
        return "Blogpost{" +
                "blogpostId=" + blogpostId +
                ", timePosted=" + timePosted +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", photoFileName='" + photoFileName + '\'' +
                ", user=" + user +
                ", hashtags=" + hashtags +
                '}';
    }

	@Override
	public int hashCode() {
		return Objects.hash(blogpostId, content, hashtags, photoFileName, status, timePosted, title, type, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Blogpost other = (Blogpost) obj;
		return blogpostId == other.blogpostId && Objects.equals(content, other.content)
				&& Objects.equals(hashtags, other.hashtags) && Objects.equals(photoFileName, other.photoFileName)
				&& Objects.equals(status, other.status) && Objects.equals(timePosted, other.timePosted)
				&& Objects.equals(title, other.title) && Objects.equals(type, other.type)
				&& Objects.equals(user, other.user);
	}
}
