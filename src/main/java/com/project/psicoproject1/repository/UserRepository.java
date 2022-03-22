package com.project.psicoproject1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.psicoproject1.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Boolean existsByUsername(String username);

	Optional<User> findByUsername(String username);

	Optional<User> findByUsernameAndPassword(String username, String password);

	Optional<User> findByEmail(String email);

	Boolean existsByEmail(String email);

}
