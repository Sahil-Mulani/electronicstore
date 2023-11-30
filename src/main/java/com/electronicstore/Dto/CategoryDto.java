package com.electronicstore.Dto;

import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
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
    private String description;

    private String coverImage;

}
