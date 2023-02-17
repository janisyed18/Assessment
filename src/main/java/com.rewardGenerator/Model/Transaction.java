package com.rewardGenerator.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int transactionID;
    private LocalDate date = LocalDate.now();
    @Column(nullable = false)
    private double amount;
    private int pointsEarned;
    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

}
