package com.knsr.ingestion.repository;

import com.knsr.ingestion.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}

