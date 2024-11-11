package az.atlacademy.etaskify.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.time.LocalDate;
import java.util.List;
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private LocalDate deadLine;
    private boolean status;
    @ManyToMany
    private List<UserEntity> users;
    @ManyToOne
    private OrganizationEntity organizationEntity;

}
