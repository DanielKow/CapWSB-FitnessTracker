package com.capgemini.wsb.fitnesstracker.reports.internal;

import com.capgemini.wsb.fitnesstracker.reports.api.Report;
import com.capgemini.wsb.fitnesstracker.reports.api.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/lastMonth")
    public Report getReportFromLastMonthForUser(@Param("userId") Long userId) {
        return reportService.generateReportFromLastMonthForUser(userId);
    }
}
