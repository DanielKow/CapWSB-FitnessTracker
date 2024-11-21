package com.capgemini.wsb.fitnesstracker.reports.internal;

import com.capgemini.wsb.fitnesstracker.reports.api.Report;
import com.capgemini.wsb.fitnesstracker.reports.api.ReportService;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of {@link ReportService}.
 */
@Service
@RequiredArgsConstructor
class ReportServiceImpl implements ReportService {
    private final UserProvider userProvider;
    private final TrainingProvider trainingProvider;

    @Override
    public List<Report> generateReportsFromLastMonth() {
        return userProvider
                .findAllUsers()
                .stream()
                .map(user -> generateReportFromLastMonthForUser(user.id()))
                .toList();
    }

    @Override
    public Report generateReportFromLastMonthForUser(long userId) {
        UserDto user = userProvider.getUser(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<TrainingDto> userTrainingsFromLastMonth = trainingProvider.getUserTrainingsFromLastMonth(userId);

        int numberOfTrainings = userTrainingsFromLastMonth.size();
        double totalDistance = userTrainingsFromLastMonth.stream().mapToDouble(TrainingDto::getDistance).sum();
        double totalTime = userTrainingsFromLastMonth.stream().mapToDouble(training -> training.getEndTime().getTime() - training.getStartTime().getTime()).sum();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM.yy");

        String title = "Raport z miesiąca " + dateFormat.format(LocalDate.now());

        return new Report(user.id(), title, numberOfTrainings, totalDistance, totalTime);
    }

    @Override
    public void sendReport(Report report) {

    }

    @Override
    public void sendReports(List<Report> reports) {

    }
}
