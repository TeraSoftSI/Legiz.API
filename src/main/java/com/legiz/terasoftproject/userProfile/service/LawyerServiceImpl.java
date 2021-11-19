package com.legiz.terasoftproject.userProfile.service;

import com.legiz.terasoftproject.payment.domain.model.entity.Subscription;
import com.legiz.terasoftproject.payment.domain.persistence.SubscriptionRepository;
import com.legiz.terasoftproject.shared.exception.ResourceNotFoundException;
import com.legiz.terasoftproject.shared.exception.ResourceValidationException;
import com.legiz.terasoftproject.userProfile.domain.model.entity.Lawyer;
import com.legiz.terasoftproject.userProfile.domain.persistence.LawyerRepository;
import com.legiz.terasoftproject.userProfile.domain.service.LawyerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class LawyerServiceImpl implements LawyerService {

    private static final String ENTITY = "Lawyer";
    private LawyerRepository lawyerRepository;
    private SubscriptionRepository subscriptionRepository;
    private final Validator validator;

    public LawyerServiceImpl(LawyerRepository lawyerRepository, SubscriptionRepository subscriptionRepository, Validator validator) {
        this.lawyerRepository = lawyerRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.validator = validator;
    }

    @Override
    public List<Lawyer> getAll() {
        return lawyerRepository.findAll();
    }

    @Override
    public Page<Lawyer> getAll(Pageable pageable) {
        return lawyerRepository.findAll(pageable);
    }

    @Override
    public Lawyer getById(Long lawyerId) {
        return lawyerRepository.findById(lawyerId)
                .orElseThrow(()->new ResourceNotFoundException(ENTITY, lawyerId));
    }

    @Override
    public Lawyer create(Lawyer request) {
        Set<ConstraintViolation<Lawyer>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        // Validate Subscription Id
        if (!subscriptionRepository.existsById(request.getSubscription().getId()))
            throw new ResourceNotFoundException("Subscription", request.getSubscription().getId());

        // Validate Lawyer with != Username
        Lawyer lawyerWithUsername = lawyerRepository.findByUsername(request.getUsername());

        if (lawyerWithUsername != null)
            throw new ResourceValidationException(ENTITY, "A Lawyer with the same username already exists.");

        // Validate Lawyer with != Email
        Lawyer lawyerWithEmail = lawyerRepository.findByEmail(request.getEmail());

        if (lawyerWithEmail != null)
            throw new ResourceValidationException(ENTITY, "A Lawyer with the same email already exists.");

        return lawyerRepository.save(request);
    }

    @Override
    public Lawyer update(Long lawyerId, Lawyer request) {
        Set<ConstraintViolation<Lawyer>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        //Validate Subscription Id
        if (!subscriptionRepository.existsById(request.getSubscription().getId()))
            throw new ResourceNotFoundException("Subscription", request.getSubscription().getId());
        Subscription subscription = subscriptionRepository.getById(request.getSubscription().getId());

        // Validate Lawyer with != Username
        Lawyer lawyerWithUsername = lawyerRepository.findByUsername(request.getUsername());

        if (lawyerWithUsername != null && lawyerWithUsername.getId() != request.getId())
            throw new ResourceValidationException(ENTITY, "A Lawyer with the same username already exists.");

        // Validate Lawyer with != Email
        Lawyer lawyerWithEmail = lawyerRepository.findByEmail(request.getEmail());

        if (lawyerWithEmail != null && lawyerWithEmail.getEmail() != request.getEmail())
            throw new ResourceValidationException(ENTITY, "A Lawyer with the same email already exists.");

        return lawyerRepository.findById(lawyerId).map(lawyer -> {
            lawyer.setUsername(request.getUsername());
            lawyer.setPassword(request.getPassword());
            lawyer.setLawyerName(request.getLawyerName());
            lawyer.setLawyerLastName(request.getLawyerLastName());
            lawyer.setSubscription(subscription);
            return lawyerRepository.save(lawyer);
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, lawyerId));
    }

    @Override
    public ResponseEntity<?> delete(Long lawyerId) {
        return lawyerRepository.findById(lawyerId).map(lawyer -> {
            lawyerRepository.delete(lawyer);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new ResourceNotFoundException(ENTITY, lawyerId));
    }
}
