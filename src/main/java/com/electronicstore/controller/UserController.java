package com.electronicstore.controller;


import com.electronicstore.Dto.PageableResponse;
import com.electronicstore.Dto.UserDto;
import com.electronicstore.service.UserServiceI;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        log.info("Entering controller for create user");

        UserDto user = this.userServiceI.createUser(userDto);
        log.info("Complate");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> UpdateUser(@RequestBody UserDto userDto, @PathVariable("userId") String userId) {

        UserDto updatedUser = this.userServiceI.updateUser(userDto, userId);

        return ResponseEntity.ok(updatedUser);

    }

    @GetMapping("/")
    public ResponseEntity<PageableResponse> getAllUsers(

            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "2", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "userName", required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc", required = false) String direction) {
        log.info("Enter the  request for get all users  ");
        PageableResponse allUsers = this.userServiceI.getAllUser(pageNumber, pageSize, sortBy, direction);
        log.info("Completed the request for get all users  ");
        return new ResponseEntity<PageableResponse>(allUsers, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserDto> DeleteUser(@PathVariable("userId") String userId) {
        log.info("Entering the request for delete users");
        this.userServiceI.DeleteUser(userId);

        return new ResponseEntity<UserDto>(HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> getUserContaing(@PathVariable String keyword) {

        log.info("Entering the request for get user containg");
        List<UserDto> userDtos = this.userServiceI.searchUser(keyword);

        log.info("complate the requset for get user containg ");


        return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        log.info("Enter the  request for get the user with EmailId :{} ", email);
        UserDto userByEmailId = this.userServiceI.getUserByEmailId(email);
        log.info("Completed the  request for get the user with EmailId :{} ", email);
        return new ResponseEntity<UserDto>(userByEmailId, HttpStatus.OK);
    }

    @GetMapping("/email/{email}/pass/{password}")
    public ResponseEntity<UserDto> getUserByEmailAndPass(@PathVariable String email, @PathVariable String password) {

        log.info("Enter the  request for get the user with EmailId And Password :{} :{} ", email, password);
        UserDto userByEmailAndPassword = this.userServiceI.getByUserEmailAndUserPassword(email, password);
        log.info("Completed the  request for get the user with EmailId And Password :{} :{} ", email, password);
        return new ResponseEntity<UserDto>(userByEmailAndPassword,HttpStatus.OK);

    }











    }
