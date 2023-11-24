package com.electronicstore.Repository;

import com.electronicstore.Dto.UserDto;
import com.electronicstore.Enitity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<User,String> {

    Optional<User> findByUserEmail(String email);

    List<User> findByUserNameContaining(String keyword);

    Optional<User> getByUserEmailAndUserPassword(String email,String password);


}
