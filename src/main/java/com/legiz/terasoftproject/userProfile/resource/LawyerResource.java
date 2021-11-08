package com.legiz.terasoftproject.userProfile.resource;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class LawyerResource {
    private Long id;
    private String userName;
    private String password;
    private String lawyerName;
}
