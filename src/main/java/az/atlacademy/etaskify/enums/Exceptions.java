package az.atlacademy.etaskify.enums;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Exceptions {

    NOT_SAME_PASSWORD("exception.not-same-password",HttpStatus.BAD_REQUEST),
    NOT_FOUND("exception.not-found",HttpStatus.NOT_FOUND),
    TOKEN_EXPIRED_EXCEPTION("exception.time.expired-refresh",HttpStatus.BAD_REQUEST),
    BAD_CREDENTIALS_EXCEPTION("exception.bad-credentials",HttpStatus.BAD_REQUEST),
    USERNAME_IS_UNAVAILABLE_EXCEPTION("exception.username-unavailable",HttpStatus.BAD_REQUEST);
    private final String message;
    private final HttpStatus status;

    Exceptions(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

}
