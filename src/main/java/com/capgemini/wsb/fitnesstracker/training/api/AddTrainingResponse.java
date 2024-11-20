package com.capgemini.wsb.fitnesstracker.training.api;
import com.capgemini.wsb.fitnesstracker.user.api.UserNameEmailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response with added training
 */
@Getter
@AllArgsConstructor
public class AddTrainingResponse {
    private UserNameEmailDto user;
    private double distance;
    private double averageSpeed;

}
