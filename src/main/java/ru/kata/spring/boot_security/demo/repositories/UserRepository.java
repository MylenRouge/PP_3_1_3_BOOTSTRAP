package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    List<User> findAll();
    @Override
    Optional<User> findById(Long id);
    @Override
    <S extends User> S save(S user);
    @Modifying
    @Query("UPDATE User user SET user.name = :name, user.surname = :surname, user.age = :age WHERE user.id = :id")
    int updateUserByIdIs(@Param("id") Long id, @Param("name") String name, @Param("surname") String surname, @Param("age") Short age);
    Optional<User> findByUsername(String username);
    @Override
    void deleteById(Long id);
}