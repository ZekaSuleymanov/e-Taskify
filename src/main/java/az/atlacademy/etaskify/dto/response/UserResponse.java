package az.atlacademy.etaskify.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

    private long id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private OrganizationResponse organization;

}
