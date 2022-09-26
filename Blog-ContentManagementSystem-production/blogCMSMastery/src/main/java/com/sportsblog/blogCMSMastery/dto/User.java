package com.sportsblog.blogCMSMastery.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;


/**
 * 
 * 
 * @author Tudor Coroian, Sreedevi Suresh
 * */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {
    private int userId;

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 20, message= "Invalid username: Please enter user between 1-20 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(max = 50, message= "Invalid password: Please enter password between 1-50 characters")
    private String password;

    @NotBlank(message = "FirstName cannot be blank")
    @Size(max = 20, message= "Invalid first name: Please enter first name between 1-20 characters")
    private String firstName;

    @NotBlank(message = "LastName cannot be blank")
    @Size(max = 20, message= "Invalid last name: Please enter last name between 1-20 characters")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Size(max = 20, message= "Invalid Email: Please enter Email between 1-20 characters")
    private String email;

    private boolean enable;
    private List<Role> roles;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", enable=" + enable +
                ", roles=" + roles +
                '}';
    }

	@Override
	public int hashCode() {
		return Objects.hash(email, enable, firstName, lastName, password, roles, userId, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && enable == other.enable
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password) && Objects.equals(roles, other.roles)
				&& userId == other.userId && Objects.equals(username, other.username);
	}
    
    
}
