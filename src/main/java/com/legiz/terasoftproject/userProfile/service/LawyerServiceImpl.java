package com.legiz.terasoftproject.userProfile.service;

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
    private final Validator validator;

    public LawyerServiceImpl(LawyerRepository lawyerRepository, Validator validator) {
        this.lawyerRepository = lawyerRepository;
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

        Lawyer lawyerWithUserName = lawyerRepository.findByUserName(request.getUserName());

        if (lawyerWithUserName != null)
            throw new ResourceValidationException(ENTITY, "A Lawyer with the same user-name already exists.");

        return lawyerRepository.save(request);
    }

    @Override
    public Lawyer update(Long lawyerId, Lawyer request) {
        Set<ConstraintViolation<Lawyer>> violations = validator.validate(request);

        Lawyer lawyerWithUserName = lawyerRepository.findByUserName(request.getUserName());

        if (lawyerWithUserName != null && lawyerWithUserName.getId() != request.getId())
            throw new ResourceValidationException(ENTITY, "A Lawyer with the same user-name already exists.");

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);


        return lawyerRepository.findById(lawyerId).map(lawyer -> {
            lawyer.setUserName(request.getUserName());
            lawyer.setPassword(request.getPassword());
            lawyer.setLawyerName(request.getLawyerName());
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
