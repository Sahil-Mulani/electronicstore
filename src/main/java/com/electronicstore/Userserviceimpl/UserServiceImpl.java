package com.electronicstore.Userserviceimpl;

import com.electronicstore.Dto.PageableResponse;
import com.electronicstore.Dto.UserDto;
import com.electronicstore.Enitity.User;
import com.electronicstore.Helper.Helper;
import com.electronicstore.Repository.UserRepo;
import com.electronicstore.exception.ResourceNotFoundException;
import com.electronicstore.service.UserServiceI;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserServiceI {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Entering the Dao for Creating User");

        String str = UUID.randomUUID().toString();
        userDto.setUserId(str);
        User user = this.modelMapper.map(userDto, User.class);
        this.userRepo.save(user);
        UserDto userDto1 = this.modelMapper.map(user, UserDto.class);
        log.info("Complete Dao for create user");
        return userDto1;


    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        log.info("Entering Dao for Updating User");
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("resource not found exception Id"));

        user.setUserName(userDto.getUserName());
        user.setUserAbout(userDto.getUserAbout());
        user.setUserGender(userDto.getUserGender());
        user.setUserPassword(userDto.getUserPassword());
        user.setUserImage(userDto.getUserImage());
        this.userRepo.save(user);
        UserDto userDto1 = this.modelMapper.map(user, UserDto.class);
        log.info("Complete Dao for upkdatng user ");
        return userDto1;


    }

    @Override
    public UserDto getUserById(UserDto userDto, String userId) {
        log.info("Entering Dao  for get user By Id");
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("resource not found exception Id"));
        UserDto userDto1 = this.modelMapper.map(user, UserDto.class);
        log.info("Complte Dao for get user By Id");
        return userDto1;
    }

    @Override
    public PageableResponse getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String direction) {

        log.info("Entering Dao for get all user");
        Sort desc = (direction.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        PageRequest Pr = PageRequest.of(pageNumber, pageSize, desc);
        Page<User> pages = this.userRepo.findAll(Pr);
        PageableResponse<UserDto> pageableResponse = Helper.getPageableResponse(pages, UserDto.class);


        log.info("Complete Dao for get alluser");

        return pageableResponse;
    }

    @Override
    public void DeleteUser(String userId) {
        log.info("Entering Dao for delete user with userId");

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("resource not found exception Id"));
        this.userRepo.delete(user);



    }

    @Override
    public UserDto getUserByEmailId(String email) {
        log.info("Entering Dao for get user by EmailId");
        User user = this.userRepo.findByUserEmail(email).orElseThrow(() -> new ResourceNotFoundException("resource not found exception Id"));
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        log.info("Complate dao for get user by EmailId");
        return userDto;

    }


    @Override
    public List<UserDto> searchUser(String keyword) {
        log.info("Entering dao for serach user");
        List<User> users = this.userRepo.findAll();
        List<UserDto> dtos = users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        log.info("Complete dao for search user");
        return dtos;

    }

    @Override
    public UserDto getByUserEmailAndUserPassword(String email, String password) {
        log.info("Entering dao for find user by email and passsword");
        User user = this.userRepo.getByUserEmailAndUserPassword(email, password).get();
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        log.info("complete dao for find user by email and passsword");
        return userDto;
    }
}
