package com.electronicstore.service;

import com.electronicstore.Dto.CategoryDto;
import com.electronicstore.Dto.PageableResponse;
import org.springframework.stereotype.Service;

@Service
public interface CategoryServiceI {

    CategoryDto createCategory(CategoryDto categoryDto);


    PageableResponse getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String direction);


    CategoryDto getSingleCategory(String categoryId);

    void deleteCategory(String categoryId);

    CategoryDto updateCategory(CategoryDto categoryDto,String categoryId);

}
