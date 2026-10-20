package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Login;
import com.example.demo.entities.ServiceCenter;

@Repository
public interface ServiceCenterRepository extends JpaRepository<ServiceCenter, Integer> {
	@Query("select sc from ServiceCenter sc where sc.area.areaid = :aid")
	public List<ServiceCenter> getServiceCentersbyArea(@Param("aid") int aid);
	
	@Query("select sc from ServiceCenter sc where sc.brand.brandid = :bid")
	public List<ServiceCenter> getServiceCentersbyBrand(@Param("bid") int bid);
	
	@Query("select sc from ServiceCenter sc where sc.brand.brandid = :brandid and sc.area.areaid = :areaid")
	public List<ServiceCenter> getScByBrandArea(@Param("brandid") int brandid, @Param("areaid") int areaid);
	
	@Query("select sc.bookinglimit from ServiceCenter sc where sc.servicecenterid = :scid")
	public Integer getBookingLimitScById(@Param("scid") int scid);
	
	@Query("select sc from ServiceCenter sc where sc.login = :login")
	public ServiceCenter getDetailsByLoginid(@Param("login") Login login);

	@Query("select sc from ServiceCenter sc where sc.login.loginid = :loginid")
	public ServiceCenter getDetailsByLoginIdNumber(@Param("loginid") int loginid);
}

