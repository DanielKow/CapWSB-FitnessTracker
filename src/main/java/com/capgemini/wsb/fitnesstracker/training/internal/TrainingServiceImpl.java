package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    @Override
    public Optional<TrainingDto> getTraining(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }

    /**
     * Returns all trainings
     *
     * @return list of all trainings
     */
    @Override
    public List<TrainingDto> getAllTrainings() {
        return trainingRepository.findAll().stream().map(trainingMapper::toDto).toList();
    }

    /**
     * Retrieves all trainings for a given user.
     * @param userId id of the user to search trainings for
     * @return List of trainings for the given user
     */
    @Override
    public List<TrainingDto> getTrainingsByUserId(Long userId) {
        return trainingRepository.findByUserId(userId).stream().map(trainingMapper::toDto).toList();
    }

}
