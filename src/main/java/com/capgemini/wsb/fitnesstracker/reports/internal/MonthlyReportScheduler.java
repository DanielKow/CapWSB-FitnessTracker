package com.capgemini.wsb.fitnesstracker.reports.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonthlyReportScheduler {

    private final ReportService reportService;

    /**
     * Generates and sends reports at the 1st day of each month.
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void generateAndSendReports() {
        reportService.generateAndSendMonthlyReport();
    }
}
