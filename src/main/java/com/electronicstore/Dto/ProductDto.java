package com.electronicstore.Dto;

import lombok.*;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {


    private String productId;

    @NotBlank
    @Size(min = 5,max = 50,message = "Product Title Should Be Min 5 Character & Max 50 Char ")
    private String title;

    @NotBlank
    @Size(min = 5,max = 500,message = "Product  Desc Should Be Min 5 Character & Max 500 Char ")
    private String description;

    @NotNull(message = "Product Price Must Not Be Blank")
    private Double price;

    @NotNull(message = "Product Price Must Not Be Blank")
    private Double discountedPrice;

    @NotNull(message = "Quantity Should Not Be Blank")
    private Integer quantity;

    private Date addedDate;

    private Boolean live;

    private Boolean stock;

    private String image;

    private CategoryDto categories;
}