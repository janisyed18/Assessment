package com.rewardGenerator.Repository;

import com.rewardGenerator.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    public List<Transaction> findAllByCustomer_customerId(Integer customer_customerId);
}
