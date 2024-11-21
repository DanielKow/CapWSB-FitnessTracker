package com.capgemini.wsb.fitnesstracker.reports.api;

public record Report(Long userId, String title, int NumberOfTrainings, double totalDistance, double totalHours) {

}
