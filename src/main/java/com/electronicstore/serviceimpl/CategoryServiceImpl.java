package com.electronicstore.serviceimpl;
import com.electronicstore.Constant.AppConstant;
import com.electronicstore.Dto.CategoryDto;
import com.electronicstore.Enitity.Category;
import com.electronicstore.Helper.Helper;
import com.electronicstore.Repository.CategoryRepo;
import com.electronicstore.exception.ResourceNotFoundException;
import com.electronicstore.Dto.PageableResponse;
import com.electronicstore.service.CategoryServiceI;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryServiceI {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    /**
     *
     * @param categoryDto
     * @return
     */
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        log.info("Entering the Dao call for create the Category : {}",categoryDto);
        String id = UUID.randomUUID().toString();
        categoryDto.setCategoryId(id);
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = this.categoryRepo.save(category);
        log.info("Completed the Dao call for create the Category : {}",categoryDto);
        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    /**
     *
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @return
     */

    @Override
    public PageableResponse getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String direction) {
        log.info("Entering the Dao call for Get All Categories :");
        Sort sort = (direction.equalsIgnoreCase("desc")) ?
                (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        PageRequest pages = PageRequest.of(pageNumber, pageSize, sort);

        Page<Category> all = this.categoryRepo.findAll(pages);
        PageableResponse<CategoryDto> response = Helper.getPageableResponse(all, CategoryDto.class);
        log.info("Completed the Dao call for Get All Categories :");
        return response;
    }

    /**
     *
     * @param categoryId
     * @return
     */

    @Override
    public CategoryDto getSingleCategory(String categoryId) {
        log.info("Entering the Dao call for Get Single Category With CategoryId :{}",categoryId);
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND+categoryId));

        CategoryDto dto = modelMapper.map(category, CategoryDto.class);
        log.info("Completed the Dao call for Get All  Category With CategoryId:{}",category);
        return dto;
    }

    /**
     *
     * @param categoryId
     */

    @Override
    public void deleteCategory(String categoryId) {
        log.info("Entering the Dao call for Delete The Category With CategoryId :{}",categoryId);
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));
        log.info("Completed the Dao call for Delete The Category With CategoryId :{}",categoryId);
        this.categoryRepo.delete(category);

    }

    /**
     *
     * @param categoryDto
     * @param categoryId
     * @return
     */

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        log.info("Entering the Dao call for Update The Category With CategoryId :{}",categoryId);
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));
        category.setDescription(categoryDto.getDescription());
        category.setTitle(categoryDto.getTitle());
        category.setCoverImage(categoryDto.getCoverImage());
        this.categoryRepo.save(category);
        CategoryDto dto = this.modelMapper.map(category, CategoryDto.class);
        log.info("Completed the Dao call for Update The Category With CategoryId :{}",categoryId);
        return dto;
    }
}