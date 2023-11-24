package com.electronicstore.Enitity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id

    private String userId;

    private String userEmail;


    private String userName;

    private String userPassword;

    private String userGender;

    private String userImage;

    private String userAbout;



}
