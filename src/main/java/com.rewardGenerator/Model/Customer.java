package com.rewardGenerator.Model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int customerId;
    @Column(nullable = false)
    private String customerName;
    @Column(nullable = false)
    private String emailId;

}
