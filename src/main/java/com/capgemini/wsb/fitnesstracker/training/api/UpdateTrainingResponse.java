package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.UserNameEmailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response with updated training
 */
@Getter
@AllArgsConstructor
public class UpdateTrainingResponse {
    private UserNameEmailDto user;
    private ActivityType activityType;
    private double distance;
    private double averageSpeed;
}
