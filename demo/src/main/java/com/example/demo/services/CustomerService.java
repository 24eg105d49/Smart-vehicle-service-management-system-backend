package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Customer;
import com.example.demo.repositories.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository custrepo;
	
	public Customer save(Customer customer)
	{
		return custrepo.save(customer);
	}
	
	public List<Customer> getAll()
	{
		return custrepo.findAll();
	}
	
	public Customer getById(int id)
	{
		return custrepo.findById(id).orElse(null);
	}
	
	public Customer getByLoginid(String loginid)
	{
		if (loginid == null || loginid.trim().isEmpty() || "null".equalsIgnoreCase(loginid.trim())) {
			return null;
		}
		String trimmed = loginid.trim();
		try {
			int id = Integer.parseInt(trimmed);
			Optional<Customer> cust = custrepo.getByLoginidInt(id);
			if (cust.isPresent()) {
				return cust.get();
			}
		} catch (NumberFormatException e) {
			// Not a numeric ID, fall through to lookup by username (userid)
		}

		Optional<Customer> custByUser = custrepo.getByUserId(trimmed);
		return custByUser.orElse(null);
	}

	public Customer getByLoginidInt(int loginid)
	{
		return custrepo.getByLoginidInt(loginid).orElse(null);
	}
}

