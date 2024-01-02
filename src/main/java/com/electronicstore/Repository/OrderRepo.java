package com.electronicstore.Repository;
import com.electronicstore.Enitity.Order;
import com.electronicstore.Enitity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,String> {

    List<Order> findByUser(User user);

}