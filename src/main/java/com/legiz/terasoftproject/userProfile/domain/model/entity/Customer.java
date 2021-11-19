package com.legiz.terasoftproject.userProfile.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("user_customer")
public class Customer extends User{

    @NotNull
    @NotBlank
    private String customerName;

    @NotNull
    @NotBlank
    private String customerLastName;

    public Customer(Long id, @NotNull @NotBlank String username, @NotNull @NotBlank String password, @NotNull @NotBlank @Email String email,
                    @NotNull @NotBlank String customerName, @NotNull @NotBlank String customerLastName) {
        super(id, username, password, email);
        this.customerName = customerName;
        this.customerLastName = customerLastName;
    }

}
