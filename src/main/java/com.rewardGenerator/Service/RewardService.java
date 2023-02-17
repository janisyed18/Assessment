package com.rewardGenerator.Service;

import com.rewardGenerator.Model.Reward;
import com.rewardGenerator.Model.Transaction;

import java.util.List;

public interface RewardService {
    Transaction createTransaction(Transaction transaction);

    Reward calculateRewardsByCustomer(Integer customerId);

    List<Transaction> getAllTransactions();

}
