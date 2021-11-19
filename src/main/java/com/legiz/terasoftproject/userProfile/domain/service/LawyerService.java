package com.legiz.terasoftproject.userProfile.domain.service;

import com.legiz.terasoftproject.userProfile.domain.model.entity.Lawyer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LawyerService {
    List<Lawyer> getAll();
    Page<Lawyer> getAll(Pageable pageable);
    Lawyer getById(Long lawyerId);
    Lawyer create(Lawyer request);
    Lawyer update(Long lawyerId, Lawyer request);
    ResponseEntity<?> delete(Long lawyerId);
}
