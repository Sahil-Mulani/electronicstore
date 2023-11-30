package com.electronicstore.Enitity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "categories")

public class Categorry {


        @Id
        @Column( name = "Id")
        private String categoryId;

        @Column(name = "category_title",length = 60,nullable = false)
        private String title;
        @Column(name = "cateegory_desc", length = 50)
        private String description;

        private String coverImage;


    }


