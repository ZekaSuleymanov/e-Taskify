package az.atlacademy.etaskify.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TaskResponse {

    private long id;
    private String title;
    private String description;
    private LocalDate deadLine;
    private boolean status;

}
