package az.atlacademy.etaskify.dto.request;

import az.atlacademy.etaskify.dto.response.ExceptionResponse;
import az.atlacademy.etaskify.exception.ApplicationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PasswordRequest {

    @NotBlank(message ="NOT_BLANK_EXCEPTION")
    @Pattern(regexp = "[a-zA-z0-9]{6,20}", message = "PASSWORD_PATTERN_EXCEPTION")
    private final String password;
    @NotBlank(message = "NOT_BLANK_EXCEPTION")
    @Pattern(regexp = "[a-zA-z0-9]{6,20}", message = "PASSWORD_PATTERN_EXCEPTION")
    private final String repeatPassword;

    public PasswordRequest(String password, String repeatPassword) {
        this.password = password;
        this.repeatPassword = repeatPassword;
        elseThrow();
    }

    private void elseThrow() throws ApplicationException {
        if (!this.password.equals(this.repeatPassword)) throw ApplicationException.of(ExceptionResponse.of("NOT_SAME_PASSWORD", HttpStatus.NOT_FOUND));
    }

}
