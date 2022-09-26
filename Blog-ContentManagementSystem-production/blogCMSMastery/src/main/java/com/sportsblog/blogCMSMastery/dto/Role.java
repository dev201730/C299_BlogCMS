package com.sportsblog.blogCMSMastery.dto;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * 
 * @author Benat Underwood
 * */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Role {
    private int roleId;
    private String role;

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", role='" + role + '\'' +
                '}';
    }

	@Override
	public int hashCode() {
		return Objects.hash(role, roleId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return Objects.equals(role, other.role) && roleId == other.roleId;
	}
    
    
}
