package com.capgemini.wsb.fitnesstracker.reports.internal;

import com.capgemini.wsb.fitnesstracker.reports.api.Report;
import com.capgemini.wsb.fitnesstracker.reports.api.ReportService;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public void generateAndSendMonthlyReport() {
        List<UserDto> allUsers = userProvider.findAllUsers();
    }

    @Override
    public Report generateMonthlyReport() {
        return null;
    }

    @Override
    public void sendReport(Report report) {

    }
}
