package com.legiz.terasoftproject.qualification.api;

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
@RequestMapping("api/v1/legaladvices/{legaladviceId}/qualifications")
public class QualificationLegalAdviceController {

    private final QualificationLegalAdviceService qualificationLegalAdviceService;
    private final QualificationMapper mapper;

    public QualificationLegalAdviceController(QualificationLegalAdviceService qualificationLegalAdviceService, QualificationMapper mapper) {
        this.qualificationLegalAdviceService = qualificationLegalAdviceService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<QualificationResource> getQualificationByLegalAdviceId(@PathVariable Long legaladviceId, Pageable pageable) {
        return mapper.modelListToPage(qualificationLegalAdviceService.getAllByLegalAdviceId(legaladviceId), pageable);
    }

    @PostMapping
    public QualificationResource createQualification(@PathVariable Long legaladviceId, @RequestBody CreateQualificationResource request) {
        return mapper.toResource(qualificationLegalAdviceService.create(legaladviceId, mapper.toModel(request)));
    }

    @PutMapping("{qualificationId}")
    public QualificationResource updateQualification(@PathVariable Long legaladviceId, @PathVariable Long qualificationId, @RequestBody UpdateQualificationResource request) {
        return mapper.toResource(qualificationLegalAdviceService.update(legaladviceId, qualificationId, mapper.toModel(request)));
    }

    @DeleteMapping("{qualificationId}")
    public ResponseEntity<?> deleteQualification(@PathVariable Long legaladviceId, @PathVariable Long qualificationId) {
        return qualificationLegalAdviceService.delete(legaladviceId, qualificationId);
    }
}
