package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Rating;
import com.example.demo.entities.ServiceCenter;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
	@Query("select floor(coalesce(avg(r.rating), 0)) from Rating r where r.serviceCenter = :sc")
	public Integer getRating(@Param("sc") ServiceCenter sc);
}

