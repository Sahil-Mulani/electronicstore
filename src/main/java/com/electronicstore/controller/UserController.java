package com.electronicstore.controller;


import com.electronicstore.Dto.ImageResponse;
import com.electronicstore.Dto.PageableResponse;
import com.electronicstore.Dto.UserDto;
import com.electronicstore.service.UserServiceI;
import com.electronicstore.service.FileServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserServiceI userServiceI;

    @Autowired
    private FileServiceI fileServiceI;

    @Value("${user.profile.image.path}")
    private String path;

    /**
     * @author sahil mulani
     * @param userDto
     * @return
     */


    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        log.info("Entering controller for create user");

        UserDto user = this.userServiceI.createUser(userDto);
        log.info("Complate controller for create user");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * @author sahil mulani
     * @apiNote To update user data
     * @param userDto
     * @param userId
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> UpdateUser(@RequestBody UserDto userDto, @PathVariable("userId") String userId) {
      log.info("Entering Controller for update user");
        UserDto updatedUser = this.userServiceI.updateUser(userDto, userId);
      log.info("Complete controller for update user");
        return ResponseEntity.ok(updatedUser);

    }

    /**
     * @auther sahil
     * @apiNote To get all user data
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @return
     */
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

    /**
     * @auther sahil
     * @apiNote To delete user in database
     * @param userId
     * @return
     */

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserDto> DeleteUser(@PathVariable("userId") String userId) {
        log.info("Entering the request for delete users");
        this.userServiceI.DeleteUser(userId);

        return new ResponseEntity<UserDto>(HttpStatus.OK);
    }

    /**
     * @auther sahil
     * @apiNote To get user containg form database
     * @param keyword
     * @return
     *
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> getUserContaing(@PathVariable String keyword) {

        log.info("Entering the request for get user containg");
        List<UserDto> userDtos = this.userServiceI.searchUser(keyword);

        log.info("complate the requset for get user containg ");


        return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK);
    }

    /**
     * @author sahil
     * @param email
     * @return
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        log.info("Enter the  request for get the user with EmailId :{} ", email);
        UserDto userByEmailId = this.userServiceI.getUserByEmailId(email);
        log.info("Completed the  request for get the user with EmailId :{} ", email);
        return new ResponseEntity<UserDto>(userByEmailId, HttpStatus.OK);
    }

    /**
     * @author sahil
     * @param email
     * @param password
     * @return
     */
    @GetMapping("/email/{email}/pass/{password}")
    public ResponseEntity<UserDto> getUserByEmailAndPass(@PathVariable String email, @PathVariable String password) {

        log.info("Enter the  request for get the user with EmailId And Password :{} :{} ", email, password);
        UserDto userByEmailAndPassword = this.userServiceI.getByUserEmailAndUserPassword(email, password);
        log.info("Completed the  request for get the user with EmailId And Password :{} :{} ", email, password);
        return new ResponseEntity<UserDto>(userByEmailAndPassword, HttpStatus.OK);
    }

    /**
     *
     * @param image
     * @param userId
     * @return
     * @throws IOException
     */


        @PostMapping("/image/{userId}")
        public ResponseEntity<ImageResponse> uploadImage(@RequestParam MultipartFile image, @PathVariable    String userId) throws
        IOException {
            log.info("Enter the request for Upload Image with UserId : {}",userId);
            String file = this.fileServiceI.uploadFile(image, path);

            UserDto user = this.userServiceI.getSingleUser(userId);

            user.setUserImage(file);

            UserDto updatedUser = this.userServiceI.updateUser(user, userId);

            ImageResponse imageResponse = ImageResponse.builder().message("Image Uploaded").imageName(file).status(true).statusCode(HttpStatus.CREATED).build();

            log.info("Completed the request for Upload Image with UserId : {}",userId);
            return new ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);

        }
        @GetMapping("/{userId}")
        public ResponseEntity<UserDto> getUserById(@PathVariable String userId){


            UserDto userDto = this.userServiceI.getUserById(userId);

            return new ResponseEntity<>(userDto,HttpStatus.OK);
        }









    }














