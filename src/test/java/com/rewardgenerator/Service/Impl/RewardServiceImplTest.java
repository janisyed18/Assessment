package com.rewardgenerator.Service.Impl;

import com.rewardGenerator.Model.Customer;
import com.rewardGenerator.Model.Reward;
import com.rewardGenerator.Model.Transaction;
import com.rewardGenerator.Repository.CustomerRepository;
import com.rewardGenerator.Repository.TransactionRepository;
import com.rewardGenerator.Service.Impl.RewardServiceImpl;
import com.rewardGenerator.Service.RewardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.*;

public class RewardServiceImplTest {

    private RewardService rewardService;
    private TransactionRepository transactionRepository;
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setup() {
         customerRepository = Mockito.mock(CustomerRepository.class);
        transactionRepository = Mockito.mock(TransactionRepository.class);
        rewardService = new RewardServiceImpl(customerRepository, transactionRepository);
    }

    @Test
    public void testCreateTransaction() {
        Transaction transaction = new Transaction();
        transaction.setAmount(120.0);
        transaction.setDate(LocalDate.now());

        rewardService.createTransaction(transaction);

        Assertions.assertEquals(90, transaction.getPointsEarned());
        Mockito.verify(transactionRepository, Mockito.times(1)).save(transaction);
    }

    @Test
    public void testCalculateRewardsByCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        Transaction transaction1 = new Transaction();
        transaction1.setTransactionID(1);
        transaction1.setCustomer(customer);
        transaction1.setAmount(120.0);
        transaction1.setDate(LocalDate.of(2023, 1, 10));
        transaction1.setPointsEarned(90);
        Transaction transaction2 = new Transaction();
        transaction2.setTransactionID(2);
        transaction2.setCustomer(customer);
        transaction2.setAmount(99.0);
        transaction2.setDate(LocalDate.of(2023, 2, 15));
        transaction2.setPointsEarned(49);
        Transaction transaction3 = new Transaction();
        transaction3.setTransactionID(3);
        transaction3.setCustomer(customer);
        transaction3.setAmount(51.0);
        transaction3.setDate(LocalDate.of(2023, 2, 19));
        transaction3.setPointsEarned(10);
        Transaction transaction4 = new Transaction();
        transaction4.setTransactionID(4);
        transaction4.setCustomer(customer);
        transaction4.setAmount(160.0);
        transaction4.setDate(LocalDate.of(2022, 9, 22));
        transaction4.setPointsEarned(230);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);

        Mockito.when(transactionRepository.findAllByCustomer_customerId(1)).thenReturn(transactions);
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(new Customer()));

        Reward reward = rewardService.calculateRewardsByCustomer(1);

        Map<Integer, Integer> rewardsByMonth = reward.getRewardsByMonth();
        Assertions.assertEquals(149, reward.getTotalRewards());
        Assertions.assertEquals(90, rewardsByMonth.get(1));
        Assertions.assertEquals(59, rewardsByMonth.get(2));
        Assertions.assertNull(rewardsByMonth.get(3));
    }

    @Test
    public void testCalculateRewardsByCustomerWithNoTransactions() {
        Mockito.when(transactionRepository.findAllByCustomer_customerId(1)).thenReturn(new ArrayList<>());
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(new Customer()));
        Reward reward = rewardService.calculateRewardsByCustomer(1);

        Map<Integer, Integer> rewardsByMonth = reward.getRewardsByMonth();
        Assertions.assertEquals(0, reward.getTotalRewards());
        Assertions.assertNull(rewardsByMonth.get(1));
        Assertions.assertNull(rewardsByMonth.get(2));
        Assertions.assertNull(rewardsByMonth.get(3));
    }

    @Test
    public void testCalculateRewardsByCustomerWithOnlyOneTransaction() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        Transaction transaction = new Transaction();
        transaction.setTransactionID(1);
        transaction.setCustomer(customer);
        transaction.setAmount(120.0);
        transaction.setDate(LocalDate.of(2023, 1, 10));
        transaction.setPointsEarned(90);

        Mockito.when(transactionRepository.findAllByCustomer_customerId(1)).thenReturn(Collections.singletonList(transaction));
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(new Customer()));
        Reward reward = rewardService.calculateRewardsByCustomer(1);

        Map<Integer, Integer> rewardsByMonth = reward.getRewardsByMonth();
        Assertions.assertEquals(90, reward.getTotalRewards());
        Assertions.assertEquals(90, rewardsByMonth.get(1));
        Assertions.assertNull(rewardsByMonth.get(2));
        Assertions.assertNull(rewardsByMonth.get(3));
    }

}