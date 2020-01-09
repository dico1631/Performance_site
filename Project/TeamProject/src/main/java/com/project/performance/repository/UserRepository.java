package com.project.performance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.performance.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByEmailAndPwd(String email, String pwd);
}