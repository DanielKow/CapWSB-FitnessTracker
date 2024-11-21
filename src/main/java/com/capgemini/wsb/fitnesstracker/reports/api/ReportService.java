package com.capgemini.wsb.fitnesstracker.reports.api;

import java.util.List;

/**
 * Service for generating and sending reports.
 */
public interface ReportService {
    List<Report> generateReportsFromLastMonth();
    Report generateReportFromLastMonthForUser(long userId);
    void sendReport(Report report);
    void sendReports(List<Report> reports);
}
