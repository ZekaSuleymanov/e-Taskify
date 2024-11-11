package az.atlacademy.etaskify.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequest {

    private String name;
    @NotBlank(message = "NOT_BLANK_EXCEPTION")
    private String surname;
    @NotBlank(message = "NOT_BLANK_EXCEPTION")
    private String username;
    @Email(message = "EMAIL_EXCEPTION")
    @NotBlank(message = "NOT_BLANK_EXCEPTION")
    private String email;

}
