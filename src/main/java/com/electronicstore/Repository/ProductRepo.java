package com.electronicstore.Repository;


import com.electronicstore.Enitity.Category;
import com.electronicstore.Enitity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product,String> {

    //findByLiveTrue()

    Page<Product> findByLiveTrue(Pageable pageable);

    Page<Product> findByTitleContaining(Pageable pageable,String keyword);


    Page<Product> findByCategories(Category category, Pageable pageable);


}