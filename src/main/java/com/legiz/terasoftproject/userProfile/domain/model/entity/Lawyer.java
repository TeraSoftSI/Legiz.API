package com.legiz.terasoftproject.userProfile.domain.model.entity;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
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

    public Lawyer(Long id, @NotNull @NotBlank String userName, @NotNull @NotBlank String password, @NotNull @NotBlank String lawyerName) {
        super(id, userName, password);
        this.lawyerName = lawyerName;
    }

}
