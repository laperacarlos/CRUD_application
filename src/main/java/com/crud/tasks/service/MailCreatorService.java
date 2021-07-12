package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {
    private final AdminConfig adminConfig;
    private final CompanyConfig companyConfig;
    private final TemplateEngine templateEngine;

    public MailCreatorService(AdminConfig adminConfig, CompanyConfig companyConfig, @Qualifier("templateEngine") TemplateEngine templateEngine) {
        this.adminConfig = adminConfig;
        this.companyConfig = companyConfig;
        this.templateEngine = templateEngine;
    }

    public String buildTrelloCardEmail(String message) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://laperacarlos.github.io/");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName() + ",");
        context.setVariable("goodbye_msg", "Thank you for being with us!");
        context.setVariable("cmp_details", companyConfig.getName()+ ", phone: " + companyConfig.getPhone() + ", email: " + companyConfig.getEmail() + ". " + companyConfig.getGoal());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildDailyTrelloEmail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://laperacarlos.github.io/");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName() + ",");
        context.setVariable("goodbye_msg", "See you tomorrow!");
        context.setVariable("cmp_details", companyConfig.getName()+ ", phone: " + companyConfig.getPhone() + ", email: " + companyConfig.getEmail() + ". " + companyConfig.getGoal());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        return templateEngine.process("mail/daily-trello-mail", context);
    }
}
