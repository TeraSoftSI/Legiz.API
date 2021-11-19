package com.legiz.terasoftproject.userProfile.resource;

import com.legiz.terasoftproject.payment.resource.SubscriptionResource;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class LawyerResource {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String lawyerName;
    private String lawyerLastName;
    private String specialization;
    private Long priceLegalAdvice;
    private Long priceCustomLegalCase;
    private int subscriptionId;
}
