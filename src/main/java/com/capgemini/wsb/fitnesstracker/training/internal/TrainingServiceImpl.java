package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.dates.DatesUtils;
import com.capgemini.wsb.fitnesstracker.training.api.*;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class TrainingServiceImpl implements TrainingProvider, TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final UserProvider userProvider;

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
     *
     * @param userId id of the user to search trainings for
     * @return List of trainings for the given user
     */
    @Override
    public List<TrainingDto> getTrainingsByUserId(Long userId) {
        return trainingRepository.findByUserId(userId).stream().map(trainingMapper::toDto).toList();
    }

    /**
     * Get all trainings that ended after a given date
     *
     * @param after date
     * @return List of TrainingDto
     */
    @Override
    public List<TrainingDto> getTrainingEndedAfter(Date after) {
        return trainingRepository.findByEndTimeAfter(after).stream().map(trainingMapper::toDto).toList();
    }

    /**
     * Get all trainings for a given activity type
     *
     * @param activityType type of activity
     * @return List of TrainingDto
     */
    @Override
    public List<TrainingDto> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType).stream().map(trainingMapper::toDto).toList();
    }

    /**
     * Get all trainings for a given user from the last month
     *
     * @param userId id of the user
     * @return List of trainings for the given user from the last month
     */
    @Override
    public List<TrainingDto> getUserTrainingsFromLastMonth(Long userId) {
        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        Date startOfLastMonth = DatesUtils.toDate(lastMonth.atDay(1));
        Date endOfLastMonth = DatesUtils.toDate(lastMonth.atEndOfMonth().atTime(23, 59, 59));
        return trainingRepository.findByUserIdAndEndTimeBetween(userId, startOfLastMonth, endOfLastMonth).stream().map(trainingMapper::toDto).toList();
    }

    /**
     * Add a new training
     *
     * @param trainingToAdd request to create a new training
     * @return created training
     */
    @Override
    public TrainingDto createTraining(TrainingRequest trainingToAdd) {
        Optional<UserDto> user = userProvider.getUser(trainingToAdd.getUserId());
        if (user.isEmpty()) {
            throw new UserNotFoundException(trainingToAdd.getUserId());
        }
        UserDto userDto = user.get();
        Training trainingEntity = new Training(new User(
                userDto.id(),
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email()),
                trainingToAdd.getStartTime(),
                trainingToAdd.getEndTime(),
                trainingToAdd.getActivityType(),
                trainingToAdd.getDistance(),
                trainingToAdd.getAverageSpeed());

        Training createdTraining = trainingRepository.save(trainingEntity);

        return trainingMapper.toDto(createdTraining);
    }

    /**
     * Updates an existing training
     *
     * @param trainingToUpdate training to be updated
     * @return updated training
     */
    @Override
    public TrainingDto updateTraining(Long id, TrainingRequest trainingToUpdate) {
        Optional<Training> existingTraining = trainingRepository.findById(id);

        if (existingTraining.isEmpty()) {
            throw new TrainingNotFoundException(id);
        }

        Optional<UserDto> user = userProvider.getUser(trainingToUpdate.getUserId());
        if (user.isEmpty()) {
            throw new UserNotFoundException(trainingToUpdate.getUserId());
        }
        UserDto userDto = user.get();

        Training trainingEntity = existingTraining.get();

        trainingEntity.setUser(new User(
                userDto.id(),
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email()));
        trainingEntity.setStartTime(trainingToUpdate.getStartTime());
        trainingEntity.setEndTime(trainingToUpdate.getEndTime());
        trainingEntity.setActivityType(trainingToUpdate.getActivityType());
        trainingEntity.setDistance(trainingToUpdate.getDistance());
        trainingEntity.setAverageSpeed(trainingToUpdate.getAverageSpeed());

        Training updatedTraining = trainingRepository.save(trainingEntity);

        return trainingMapper.toDto(updatedTraining);
    }
}
