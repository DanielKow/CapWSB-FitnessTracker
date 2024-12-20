package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrainingProvider {

    /**
     * Retrieves a training based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    Optional<TrainingDto> getTraining(Long trainingId);

    List<TrainingDto> getAllTrainings();

    /**
     * Retrieves all trainings for a given user.
     * @param userId id of the user to search trainings for
     * @return List of trainings for the given user
     */
    List<TrainingDto> getTrainingsByUserId(Long userId);

    List<TrainingDto> getTrainingEndedAfter(Date after);

    /**
     * Get all trainings for a given activity type
     * @param activityType activity type to search trainings for
     * @return List of trainings for the given activity type
     */
    List<TrainingDto> getTrainingsByActivityType(ActivityType activityType);

    /**
     * Get all trainings for a given user from the last month
     * @param userId id of the user
     * @return List of trainings for the given user from the last month
     */
    List<TrainingDto> getUserTrainingsFromLastMonth(Long userId);
}
