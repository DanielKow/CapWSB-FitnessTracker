package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingRequest;
import com.capgemini.wsb.fitnesstracker.training.api.AddTrainingResponse;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.UpdateTrainingResponse;
import com.capgemini.wsb.fitnesstracker.user.api.UserNameEmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    /**
     * Get all trainings that ended after a given date
     * @param afterTime date after which the trainings ended
     * @return List of TrainingDto
     */
    @GetMapping("finished/{afterTime}")
    public List<TrainingDto> getTrainingEndedAfter(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date afterTime){
        return trainingService.getTrainingEndedAfter(afterTime);
    }

    /**
     * Get all trainings for a given activity type
     * @param activityType type of activity
     * @return List of TrainingDto
     */
    @GetMapping("activityType")
    public List<TrainingDto> getTrainingsByActivityType(@Param("activityType") ActivityType activityType) {
        return trainingService.getTrainingsByActivityType(activityType);
    }

    /**
     * Add a new training
     * @param request request to create a new training
     * @return response with created training
     */
    @PostMapping()
    public ResponseEntity<AddTrainingResponse> addTraining(@RequestBody TrainingRequest request){
        TrainingDto training = trainingService.createTraining(request);
        UserNameEmailDto userNameEmail = new UserNameEmailDto(training.getUser().id(), training.getUser().firstName(), training.getUser().lastName(), training.getUser().email());
        AddTrainingResponse response = new AddTrainingResponse(userNameEmail, training.getDistance(), training.getAverageSpeed());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update an existing training
     * @param trainingId id of the training to be updated
     * @param request data about training to be updated
     * @return response with updated training
     */
    @PutMapping("{trainingId}")
    public ResponseEntity<UpdateTrainingResponse> updateTraining(@PathVariable Long trainingId, @RequestBody TrainingRequest request){
        TrainingDto training = trainingService.updateTraining(trainingId, request);
        UserNameEmailDto userNameEmail = new UserNameEmailDto(training.getUser().id(), training.getUser().firstName(), training.getUser().lastName(), training.getUser().email());
        UpdateTrainingResponse response = new UpdateTrainingResponse(userNameEmail, training.getActivityType(), training.getDistance(), training.getAverageSpeed());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
