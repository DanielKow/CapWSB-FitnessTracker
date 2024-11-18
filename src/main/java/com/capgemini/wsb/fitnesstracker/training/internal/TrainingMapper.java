package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

/***
 * Maps types related to training
 */
@Component
public class TrainingMapper {

    /**
     * Maps {@link Training} entity to {@link TrainingDto}
     * @param training {@link Training} entity
     * @return {@link TrainingDto}
     */
    TrainingDto toDto(@NotNull Training training) {
        return new TrainingDto(training.getId(),
                new UserDto(training.getUser().getId(),
                        training.getUser().getFirstName(),
                        training.getUser().getLastName(),
                        training.getUser().getBirthdate(),
                        training.getUser().getEmail()),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed());
    }

    /**
     * Maps {@link TrainingDto} to {@link Training} entity
     * @param trainingDto {@link TrainingDto}
     * @return {@link Training} entity
     */
    Training toEntity(@NotNull TrainingDto trainingDto) {
        return new Training(trainingDto.getId(),
                new User(trainingDto.getUser().id(),
                        trainingDto.getUser().firstName(),
                        trainingDto.getUser().lastName(),
                        trainingDto.getUser().birthdate(),
                        trainingDto.getUser().email()),
                trainingDto.getStartTime(),
                trainingDto.getEndTime(),
                trainingDto.getActivityType(),
                trainingDto.getDistance(),
                trainingDto.getAverageSpeed());
    }
}
