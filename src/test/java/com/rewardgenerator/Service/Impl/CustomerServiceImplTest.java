package com.rewardGenerator.Service.Impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rewardGenerator.Model.Customer;
import com.rewardGenerator.Repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateCustomer() {
        // create a mock customer object
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setCustomerName("John");
        customer.setEmailId("John@gmail.com");

        // mock the behavior of the customer repository
        when(customerRepository.save(customer)).thenReturn(customer);

        // call the createCustomer method of the customer service
        Customer createdCustomer = customerService.createCustomer(customer);

        // verify that the customer was saved in the repository
        verify(customerRepository, times(1)).save(customer);

        // verify that the returned customer is the same as the mock customer
        assertEquals(customer, createdCustomer);
    }
}
