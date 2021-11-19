package com.legiz.terasoftproject.userProfile.service;

import com.legiz.terasoftproject.shared.exception.ResourceNotFoundException;
import com.legiz.terasoftproject.shared.exception.ResourceValidationException;
import com.legiz.terasoftproject.userProfile.domain.model.entity.Customer;
import com.legiz.terasoftproject.userProfile.domain.model.entity.Lawyer;
import com.legiz.terasoftproject.userProfile.domain.persistence.CustomerRepository;
import com.legiz.terasoftproject.userProfile.domain.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;


@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String ENTITY = "Customer";
    private CustomerRepository customerRepository;
    private final Validator validator;

    public CustomerServiceImpl(CustomerRepository customerRepository, Validator validator) {
        this.validator = validator;
        this.customerRepository = customerRepository;
    }


    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Page<Customer> getAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer getById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, customerId));
    }

    @Override
    public Customer create(Customer request) {
        Set<ConstraintViolation<Customer>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        // Validate Customer with != Username
        Customer customerWithUsername = customerRepository.findByUsername(request.getUsername());

        if (customerWithUsername != null)
            throw new ResourceValidationException(ENTITY, "A Customer with the same username already exists.");

        // Validate Customer with != Email
        Customer customerWithEmail = customerRepository.findByEmail(request.getEmail());

        if (customerWithEmail != null)
            throw new ResourceValidationException(ENTITY, "A Customer with the same email already exists.");

        return customerRepository.save(request);
    }

    @Override
    public Customer update(Long customerId, Customer request) {
        Set<ConstraintViolation<Customer>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        // Validate Customer with != Username
        Customer customerWithUserName = customerRepository.findByUsername(request.getUsername());

        if (customerWithUserName != null && customerWithUserName.getId() != request.getId())
            throw new ResourceValidationException(ENTITY, "A Customer with the same username already exists.");

        // Validate Customer with != Email
        Customer customerWithEmail = customerRepository.findByEmail(request.getEmail());

        if (customerWithEmail != null && customerWithEmail.getEmail() != request.getEmail())
            throw new ResourceValidationException(ENTITY, "A Customer with the same email already exists.");

        return customerRepository.findById(customerId).map(customer -> {
            customer.setUsername(request.getUsername());
            customer.setPassword(request.getPassword());
            customer.setCustomerName(request.getCustomerName());
            customer.setCustomerLastName(request.getCustomerLastName());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, customerId));
    }

    @Override
    public ResponseEntity<?> delete(Long customerId) {
        return customerRepository.findById(customerId).map(lawyer -> {
            customerRepository.delete(lawyer);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new ResourceNotFoundException(ENTITY, customerId));
    }
}
