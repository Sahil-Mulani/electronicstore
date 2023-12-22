package com.electronicstore.Enitity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CartItem {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;
    @OneToOne
    @JoinColumn(name="product_id")
    private Product product;

    private int quantity;

    private int totalPrice;

    @ManyToOne
    @JoinColumn
    private Cart cart;
}
