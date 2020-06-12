package com.bridgelabz.bookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bridgelabz.bookstore.model.dto.RoleDTO;

@Entity
@Table(name = "role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "roleId")
	private Long roleId;

	@Column(name = "role_name")
	private String role;

	public Role() {
	}

	public Role(RoleDTO req) {
		this.role = req.getRole();
	}
	/*
	 * @OneToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	 * 
	 * @JoinTable(name="userData", joinColumns={@JoinColumn(name="roleId")} ,
	 * inverseJoinColumns={@JoinColumn(name="id")})
	 * 
	 * @JsonBackReference
	 * 
	 * @JsonIgnore
	 */

	/*
	 * @JsonIgnore
	 * 
	 * @OneToMany(cascade = CascadeType.ALL)
	 * 
	 * @JoinColumn(name = "role_id") private List<User> user;
	 */

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	/*
	 * public List<User> getUser() { return user; }
	 * 
	 * public void setUser(List<User> user) { this.user = user; }
	 */
	@Override
	public String toString() {
		return "Role [roleId=" + ", role=" + role + "]";
	}

	public String getRole(String role) {

		return role;
	}

}
