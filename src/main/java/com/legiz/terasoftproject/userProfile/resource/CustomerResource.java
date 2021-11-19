package com.legiz.terasoftproject.userProfile.resource;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class CustomerResource {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String customerName;
    private String customerLastName;
}
