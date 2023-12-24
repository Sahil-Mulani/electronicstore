package com.electronicstore.Dto;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderItemDto {

    private int orederItemId;

    private int quantity;

    private int totalPrice;

    private ProductDto product;

    private OrderDto order;

}