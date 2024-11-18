package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
class TrainingController {

    private final TrainingServiceImpl trainingService;

    /**
     * Get all trainings
     * @return List of TrainingDto
     */
    @GetMapping
    public List<TrainingDto> getTrainings() {
        return trainingService.getAllTrainings();
    }

    /**
     * Get all trainings for a given user
     * @param userId id of the user
     * @return List of TrainingDto
     */
    @GetMapping("{userId}")
    public List<TrainingDto> getTrainingsByUserId(@PathVariable Long userId) {
        return trainingService.getTrainingsByUserId(userId);
    }
}
