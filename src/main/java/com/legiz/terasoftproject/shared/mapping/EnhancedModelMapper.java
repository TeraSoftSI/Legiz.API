package com.legiz.terasoftproject.shared.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import java.util.List;
import java.util.stream.Collectors;

public class EnhancedModelMapper extends ModelMapper {

    public EnhancedModelMapper() {
        super();
    }

    @Override
    public Configuration getConfiguration() {
        return super.getConfiguration().setAmbiguityIgnored(true);
    }

    public <S, T> List<T> mapList(List<S> sourceList, Class<T> targetClass) {
        return sourceList.stream().map(item -> this.map(item, targetClass))
                .collect(Collectors.toList());
    }

}
