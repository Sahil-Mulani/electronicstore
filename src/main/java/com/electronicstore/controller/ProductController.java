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

    /**
     * @auther sahil
     * @param productDto
     * @return
     */

    @PostMapping("/")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("Entering product for create product");
        ProductDto productDto1 = this.productService.createProduct(productDto);
        log.info("Complte product for create product");
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    /**
     * @auther sahil
     * @apiNote for get single product
     * @param productId
     * @return
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId) {
        log.info("Entering product for get single product");

        ProductDto singleProduct = this.productService.getSingleProduct(productId);
        log.info("complete product for get single prod");
        return new ResponseEntity<>(singleProduct, HttpStatus.OK);
    }

    /**
     *
     * @param productId
     * @return
     */

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable String productId) {
        log.info("Entering product for delete product by using Id ");
        ApiResponse apiResponse = ApiResponse.builder().message(AppConstant.DELETE).status(true).statusCode(HttpStatus.OK).build();
        this.productService.deleteProduct(productId);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    /**
     *
     * @param productId
     * @param dto
     * @return
     */

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @PathVariable String productId, @RequestBody ProductDto dto) {
        log.info("Eneting product for update the product");
        ProductDto productDto = this.productService.updateProduct(productId, dto);
        log.info("Complete product for update the product");
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    /**
     *
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @return
     */

    @GetMapping("/")
    public ResponseEntity<PageableResponse> getAllProducts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false) String direction

    ) {
           log.info("Entering product for geting all product data");
        PageableResponse<ProductDto> allProducts = this.productService.getAllProducts(pageNumber, pageSize, sortBy, direction);
        log.info("Complete product for geting all product data");
        return new ResponseEntity<>(allProducts, HttpStatus.OK);

    }

    /**
     *
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @return
     */

    @GetMapping("/trueLiveProducts")
    public ResponseEntity<PageableResponse> findAllLiveTrueProducts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false) String direction
    ) {
        log.info("Enteering product for find all live true product data");
        PageableResponse<ProductDto> allLIveProducts = this.productService.findByliveTrue(pageNumber, pageSize, sortBy, direction);
        log.info("Complete product for find all live true product data");
        return new ResponseEntity<>(allLIveProducts, HttpStatus.OK);
    }

    /**
     *
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @return
     */

    @GetMapping("/liveProducts")
    public ResponseEntity<PageableResponse> getLiveProducts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false) String direction
    ) {
        log.info("Entering product for get livee products data");

        PageableResponse<ProductDto> allLIveProducts = this.productService.getAllLIveProducts(pageNumber, pageSize, sortBy, direction);
        log.info("Entering product for get live products data");
        return new ResponseEntity<>(allLIveProducts, HttpStatus.OK);

    }

    /**
     *
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @param keyword
     * @return
     */

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<PageableResponse> getProductByTitle(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false) String direction
            , @PathVariable String keyword) {

        log.info("Enetring product for get product by title ");
        PageableResponse<ProductDto> productByTitle = this.productService.getProductByTitle(keyword, pageNumber, pageSize, sortBy, direction);
        log.info("Complete pproduct for get product by title ");
        return new ResponseEntity<>(productByTitle, HttpStatus.OK);
    }

    /**
     *
     * @param image
     * @param productId
     * @return
     * @throws IOException
     */


    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam MultipartFile image, @PathVariable String productId) throws IOException {
        log.info("Entering product for upload image ");
        String file = this.fileServiceI.uploadFile(image, path);

        ProductDto product = this.productService.getSingleProduct(productId);

        product.setImage(file);

        ProductDto updatedProduct = this.productService.updateProduct(productId, product);

        ImageResponse imageResponse = ImageResponse.builder().message("Image Uploaded").imageName(file).status(true).statusCode(HttpStatus.CREATED).build();
        log.info("compltete product for uploading image");
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);

    }

    /**
     *
     * @param categoryId
     * @param response
     * @throws IOException
     */


    @GetMapping("/image/{categoryId}")
    public void getProductImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {

        log.info("Entering product for get product by Image");
        ProductDto product = this.productService.getSingleProduct(categoryId);
        InputStream resource = this.fileServiceI.getResource(path, product.getImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        log.info("Complete product for get product by image");
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
































