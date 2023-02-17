package com.rewardGenerator.Service.Impl;

import com.rewardGenerator.Model.Customer;
import com.rewardGenerator.Repository.CustomerRepository;
import com.rewardGenerator.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Customer createCustomer(Customer customer) {
        customerRepository.save(customer);
        return customer;
    }
}
