package com.electronicstore.service;

import com.electronicstore.Dto.PageableResponse;
import com.electronicstore.Dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserServiceI {

    UserDto createUser (UserDto userDto);

    UserDto updateUser (UserDto userDto,  String userId);

    UserDto getUserById(String userId);

    PageableResponse getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String direction);


    void DeleteUser(String userId);

    UserDto getUserByEmailId(String email);

    List<UserDto> searchUser(String keyword);

   UserDto getByUserEmailAndUserPassword(String email,String password);


    UserDto getSingleUser(String userId);
}
