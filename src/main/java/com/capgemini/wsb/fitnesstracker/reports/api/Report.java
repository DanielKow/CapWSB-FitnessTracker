package com.capgemini.wsb.fitnesstracker.reports.api;

public record Report(Long userId, int NumberOfTrainings, double totalDistance, double totalDuration) {

}
