package com.electronicstore.Dto;



import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private String cartId;
    private Date createdAt;
    private UserDto user;

    private List<CartItemDto> items = new ArrayList<>();

}
