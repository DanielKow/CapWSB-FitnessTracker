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
    TrainingDto createTraining(TrainingRequest training);

    /**
     * Updates an existing training
     * @param id id of the training to be updated
     * @param training data about training to be updated
     * @return updated training
     */
    TrainingDto updateTraining(Long id, TrainingRequest training);
}
