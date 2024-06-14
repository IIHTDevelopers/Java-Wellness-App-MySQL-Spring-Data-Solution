package com.wellnessapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellnessapp.dto.UserDTO;
import com.wellnessapp.entity.User;
import com.wellnessapp.exception.ResourceNotFoundException;
import com.wellnessapp.repo.UserRepository;
import com.wellnessapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDTO createUser(UserDTO userDto) {
		User user = new User();
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		User savedUser = userRepository.save(user);
		return new UserDTO(savedUser.getUsername(), savedUser.getEmail(), null);
	}

	@Override
	public UserDTO getUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		return new UserDTO(user.getUsername(), user.getEmail(), null);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		return userRepository.findAll().stream().map(user -> new UserDTO(user.getUsername(), user.getEmail(), null))
				.collect(Collectors.toList());
	}

	@Override
	public UserDTO updateUser(Long id, UserDTO userDto) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());
		User updatedUser = userRepository.save(user);
		return new UserDTO(updatedUser.getUsername(), updatedUser.getEmail(), null);
	}

	@Override
	public boolean deleteUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		userRepository.delete(user);
		return true;
	}
}