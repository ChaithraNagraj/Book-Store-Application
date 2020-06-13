package com.bridgelabz.bookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.bridgelabz.bookstore.model.dto.RoleDTO;

@Entity
@Table(name = "role")
public class Role {
	@GenericGenerator(name = "role_id", strategy = "increment")
	@GeneratedValue(generator = "roleid")
	@Id
	private Long roleId;

	@Column(name = "role_name")
	private String role;

	public Role() {
	}

	public Role(RoleDTO req) {
		this.role = req.getRole();
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRole(String role) {
		return role;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + ", role=" + role + "]";
	}

}
