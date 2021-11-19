package com.legiz.terasoftproject.userProfile.resource;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class UpdateLawyerResource {

    @NotNull
    @NotBlank
    private String password;

    @Email
    @NotBlank
    @NotNull
    private String email;

    @NotNull
    @NotBlank
    private String lawyerName;

    @NotNull
    @NotBlank
    private String lawyerLastName;

    private Long priceLegalAdvice;

    private Long priceCustomLegalCase;

    @NotNull
    private int subscriptionId;
}
