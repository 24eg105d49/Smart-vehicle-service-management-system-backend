package com.example.demo.repositories;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.ServiceCenter;
import com.example.demo.entities.ServiceRequest;
import com.example.demo.entities.Vehicle;

@Repository
@Transactional
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Integer> {

	@Query("select sr from ServiceRequest sr where sr.servicdate = :date and sr.scid = :sc")
	public List<ServiceRequest> getAllServiceReqsByDateSc(@Param("date") Date date, @Param("sc") ServiceCenter sc);
	
	@Query("select count(sr) from ServiceRequest sr where sr.servicdate = :date and sr.scid = :servc")
	public Integer getAllServReqOnADateforSC(@Param("date") Date date, @Param("servc") ServiceCenter servc);
	
	@Query("select sr from ServiceRequest sr where sr.vehid in :vehs")
	public List<ServiceRequest> getSerReqsByCid(@Param("vehs") List<Vehicle> vehs);
	
	@Modifying
	@Query("Update ServiceRequest set status=true where servicerequestid = :servicerequestid")
	public int changeStatus(@Param("servicerequestid") int servicerequestid);
}

