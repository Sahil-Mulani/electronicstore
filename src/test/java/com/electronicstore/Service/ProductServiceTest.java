package com.electronicstore.Service;

import com.electronicstore.Dto.ProductDto;
import com.electronicstore.Enitity.Product;
import com.electronicstore.Repository.ProductRepo;
import com.electronicstore.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {

    @MockBean
    private ProductRepo productRepo;

    @Autowired
    private ProductService productService;

    Product product;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    private void init() {

        product = Product.builder()
                .title("lenovo")
                .description("The details related to info")
                .price(50000.0)
                .discountedPrice(40000.0)
                .quantity(40)
                .live(false)
                .stock(false)
                .build();
    }

    @Test
    public void createProductTest() {
        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product);
        ProductDto product1 = productService.createProduct(modelMapper.map(product, ProductDto.class));
        System.out.println(product1.getTitle());
        Assertions.assertNotNull(product1);

        Assertions.assertEquals("lenovo", product1.getTitle());
    }

    @Test
    public void updateProductTest() {

        String productId = "bacdefcgf";

        ProductDto productDto = ProductDto.builder()
                .title("lenovo")
                .description("The details related to info")
                .price(50000.00)
                .discountedPrice(40000.00)
                .quantity(40)
                .live(false)
                .stock(false)
                .build();

        Mockito.when(productRepo.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product);

        ProductDto updateProd = productService.updateProduct(productId, productDto);
        System.out.println(updateProd.getTitle());
        Assertions.assertNotNull(productDto);

        Assertions.assertEquals(productDto.getTitle(), updateProd.getTitle(), "Title is not equal");

    }

    @Test
    public void deleteProdTest() {

        String productId = "abcdefghijklm";

        Mockito.when(productRepo.findById(productId)).thenReturn(Optional.of(product));

        productService.deleteProduct(productId);

        Mockito.verify(productRepo, Mockito.times(1)).delete(product);

    }
    @Test
    public void getAllProdTest(){

        Product product1 = Product.builder()
                .title("Samsumg")
                .description("The details related to info")
                .price(50000.00)
                .discountedPrice(40000.00)
                .quantity(40)
                .live(false)
                .stock(false)
                .build();

        Product product2 = Product.builder()
                .title("IPhone")
                .description("The details related to info")
                .price(100000.)
                .discountedPrice(90000.)
                .quantity(50)
                .live(false)
                .stock(false)
                .build();

        List<Product> productList = Arrays.asList(product1, product2);
        Page<Product> page = new PageImpl<>(productList);

        Mockito.when(productRepo.findAll((Pageable) Mockito.any())).thenReturn(page);
        Sort sort = Sort.by("title").ascending();
    }

    @Test
    public void getAllLiveTest(){

        Product product1 = Product.builder()
                .title("Samsumg")
                .description("The details related to info")
                .price(50000.00)
                .discountedPrice(40000.00)
                .quantity(40)
                .live(false)
                .stock(false)
                .build();

        Product product2 = Product.builder()
                .title("IPhone")
                .description("The details related to info")
                .price(100000.00)
                .discountedPrice(90000.00)
                .quantity(50)
                .live(false)
                .stock(false)
                .build();

        List<Product> productList = Arrays.asList(product1, product2);
        Page<Product> page = new PageImpl<>(productList);

        Mockito.when(productRepo.findByLiveTrue((Pageable) Mockito.any())).thenReturn(page);
        Sort sort = Sort.by("title").ascending();

    }

    @Test
    public void searchByTitleTest(){

        Product product1 = Product.builder()
                .title("lenovo")
                .description("The details related to info")
                .price(50000.00)
                .discountedPrice(40000.00)
                .quantity(40)
                .live(false)
                .stock(false)
                .build();

        Product product2 = Product.builder()
                .title("IPhone")
                .description("The details related to info")
                .price(100000.00)
                .discountedPrice(90000.00)
                .quantity(50)
                .live(false)
                .stock(false)
                .build();

        List<Product> productList = Arrays.asList(product1, product2);
        Page<Product> page = new PageImpl<>(productList);

        Mockito.when(productRepo.findByTitleContaining((Pageable) Mockito.any(),Mockito.anyString())).thenReturn(page);
        Sort sort = Sort.by("title").ascending();

    }

}