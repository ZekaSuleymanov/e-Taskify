package az.atlacademy.etaskify.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class TableDetail {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean status;

    public TableDetail() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = true;
    }

    public static TableDetail of() {
        return new TableDetail();
    }

    public void update() {
        this.updatedAt = LocalDateTime.now();
    }


}
