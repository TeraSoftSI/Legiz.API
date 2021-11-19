package com.legiz.terasoftproject.services.resource;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class UpdateCustomLegalCaseResource {

    private String statusLawService;

    @NotBlank
    @NotNull
    private String title;

    @NotNull
    @NotBlank
    private String startAt;

    @NotNull
    @NotBlank
    private String finishAt;

    @NotNull
    @NotBlank
    private String typeMeet;
}
