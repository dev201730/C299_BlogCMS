package com.sportsblog.blogCMSMastery.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 
 * 
 * @author Aidan Loughran, Yu Lee 
 * */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Hashtag {
    private int hashtagId;
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 20, message= "Invalid category name: Please enter category name between 1-20 characters")
    private String name;

    @Override
    public String toString() {
        return "Hashtag{" +
                "hashtagId=" + hashtagId +
                ", name='" + name + '\'' +
                '}';
    }

	@Override
	public int hashCode() {
		return Objects.hash(hashtagId, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hashtag other = (Hashtag) obj;
		return hashtagId == other.hashtagId && Objects.equals(name, other.name);
	}
    
    
}
