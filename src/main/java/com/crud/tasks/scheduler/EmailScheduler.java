package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailScheduler {
    private static final String SUBJECT = "Tasks: Once a day email";
    private final SimpleEmailService simpleEmailService;
    private final TaskRepository taskRepository;
    private final AdminConfig adminConfig;

    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail() {
        String numberOfTasks;
        long size = taskRepository.count();
        if (size == 1) {
            numberOfTasks = size + " task.";
        } else {
            numberOfTasks = size + " tasks.";
        }

        simpleEmailService.sendDaily(
                new Mail(
                        adminConfig.getAdminMail(),
                        null,
                        SUBJECT,
                        "Currently in database you got: " + numberOfTasks
                )
        );
    }
}
