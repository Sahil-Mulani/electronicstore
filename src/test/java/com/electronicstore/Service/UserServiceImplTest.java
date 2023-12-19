package com.electronicstore.Service;

import com.electronicstore.Dto.UserDto;
import com.electronicstore.Enitity.User;
import com.electronicstore.Repository.UserRepo;
import com.electronicstore.service.UserServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceImplTest {

    @MockBean
    private UserRepo userRepository;

    @Autowired
    private UserServiceI userServiceI;

    User user;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    private void init() {

        user = User.builder()
                .userName("sahil")
                .userEmail("sahilmulani@gmail.com")
                .userAbout("This is testing electronic store")
                .userGender("male")
                .userImage("txt.image")
                .userPassword("1234")
                .build();
    }

    @Test
    public void createUserTest() {

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto user1 = userServiceI.createUser(modelMapper.map(user, UserDto.class));
        System.out.println(user1.getUserName());
        Assertions.assertNotNull(user1);

        Assertions.assertEquals("sahil", user1.getUserName());
    }

    @Test
    public void updateUserTest() {

        String id = "4f4b9018-e302-4474-a28a-e7be73f83f30";

        UserDto userDto = UserDto.builder()
                .userName("sahil")
                .userAbout("This is update user")
                .userGender("male")
                .userImage("abc.jpg")
                .build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto updateUser = userServiceI.updateUser(userDto, id);
        System.out.println(updateUser.getUserName());
        Assertions.assertNotNull(userDto);

        Assertions.assertEquals(userDto.getUserName(), updateUser.getUserName(), "Name is not equal");

    }

    @Test
    public void deleteUserTest() {

        String id = "74676826-55a6-4dcb-a268-7a28a1941cf3";

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
        userServiceI.DeleteUser(id);

        Mockito.verify(userRepository, Mockito.times(1)).delete(user);

    }
    @Test
    public void getAllUserTest(){

        User user1 = User.builder()
                .userName("sahil")
                .userEmail("sahilmulani@gmail.com")
                .userAbout("This is testing electronic store")
                .userGender("male")
                .userImage("txt.image")
                .userPassword("1234")
                .build();

        User user2 = User.builder()
                .userName("asif")
                .userEmail("sahilmulani@gmail.com")
                .userAbout("This is testing electronic store")
                .userGender("male")
                .userImage("txt.image")
                .userPassword("1234")
                .build();

        List<User> userList = Arrays.asList(user, user1, user2);

        Page<User> page = new PageImpl<>(userList);

        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        Sort sort = Sort.by("name").ascending();

    }

    @Test
    public void getUserByIdTest(){
        String id="f4b9018-e302-4474-a28a-e7be73f83f30";
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
        UserDto userDto= userServiceI.getUserById(id);

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getUserName(),userDto.getUserName(),"Name not matched");
    }

    @Test
    public void getUserByEmailTest(){
        String emailId="sahilmulani@gmail.com";

        Mockito.when(userRepository.findByUserEmail(emailId)).thenReturn(Optional.of(user));

        UserDto userDto = userServiceI.getUserByEmailId(emailId);
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getUserEmail(),userDto.getUserEmail(),"Email not matched");

    }
}