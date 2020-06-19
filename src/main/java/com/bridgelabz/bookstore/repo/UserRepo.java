package com.bridgelabz.bookstore.repo;

import java.util.List;
import java.util.Optional;

import com.bridgelabz.bookstore.model.Role;
import com.bridgelabz.bookstore.model.User;

public interface UserRepo {
	public void addUser(User user);

	public User findByUserId(Long id);

	public List<User> getUser();

	public User update(User user, Long id);

	public void delete(Long id);

	public void updateDateTime(Long id);

	public void updatePassword(Long id, String password);

	public void verify(Long id);

	public User getusersByemail(String email);

	public List<User> findByEmail(String email);

	public Role findByRoleId(Long parseLong);

	public void saveRoles(Role role);

	public void updateUserStatus(Boolean userStatus, Long id);

	public void saveImageUrl(String imageUrl, Long id);
	
	public void userMerge(User user);

	public User findByUserIdAndRoleId(Long userId, Long roleId);

}
