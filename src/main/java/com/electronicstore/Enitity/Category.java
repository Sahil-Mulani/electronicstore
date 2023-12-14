package com.electronicstore.Enitity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "categories")

public class Category {


        @Id
        @Column( name = "Id")
        private String categoryId;

        @Column(name = "category_title",length = 60,nullable = false)
        private String title;
        @Column(name = "cateegory_desc", length = 50)
        private String description;

        private String coverImage;

        @OneToMany( mappedBy = "categories",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
        private List<Product> products=new ArrayList<>();
    }


