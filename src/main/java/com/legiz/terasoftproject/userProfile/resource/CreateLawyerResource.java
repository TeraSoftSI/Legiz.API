package com.legiz.terasoftproject.userProfile.resource;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateLawyerResource {

    @NotNull
    @NotBlank
    private String userName;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String lawyerName;
}
