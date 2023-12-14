package com.electronicstore.Dto;

import lombok.*;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;
    @NotBlank(message= "title required")
    @Size(min = 4)
    private String title;
    @NotBlank(message = "Descr is Required")
    @Size(min = 4)
    @NotBlank
    @Size(min = 5,max = 500,message = "Category Description Must Be required")
    private String description;

    @ImageNameValid (message = "Image Name Not Be Blank")
    private String coverImage;

}
