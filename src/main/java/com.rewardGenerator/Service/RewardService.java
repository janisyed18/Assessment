package com.rewardGenerator.Service;

import com.rewardGenerator.Model.Reward;
import com.rewardGenerator.Model.Transaction;

public interface RewardService {
    Transaction createTransaction(Transaction transaction);

        Reward calculateRewardsByCustomer(Integer customerId);

}
