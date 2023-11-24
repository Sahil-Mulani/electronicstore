package com.electronicstore.Dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserDto {

    private String userId;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "Enter The Valid Email Id")
    private String userEmail;
    @NotBlank
    @Size(min=5,max=30 ,   message = "UserName Should be Min 5 Character And Max 20 Character")
    private String userName;
    @NotBlank(message = "Password Must be Required")
    private String userPassword;
    @NotBlank
    @Size(min = 3,max = 6,message = "Enter Valid Gender For User")
    private String userGender;
    @ImageNameValid(message = "Image Name Must Not  Be Blank")
    private String userImage;
    @NotBlank
    @Size(message = "Please Enter About User")
    private String userAbout;
}
