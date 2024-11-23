package com.capgemini.wsb.fitnesstracker.reports.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Implementation of {@link ReportService}.
 */
@Service
@RequiredArgsConstructor
class ReportServiceImpl implements ReportService {
    private final UserProvider userProvider;
    private final TrainingProvider trainingProvider;
    private final EmailSender emailSender;

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
        double totalHours = totalTime / 1000 / 60 / 60;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM.yy");

        String title = "Raport z miesiąca " + LocalDate.now().minusMonths(1).format(dateFormat);

        return new Report(user.id(), title, numberOfTrainings, totalDistance, totalHours);
    }

    /**
     * Sends a report.
     * @param report to be sent
     */
    @Override
    public void sendReport(Report report) {
        UserDto user = userProvider.getUser(report.userId()).orElseThrow(() -> new UserNotFoundException(report.userId()));

        var content = """
                Witaj %s %s!
                Oto twój %s:
                Liczba treningów: %d
                Przebyty dystans: %.2f km
                Czas ćwiczeń: %.2f godzin"""
                .formatted(
                        user.firstName(),
                        user.lastName(),
                        report.title(),
                        report.numberOfTrainings(),
                        report.totalDistance(),
                        report.totalHours());

        EmailDto reportEmail = new EmailDto(user.email(), report.title(), content);
        emailSender.send(reportEmail);
    }

    /**
     * Sends reports.
     * @param reports list of reports to be sent
     */
    @Override
    public void sendReports(List<Report> reports) {
        reports.forEach(this::sendReport);
    }
}
