package com.capgemini.wsb.fitnesstracker.training.api;

/**
 * Service for managing trainings
 */
public interface TrainingService {
    /**
     * Creates a new training
     * @param training training to be created
     * @return created training
     */
    TrainingDto createTraining(AddTrainingRequest training);
}
