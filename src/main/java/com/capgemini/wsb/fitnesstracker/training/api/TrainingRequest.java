package com.capgemini.wsb.fitnesstracker.training.api;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Date;

/**
 * Request to add or update a training
 */
@Getter
@AllArgsConstructor
public class TrainingRequest {
    private Long userId;
    private Date startTime;
    private Date endTime;
    private ActivityType activityType;
    private double distance;
    private double averageSpeed;

}
