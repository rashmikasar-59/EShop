package com.shoppingCart.service.user;

import com.shoppingCart.dto.UserDto;
import com.shoppingCart.model.User;
import com.shoppingCart.request.CreateUserRequest;
import com.shoppingCart.request.UserUpdateRequest;


public interface IUserService  {

	   User getUserById(Long userId);
	    User createUser(CreateUserRequest request);
	    User updateUser(UserUpdateRequest request, Long userId);
	    void deleteUser(Long userId);

	    UserDto convertUserToDto(User user);
	}

