package com.wellnessapp.service;

import java.util.List;

import com.wellnessapp.dto.UserDTO;

public interface UserService {
	UserDTO createUser(UserDTO user);

	UserDTO getUserById(Long id);

	List<UserDTO> getAllUsers();

	UserDTO updateUser(Long id, UserDTO user);

	boolean deleteUser(Long id);
}
