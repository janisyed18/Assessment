package com.rewardGenerator.Model;

import lombok.*;

import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reward {
    Map<Integer, Integer> rewardsByMonth;
    Integer totalRewards;
}
