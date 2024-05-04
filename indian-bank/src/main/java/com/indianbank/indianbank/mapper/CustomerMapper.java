package com.indianbank.indianbank.mapper;

import com.indianbank.indianbank.dto.CustomerDto;
import com.indianbank.indianbank.entity.Customer;

public class CustomerMapper {
    public static Customer mapToCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setCustomerId(customerDto.getCustomerId());
        customer.setCustomerName(customerDto.getCustomerName());
        customer.setPhoneNo(customerDto.getPhoneNo());
        customer.setAddress(customerDto.getAddress());
        customer.setActive(customerDto.getActive());
        customer.setEmail(customerDto.getEmail());
        customer.setToken(customerDto.getToken());
        customer.setAccountNo(customerDto.getAccountNo());
        customer.setCreatedOn(customerDto.getCreatedOn());
        customer.setCreatedBy(customerDto.getCreatedBy());
        customer.setUpdatedOn(customerDto.getUpdatedOn());
        customer.setUpdatedBy(customerDto.getUpdatedBy());
        return customer;
    }

    public static CustomerDto mapToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(customer.getCustomerId());
        customerDto.setCustomerName(customer.getCustomerName());
        customerDto.setPhoneNo(customer.getPhoneNo());
        customerDto.setAddress(customer.getAddress());
        customerDto.setActive(customer.getActive());
        customerDto.setEmail(customer.getEmail());
        customerDto.setToken(customer.getToken());
        customerDto.setAccountNo(customer.getAccountNo());
        customerDto.setCreatedOn(customer.getCreatedOn());
        customerDto.setCreatedBy(customer.getCreatedBy());
        customerDto.setUpdatedOn(customer.getUpdatedOn());
        customerDto.setUpdatedBy(customer.getUpdatedBy());
        return customerDto;
    }
}
