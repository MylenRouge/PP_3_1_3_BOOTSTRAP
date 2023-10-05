package ru.kata.spring.boot_security.demo.services;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService extends UserRepository {

    @Override
    List<User> findAll();
    @Override
    Optional<User> findById(Long id);
    @Override
    <S extends User> S save(S user);

    @Transactional
    @Modifying
    @Query("UPDATE User user SET user.name = :name, user.surname = :surname, user.age = :age, user.username = :username, user.password = :password WHERE user.id = :id")
    void updateById(@Param("id") Long id, @Param("name") String name, @Param("surname") String surname, @Param("age") Short age, @Param("username") String username, @Param("password") String password);
    Optional<User> findByUsername(String username);
    @Override
    void deleteById(Long id);
}