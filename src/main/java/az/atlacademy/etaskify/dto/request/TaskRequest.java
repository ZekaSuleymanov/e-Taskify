package az.atlacademy.etaskify.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class TaskRequest {

    @NotBlank(message = "NOT_BLANK_EXCEPTION")
    private String title;
    @NotBlank(message = "NOT_BLANK_EXCEPTION")
    private String description;
    private LocalDate deadLine;
    @NotNull
    private List<Long> users;

}
