package com.capgemini.wsb.fitnesstracker.reports.scheduling;

import com.capgemini.wsb.fitnesstracker.reports.api.Report;
import com.capgemini.wsb.fitnesstracker.reports.api.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Scheduler for generating and sending monthly reports.
 */
@Component
@RequiredArgsConstructor
public class MonthlyReportScheduler {

    private final ReportService reportService;

    /**
     * Generates and sends reports at the 1st day of each month.
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void generateAndSendReports() {
        List<Report> reports = reportService.generateReportsFromLastMonth();
        reportService.sendReports(reports);
    }
}
