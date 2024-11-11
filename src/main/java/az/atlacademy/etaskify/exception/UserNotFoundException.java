package az.atlacademy.etaskify.exception;

import az.atlacademy.etaskify.dto.response.ExceptionResponse;
import lombok.Getter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Getter
public class UserNotFoundException extends UsernameNotFoundException {

    private final ExceptionResponse response;

    public UserNotFoundException( ExceptionResponse response) {
        super(response.getMessage());
        this.response = response;
    }

    public static UserNotFoundException of( ExceptionResponse exceptionResponse) {
        return new UserNotFoundException(exceptionResponse);
    }

}
