package com.legiz.terasoftproject.qualification.api;

import com.legiz.terasoftproject.qualification.domain.service.QualificationCustomLegalCaseService;
import com.legiz.terasoftproject.qualification.domain.service.QualificationLegalAdviceService;
import com.legiz.terasoftproject.qualification.mapping.QualificationMapper;
import com.legiz.terasoftproject.qualification.resource.CreateQualificationResource;
import com.legiz.terasoftproject.qualification.resource.QualificationResource;
import com.legiz.terasoftproject.qualification.resource.UpdateQualificationResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Qualifications")
@RestController
@RequestMapping("api/v1/customlegalcases/{customlegalcaseId}/qualifications")
public class QualificationCustomLegalCaseController {
    private final QualificationCustomLegalCaseService qualificationCustomLegalCaseService;
    private final QualificationMapper mapper;

    public QualificationCustomLegalCaseController(QualificationCustomLegalCaseService qualificationCustomLegalCaseService, QualificationMapper mapper) {
        this.qualificationCustomLegalCaseService = qualificationCustomLegalCaseService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<QualificationResource> getQualificationByCustomLegalCaseId(@PathVariable Long customlegalcaseId, Pageable pageable) {
        return mapper.modelListToPage(qualificationCustomLegalCaseService.getAllByCustomLegalCaseId(customlegalcaseId), pageable);
    }

    @PostMapping
    public QualificationResource createQualification(@PathVariable Long customlegalcaseId, @RequestBody CreateQualificationResource request) {
        return mapper.toResource(qualificationCustomLegalCaseService.create(customlegalcaseId, mapper.toModel(request)));
    }

    @PutMapping("{qualificationId}")
    public QualificationResource updateQualification(@PathVariable Long customlegalcaseId, @PathVariable Long qualificationId, @RequestBody UpdateQualificationResource request) {
        return mapper.toResource(qualificationCustomLegalCaseService.update(customlegalcaseId, qualificationId, mapper.toModel(request)));
    }

    @DeleteMapping("{qualificationId}")
    public ResponseEntity<?> deleteQualification(@PathVariable Long customlegalcaseId, @PathVariable Long qualificationId) {
        return qualificationCustomLegalCaseService.delete(customlegalcaseId, qualificationId);
    }
}
