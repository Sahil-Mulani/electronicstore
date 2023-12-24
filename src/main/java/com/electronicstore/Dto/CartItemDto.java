package com.electronicstore.Dto;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    private int cartItemId;
    private ProductDto product;
    private int quantity;
    private int totalPrice;

}