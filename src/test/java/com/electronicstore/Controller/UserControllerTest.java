package com.electronicstore.Controller;

import com.electronicstore.Dto.PageableResponse;
import com.electronicstore.Dto.UserDto;
import com.electronicstore.Enitity.User;
import com.electronicstore.service.UserServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Arrays;
import java.util.List;

import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserServiceI userServiceI;

    private User user;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    private void init(){

        user= User.builder()
                .userName("sahil")
                .userEmail("sahilmulani@gmail.com")
                .userAbout("This is testing electronic store")
                .userGender("male")
                .userImage("txt.image")
                .userPassword("1234")
                .build();

    }
    @Test
    public void createUserTest() throws Exception{

        UserDto dto=modelMapper.map(user, UserDto.class);

        Mockito.when(userServiceI.createUser(Mockito.any())).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/user/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());

    }

    private String convertObjectToJsonString(User user) {
        try{
            return new ObjectMapper().writeValueAsString(user);
        }catch ( Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void updateUserTest() throws Exception {
        String id="12";
        UserDto dto=this.modelMapper.map(user,UserDto.class);
        Mockito.when(userServiceI.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/user/updateUser/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        //  .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void getUserByIdTest() throws Exception {
        String id = "abcd";

        UserDto dto = modelMapper.map(user, UserDto.class);
        Mockito.when(userServiceI.getUserById(Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/get/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void getUserByEmailTest() throws Exception {
        String email="sahilmulani@gmail.com";

        UserDto dto=this.modelMapper.map(user,UserDto.class);

        Mockito.when(userServiceI.getUserByEmailId(email)).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/user/email/" + email)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        //  .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void getAllUserTest() throws Exception {
        User user = User.builder()
                .userName("sahil")
                .userEmail("sahilmulani@gmail.com")
                .userAbout("This is testing electronic store")
                .userGender("male")
                .userImage("txt.image")
                .userPassword("1234")
                .build();

        User user1 = User.builder()
                .userName("asif")
                .userEmail("sahilmulani@gmail.com")
                .userAbout("This is testing electronic store")
                .userGender("male")
                .userImage("txt.image")
                .userPassword("1234")
                .build();


        List<User> users = Arrays.asList(user, user1);
        List<UserDto> userDtos = users.stream().map((abc) -> this.modelMapper.map(users, UserDto.class)).collect(Collectors.toList());
        PageableResponse pagableResponce = new PageableResponse();
        Mockito.when(userServiceI.getAllUser(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pagableResponce);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/getAll")
                        .param("pageNumber", "1")
                        .param("pageSize", "10")
                        .param("sortDir", "asc")
                        .param("sortBy", "name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void deleteUser() throws Exception {

        String id = "123";

        Mockito.doNothing().when(userServiceI).DeleteUser(id);


        mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/" + id)
                        .contentType(MediaType.TEXT_PLAIN_VALUE +";charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
                .andExpect(content().string("Delete User Successfully"));
    }

}