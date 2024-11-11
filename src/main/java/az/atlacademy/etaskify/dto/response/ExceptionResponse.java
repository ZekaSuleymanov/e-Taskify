package az.atlacademy.etaskify.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class ExceptionResponse {

    private String message;
    private HttpStatus status;

    public ExceptionResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public static ExceptionResponse of(String message, HttpStatus status) {
        return new ExceptionResponse(message, status);
    }

}
