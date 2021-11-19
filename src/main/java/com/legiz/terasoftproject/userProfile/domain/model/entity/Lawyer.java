package com.legiz.terasoftproject.userProfile.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.legiz.terasoftproject.payment.domain.model.entity.Subscription;
import com.legiz.terasoftproject.userProfile.domain.model.enumeration.Specialization;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("user_lawyer")
public class Lawyer extends User {

    @NotNull
    @NotBlank
    private String lawyerName;

    @NotNull
    @NotBlank
    private String lawyerLastName;

    @Enumerated(EnumType.STRING)
    private Specialization specialization;

    private Long priceLegalAdvice;

    private Long priceCustomLegalCase;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "subscription_id", nullable = true)
    @JsonIgnore
    private Subscription subscription;

    public Lawyer(Long id, @NotNull @NotBlank String username, @NotNull @NotBlank String password, @NotNull @NotBlank @Email String email,
                  @NotNull @NotBlank String lawyerName, @NotNull @NotBlank String lawyerLastName, Specialization specialization, Long priceLegalAdvice,
                  Long priceCustomLegalCase, Subscription subscription) {
        super(id, username, password, email);
        this.lawyerName = lawyerName;
        this.lawyerLastName = lawyerLastName;
        this.specialization = specialization;
        this.subscription = subscription;
        this.priceLegalAdvice = priceLegalAdvice;
        this.priceCustomLegalCase = priceCustomLegalCase;
    }

}
