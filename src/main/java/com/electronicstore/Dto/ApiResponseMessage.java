package com.electronicstore.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseMessage {

    public String message;
    public Boolean status;
    public HttpStatus statusCode;


}
