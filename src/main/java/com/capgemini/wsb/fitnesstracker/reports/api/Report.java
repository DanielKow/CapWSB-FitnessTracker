package com.capgemini.wsb.fitnesstracker.reports.api;

public record Report(long userId, String title, int numberOfTrainings, double totalDistance, double totalHours) {

}
