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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
@Service
public class ProductServiceimpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        Product product  = this.modelMapper.map(productDto, Product.class);

        Date date=new Date();
        String id = UUID.randomUUID().toString();
        product.setProductId(id);
        product.setAddedDate(date);
        Product newProduct = this.productRepo.save(product);
        return modelMapper.map(newProduct,ProductDto.class);
    }

    @Override
    public ProductDto getSingleProduct(String productId) {

        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + productId));

        return modelMapper.map(product,ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId) {

        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));
        productRepo.delete(product);
    }

    @Override
    public ProductDto updateProduct(String productId, ProductDto productDto) {
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

        return modelMapper.map(save,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String direction) {

        Sort sort = (direction.equalsIgnoreCase("desc")) ?
                (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        PageRequest pages=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> all = productRepo.findAll(pages);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(all,ProductDto.class);

        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> findByliveTrue(Integer pageNumber, Integer pageSize, String sortBy, String direction) {

        Sort sort=(direction.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        PageRequest pages=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> byLiveTrue = productRepo.findByLiveTrue(pages);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(byLiveTrue, ProductDto.class);

        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> getAllLIveProducts(Integer pageNumber, Integer pageSize, String sortBy, String direction) {

        Sort sort=(direction.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        PageRequest pages=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> allProducts = productRepo.findAll(pages);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(allProducts, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> getProductByTitle(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String direction) {

        Sort sort=(direction.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        PageRequest pages=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> byTitleContaining = productRepo.findByTitleContaining(pages,keyword);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(byTitleContaining, ProductDto.class);

        return pageableResponse;
    }

    @Override
    public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));

        Product product  = this.modelMapper.map(productDto, Product.class);

        Date date=new Date();
        String id = UUID.randomUUID().toString();
        product.setProductId(id);
        product.setAddedDate(date);
        product.setCategories(category);
        Product newProduct = this.productRepo.save(product);
        return modelMapper.map(newProduct,ProductDto.class);

    }

    @Override
    public PageableResponse<ProductDto> getAllOfCategory(String categoryId, Integer pageNumber, Integer pageSize, String sortBy, String direction) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));
        Sort sort=(direction.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        PageRequest pages=PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> product = this.productRepo.findByCategories(category, pages);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(product, ProductDto.class);

        return pageableResponse;
    }


    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));



        product.setCategories(category);
        Product save = this.productRepo.save(product);

        ProductDto dto = modelMapper.map(save, ProductDto.class);

        return dto;
    }


}