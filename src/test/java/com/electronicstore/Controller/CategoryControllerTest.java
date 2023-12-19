package com.electronicstore.Controller;

import com.electronicstore.Dto.CategoryDto;
import com.electronicstore.Dto.PageableResponse;
import com.electronicstore.Enitity.Category;
import com.electronicstore.service.CategoryServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @MockBean
    private CategoryServiceI categoryService;

    private Category category;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    private void init() {

        category = Category.builder()
                .title("TV")
                .description("This is Tv related Info")
                .coverImage("abc.png")
                .build();
    }

    @Test
    public void createCatTest() throws Exception{

        CategoryDto dto=modelMapper.map(category, CategoryDto.class);

        Mockito.when(categoryService.createCategory(Mockito.any())).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/category/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(category))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());

    }

    private String convertObjectToJsonString(Category category) {
        try{
            return new ObjectMapper().writeValueAsString(category);
        }catch ( Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void updateCategoryTest() throws Exception {
        String categoryId="12";
        CategoryDto dto=this.modelMapper.map(category,CategoryDto.class);
        Mockito.when(categoryService.updateCategory(Mockito.any(),Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/category/updateCat/" + categoryId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(category))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }
    @Test
    public void getAllCatTest() throws Exception {
        Category category = Category.builder()
                .title("Software Test")
                .description("This is for Testing purpose")
                .coverImage("test.png")
                .build();

        Category category1= Category.builder()
                .title("Sodtware Endineer")
                .description("This is Software enginner info")
                .coverImage("soft.png")
                .build();


        List<Category> cat = Arrays.asList(category, category1);
        List<CategoryDto> categoryDtos = cat.stream().map((abc) -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
        PageableResponse pagableResponce = new PageableResponse();
        Mockito.when(categoryService.getAllCategory(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pagableResponce);

        mockMvc.perform(MockMvcRequestBuilders.get("/category/getAll")
                        .param("pageNumber", "1")
                        .param("pageSize", "10")
                        .param("sortDir", "asc")
                        .param("sortBy", "name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void getByCategoryId() throws Exception {
        String categoryId = "abcd";
        CategoryDto dto = modelMapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.getSingleCategory(Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/category/getSingle/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    public void deleteCategory() throws Exception {

        String categorytId="abc";

        Mockito.doNothing().when(categoryService).deleteCategory(categorytId);
        mockMvc.perform(MockMvcRequestBuilders.delete("/category/" + categorytId))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
