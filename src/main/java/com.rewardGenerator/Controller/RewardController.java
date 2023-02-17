package com.rewardGenerator.Controller;

import com.rewardGenerator.Exception.NotFoundException;
import com.rewardGenerator.Model.Reward;
import com.rewardGenerator.Model.Transaction;
import com.rewardGenerator.Service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class RewardController {
    private final RewardService rewardService;

    @Autowired
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return rewardService.createTransaction(transaction);
    }

    @GetMapping("/customer/{customerId}/rewards")
    public ResponseEntity<Reward> calculateRewardsByCustomer(@PathVariable Integer customerId) {
        try {
            Reward reward = rewardService.calculateRewardsByCustomer(customerId);
            return ResponseEntity.ok(reward);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        try {
            List<Transaction> transactions = rewardService.getAllTransactions();
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


