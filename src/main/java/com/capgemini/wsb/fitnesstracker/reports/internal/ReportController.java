package com.capgemini.wsb.fitnesstracker.reports.internal;

import com.capgemini.wsb.fitnesstracker.reports.api.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/generateAndSendMonthlyReport")
    public void generateAndSendMonthlyReport() {
        reportService.generateAndSendMonthlyReport();
    }
}
