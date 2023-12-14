package com.electronicstore.payload;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {

    public String message;
    public Boolean status;
    public HttpStatus statusCode;
}
