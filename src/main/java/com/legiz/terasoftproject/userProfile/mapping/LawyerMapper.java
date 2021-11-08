package com.legiz.terasoftproject.userProfile.mapping;

import com.legiz.terasoftproject.shared.mapping.EnhancedModelMapper;
import com.legiz.terasoftproject.userProfile.domain.model.entity.Lawyer;
import com.legiz.terasoftproject.userProfile.resource.CreateLawyerResource;
import com.legiz.terasoftproject.userProfile.resource.LawyerResource;
import com.legiz.terasoftproject.userProfile.resource.UpdateLawyerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class LawyerMapper implements Serializable {

    @Autowired
    private EnhancedModelMapper mapper;

    public LawyerResource toResource(Lawyer model) {
        return mapper.map(model, LawyerResource.class);
    }

    public Page<LawyerResource> modelListToPage(List<Lawyer> modelList, Pageable pageable) {
        return new PageImpl<>(
                mapper.mapList(modelList, LawyerResource.class),
                pageable,
                modelList.size());
    }

    public Lawyer toModel(CreateLawyerResource resource) {
        return mapper.map(resource, Lawyer.class);
    }

    public Lawyer toModel(UpdateLawyerResource resource) {
        return mapper.map(resource, Lawyer.class);
    }
}