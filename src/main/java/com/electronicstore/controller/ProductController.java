package com.electronicstore.controller;

import com.electronicstore.Constant.AppConstant;
import com.electronicstore.Dto.ProductDto;
import com.electronicstore.payload.ApiResponse;
import com.electronicstore.Dto.ImageResponse;
import com.electronicstore.Dto.PageableResponse;
import com.electronicstore.service.ProductService;
import com.electronicstore.service.FileServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@Slf4j
@RequestMapping("/api/product")
public class ProductController {


    @Autowired
    private ProductService productService;

    @Value("${product.profile.image.path}")
    private String path;

    @Autowired
    private FileServiceI fileServiceI;

    @PostMapping("/")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("Entering product for create product");
        ProductDto productDto1 = this.productService.saveProduct(productDto);
        log.info("Complte product for create product");
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    /**
     *
     * @param productId
     * @return
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId) {
        ProductDto singleProduct = this.productService.getSingleProduct(productId);
        return new ResponseEntity<>(singleProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable String productId) {

        ApiResponse apiResponse = ApiResponse.builder().message(AppConstant.DELETE).status(true).statusCode(HttpStatus.OK).build();
        this.productService.deleteProduct(productId);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @PathVariable String productId, @RequestBody ProductDto dto) {

        ProductDto productDto = this.productService.updateProduct(productId, dto);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<PageableResponse> getAllProducts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false) String direction

    ) {

        PageableResponse<ProductDto> allProducts = this.productService.getAllProducts(pageNumber, pageSize, sortBy, direction);
        return new ResponseEntity<>(allProducts, HttpStatus.OK);

    }

    @GetMapping("/trueLiveProducts")
    public ResponseEntity<PageableResponse> findAllLiveTrueProducts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false) String direction
    ) {

        PageableResponse<ProductDto> allLIveProducts = this.productService.findByliveTrue(pageNumber, pageSize, sortBy, direction);
        return new ResponseEntity<>(allLIveProducts, HttpStatus.OK);
    }

    @GetMapping("/liveProducts")
    public ResponseEntity<PageableResponse> getLiveProducts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false) String direction
    ) {

        PageableResponse<ProductDto> allLIveProducts = this.productService.getAllLIveProducts(pageNumber, pageSize, sortBy, direction);
        return new ResponseEntity<>(allLIveProducts, HttpStatus.OK);

    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<PageableResponse> getProductByTitle(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false) String direction
            , @PathVariable String keyword) {

        PageableResponse<ProductDto> productByTitle = this.productService.getProductByTitle(keyword, pageNumber, pageSize, sortBy, direction);

        return new ResponseEntity<>(productByTitle, HttpStatus.OK);
    }


    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam MultipartFile image, @PathVariable String productId) throws IOException {

        String file = this.fileServiceI.uploadFile(image, path);

        ProductDto product = this.productService.getSingleProduct(productId);

        product.setImage(file);

        ProductDto updatedProduct = this.productService.updateProduct(productId, product);

        ImageResponse imageResponse = ImageResponse.builder().message("Image Uploaded").imageName(file).status(true).statusCode(HttpStatus.CREATED).build();

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);

    }


    @GetMapping("/image/{categoryId}")
    public void getProductImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {


        ProductDto product = this.productService.getSingleProduct(categoryId);
        InputStream resource = this.fileServiceI.getResource(path, product.getImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
































