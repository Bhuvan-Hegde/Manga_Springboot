package com.mangalist.manga.repository;

import com.mangalist.manga.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
