package com.electronicstore.service;

import com.electronicstore.Dto.AddItemToCartRequest;
import com.electronicstore.Dto.CartDto;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    //cart is available add item
    CartDto addItemToCart(String id, AddItemToCartRequest request);

    //remove item from cart
    void removeItemFromCart(String id,int cartItem);

    //remove all items
    void clearCart(String id);

    CartDto getCartByUser(String id);
}