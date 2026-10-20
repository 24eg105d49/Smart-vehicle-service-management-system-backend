package com.example.demo.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Customer;

@Transactional
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	@Query("select c from Customer c where c.login.loginid = :loginid")
	public Optional<Customer> getByLoginidInt(@Param("loginid") int loginid);

	@Query("select c from Customer c where c.login.userid = :userid")
	public Optional<Customer> getByUserId(@Param("userid") String userid);

	@Query("select c from Customer c where c.login.loginid = :loginid")
	public Optional<Customer> getByLoginid(@Param("loginid") int loginid);
}

