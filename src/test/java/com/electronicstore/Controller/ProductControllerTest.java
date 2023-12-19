package com.electronicstore.Controller;

import com.electronicstore.Dto.PageableResponse;
import com.electronicstore.Dto.ProductDto;
import com.electronicstore.Enitity.Product;
import com.electronicstore.service.ProductService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    private Product product;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    private void init() {

        product = Product.builder()
                .title("Samsumg")
                .description("The details related to info")
                .price(50000.00)
                .discountedPrice(40000.00)
                .quantity(40)
                .live(false)
                .stock(false)
                .build();
    }

    @Test
    public void createProductTest() throws Exception{

        ProductDto dto=modelMapper.map(product, ProductDto.class);

        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/product/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(product))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }

    private String convertObjectToJsonString(Product product) {
        try{
            return new ObjectMapper().writeValueAsString(product);
        }catch ( Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void updateProductTest() throws Exception {
        String productId="12";
        ProductDto dto=this.modelMapper.map(product,ProductDto.class);
        Mockito.when(productService.updateProduct(Mockito.anyString(),Mockito.any())).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/product/updateProd/" + productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(product))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());

    }

    @Test
    public void getAllProdTest() throws Exception {
        Product product = Product.builder()
                .title("IPhone")
                .description("The details related to Iphone")
                .price(90000.00)
                .discountedPrice(80000.00)
                .quantity(30)
                .live(false)
                .stock(false)
                .build();

        Product product1 = Product.builder()
                .title("lenovo")
                .description("The details related to lenovo")
                .price(50000.00)
                .discountedPrice(40000.00)
                .quantity(40)
                .live(false)
                .stock(false)
                .build();

        List<Product> prod = Arrays.asList(product, product1);
        List<ProductDto> categoryDtos = prod.stream().map((abc) -> this.modelMapper.map(prod, ProductDto.class)).collect(Collectors.toList());
        PageableResponse pagableResponce = new PageableResponse();
        Mockito.when(productService.getAllProducts(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pagableResponce);

        mockMvc.perform(MockMvcRequestBuilders.get("/product/getAll")
                        .param("pageNumber", "1")
                        .param("pageSize", "10")
                        .param("sortDir", "asc")
                        .param("sortBy", "name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void getAllLiveTest() throws Exception {
        Product product = Product.builder()
                .title("IPhone")
                .description("The details related to Iphone")
                .price(90000.00)
                .discountedPrice(80000.00)
                .quantity(30)
                .live(false)
                .stock(false)
                .build();


        List<Product> prod = Arrays.asList(product);
        List<ProductDto> categoryDtos = prod.stream().map((abc) -> this.modelMapper.map(prod, ProductDto.class)).collect(Collectors.toList());
        PageableResponse pagableResponce = new PageableResponse();
        Mockito.when(productService.getAllLIveProducts(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pagableResponce);

        mockMvc.perform(MockMvcRequestBuilders.get("/product/live")
                        .param("pageNumber", "1")
                        .param("pageSize", "10")
                        .param("sortDir", "asc")
                        .param("sortBy", "name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void getProductTest() throws Exception {
        String productId = "abcd";

        ProductDto dto = modelMapper.map(product, ProductDto.class);
        Mockito.when(productService.getSingleProduct(Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    public void deleteProduct() throws Exception {

        String productId="abc";

        Mockito.doNothing().when(productService).deleteProduct(productId);
        mockMvc.perform(MockMvcRequestBuilders.delete("/product/" + productId))
                .andDo(print())
                .andExpect(status().isOk());
    }
}