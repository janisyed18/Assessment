package com.rewardGenerator.Service.Impl;

import com.rewardGenerator.Exception.NotFoundException;
import com.rewardGenerator.Model.Customer;
import com.rewardGenerator.Model.Reward;
import com.rewardGenerator.Model.Transaction;
import com.rewardGenerator.Repository.CustomerRepository;
import com.rewardGenerator.Repository.TransactionRepository;
import com.rewardGenerator.Service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RewardServiceImpl implements RewardService {
    private static final double RATE_OVER_100 = 2.0;
    private static final double RATE_OVER_50 = 1.0;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    public RewardServiceImpl(CustomerRepository customerRepository, TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        double amount = transaction.getAmount();
        int points = 0;
        if (amount > 100) {
            points += (int) ((amount - 100) * RATE_OVER_100);
            points += (int) (50 * RATE_OVER_50);
        } else if (amount > 50) {
            points += (int) ((amount - 50) * RATE_OVER_50);
        }
        transaction.setPointsEarned(points);
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public Reward calculateRewardsByCustomer(Integer customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isEmpty()) {
            throw new NotFoundException("Customer not found with ID " + customerId);
        }
        List<Transaction> transactions = transactionRepository.findAllByCustomer_customerId(customerId);
        Map<Integer, Integer> rewardsByMonth = transactions.stream()
                .filter(t -> t.getDate().isAfter(LocalDate.now().minusMonths(3)))
                .map(t -> Map.entry(t.getDate().getMonthValue(), t.getPointsEarned()))
                .collect(HashMap::new, (map, entry) -> map.merge(entry.getKey(), entry.getValue(), Integer::sum), HashMap::putAll);

        int totalRewards = rewardsByMonth.values().stream().mapToInt(Integer::intValue).sum();
        return new Reward(rewardsByMonth, totalRewards);
    }
}
