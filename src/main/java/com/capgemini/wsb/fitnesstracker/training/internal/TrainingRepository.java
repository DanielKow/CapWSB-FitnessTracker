package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

interface TrainingRepository extends JpaRepository<Training, Long> {

    /**
     * Query searching trainings by user ID.
     * @param userId id of the user to search trainings for
     * @return List of trainings for the given user
     */
    List<Training> findByUserId(Long userId);

    /**
     * Query searching trainings by end time.
     * @param after date
     * @return List of trainings that ended after the given date
     */
    List<Training> findByEndTimeAfter(Date after);

    /**
     * Query searching trainings by activity type.
     * @param activityType type of activity
     * @return List of trainings for the given activity type
     */
    List<Training> findByActivityType(ActivityType activityType);

    /**
     * Query searching trainings by user ID and end time.
     * @param userId id of the user to search trainings for
     * @param endTimeAfter start date
     * @param endTimeBefore end date
     * @return List of trainings for the given user and end time
     */
    List<Training> findByUserIdAndEndTimeBetween(Long userId, Date endTimeAfter, Date endTimeBefore);
}
