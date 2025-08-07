package com.knsr.wmgmt.repository;

import com.knsr.wmgmt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT MAX(CAST(SUBSTRING(u.username, 5) AS int)) FROM User u WHERE u.username LIKE 'user%'")
    Optional<Integer> findMaxUserIndex();
}
