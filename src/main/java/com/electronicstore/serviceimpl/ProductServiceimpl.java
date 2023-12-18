package com.electronicstore.serviceimpl;

import com.electronicstore.Constant.AppConstant;
import com.electronicstore.Dto.ProductDto;
import com.electronicstore.Enitity.Category;
import com.electronicstore.Enitity.Product;
import com.electronicstore.Helper.Helper;
import com.electronicstore.Repository.CategoryRepo;
import com.electronicstore.Repository.ProductRepo;
import com.electronicstore.exception.ResourceNotFoundException;
import com.electronicstore.Dto.PageableResponse;
import com.electronicstore.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
@Service
@Slf4j
public class ProductServiceimpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    /**
     *
     * @param productDto
     * @return
     */
    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        log.info("Entering Dao for save product");
        Product product  = this.modelMapper.map(productDto, Product.class);

        Date date=new Date();
        String id = UUID.randomUUID().toString();
        product.setProductId(id);
        product.setAddedDate(date);
        Product newProduct = this.productRepo.save(product);
        log.info("Complete Dao for save product");
        return modelMapper.map(newProduct,ProductDto.class);
    }

    /**
     *
     * @param productId
     * @return
     */

    @Override
    public ProductDto getSingleProduct(String productId) {
        log.info("Entering Dao for get single pproduct");
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + productId));
        log.info("Complete Dao for get single product");
        return modelMapper.map(product,ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId) {
        log.info("entering Dao for Delete product");
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));
        productRepo.delete(product);
    }

    /**
     *
     * @param productId
     * @param productDto
     * @return
     */

    @Override
    public ProductDto updateProduct(String productId, ProductDto productDto) {
        log.info("Entering Dao for update product");
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));

        product.setDescription(productDto.getDescription());
        product.setTitle(productDto.getTitle());
        product.setLive(productDto.getLive());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setStock(productDto.getStock());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setImage(productDto.getImage());

        Product save = this.productRepo.save(product);
        log.info("Coomplete Dao for update product");
        return modelMapper.map(save,ProductDto.class);
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
    public PageableResponse<ProductDto> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String direction) {
        log.info("Enetering dao for get all product");
        Sort sort = (direction.equalsIgnoreCase("desc")) ?
                (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        PageRequest pages=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> all = productRepo.findAll(pages);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(all,ProductDto.class);
        log.info("Complete dao for get all product");
        return pageableResponse;
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
    public PageableResponse<ProductDto> findByliveTrue(Integer pageNumber, Integer pageSize, String sortBy, String direction) {
        log.info("Enetering dao for find by live true ");

        Sort sort=(direction.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        PageRequest pages=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> byLiveTrue = productRepo.findByLiveTrue(pages);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(byLiveTrue, ProductDto.class);
        log.info("Completeing dao for get all product");

        return pageableResponse;
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
    public PageableResponse<ProductDto> getAllLIveProducts(Integer pageNumber, Integer pageSize, String sortBy, String direction) {
        log.info("Enetering dao for get all live product");

        Sort sort=(direction.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        PageRequest pages=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> allProducts = productRepo.findAll(pages);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(allProducts, ProductDto.class);
        log.info("Enetering dao for get all live product");
        return pageableResponse;
    }

    /**
     *
     * @param keyword
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @return
     */

    @Override
    public PageableResponse<ProductDto> getProductByTitle(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String direction) {
        log.info("Enetering dao for get product by Title");

        Sort sort=(direction.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        PageRequest pages=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> byTitleContaining = productRepo.findByTitleContaining(pages,keyword);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(byTitleContaining, ProductDto.class);
        log.info("Completedao for get all product");

        return pageableResponse;
    }

    /**
     *
     * @param productDto
     * @param categoryId
     * @return
     */

    @Override
    public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {
        log.info("Enetering dao for create product with category");

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));

        Product product  = this.modelMapper.map(productDto, Product.class);

        Date date=new Date();
        String id = UUID.randomUUID().toString();
        product.setProductId(id);
        product.setAddedDate(date);
        product.setCategories(category);
        Product newProduct = this.productRepo.save(product);
        log.info("Complete  dao for create product with category");
        return modelMapper.map(newProduct,ProductDto.class);

    }

    /**
     *
     * @param categoryId
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @return
     */

    @Override
    public PageableResponse<ProductDto> getAllOfCategory(String categoryId, Integer pageNumber, Integer pageSize, String sortBy, String direction) {
        log.info("enetring Dao for get all category");

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));
        Sort sort=(direction.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        PageRequest pages=PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> product = this.productRepo.findByCategories(category, pages);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(product, ProductDto.class);
        log.info("Commplete dao for get all category");

        return pageableResponse;
    }

    /**
     *
     * @param productId
     * @param categoryId
     * @return
     */


    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        log.info("Entering Dao for update category");
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));



        product.setCategories(category);
        Product save = this.productRepo.save(product);

        ProductDto dto = modelMapper.map(save, ProductDto.class);
        log.info("complete dao for update catrgorry");
        return dto;
    }


}