package com.capgemini.wsb.fitnesstracker.reports.api;

public record Report(String userName, String title, int numberOfTrainings, double totalDistance, double totalHours) {

}
