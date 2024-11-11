package az.atlacademy.etaskify.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Messages {
    MESSAGE_SEND_TO_EMAIL("messages.email.confirmation-token-sent", HttpStatus.OK),
    MESSAGE_SUCCESSFULLY("messages.successfully", HttpStatus.OK),
    MESSAGE_FORGOTTEN_PASSWORD_LINK_SENT_EMAIL("message.forgotten-password",HttpStatus.OK);
    private final String message;
    private final HttpStatus status;

    Messages(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

}
