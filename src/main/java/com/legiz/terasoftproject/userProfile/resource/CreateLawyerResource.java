package com.legiz.terasoftproject.userProfile.resource;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@With
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
