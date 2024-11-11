package az.atlacademy.etaskify.util;

import az.atlacademy.etaskify.entity.TaskEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    public String confirmEmailSubjectBuilder(String token, String username) {
        return "<p> Hi, " + username + ", <p>" +
                "<p>Thank you for registering with us," +
                "Please, follow the link below to complete your registration.<p>" +
                "<a href=\"" + createConfirmUrl(token) + "\">Verify your email to active your account<a>" +
                "<p> Thank you <br> Users Registration Portal Service";

    }

    private String createConfirmUrl(String token) {
        return "http://35.222.111.242:80/api/v1/users/confirmation?token=" + token;
    }
    public String taskMessageSubjectBuilder(TaskEntity taskEntity) {
        return  "Hi, your next task" +
                "\ntitle      : " + taskEntity.getTitle() +
                "\ndescription:" + taskEntity.getDescription() +
                "\ndeadLine   : " + taskEntity.getDeadLine();
    }

}
