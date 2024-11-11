package az.atlacademy.etaskify.entity;



import az.atlacademy.etaskify.dto.response.ExceptionResponse;
import az.atlacademy.etaskify.enums.Exceptions;
import az.atlacademy.etaskify.enums.TokenType;
import az.atlacademy.etaskify.exception.ApplicationException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.time.Duration;
import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.REMOVE;
import static javax.swing.JFormattedTextField.PERSIST;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private long expired;
    private boolean available;
    private String name;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    @OneToOne(cascade = {REMOVE})
    private TableDetail tableDetail;
    @ManyToOne
    private UserEntity userEntity;

    public TokenEntity(long expired, String name, TokenType tokenType, UserEntity userEntity) {
        this.expired = expired;
        this.name = name;
        this.tableDetail = TableDetail.of();
        this.userEntity = userEntity;
        this.tokenType = tokenType;
        this.available = true;

    }

    public void unusable() {
        this.available = false;
    }

    public void update(String name, long expired) {
        this.name = name;
        this.expired = expired;
        this.available = true;
        this.tableDetail.update();
    }

    public void isUsableOrElseThrow() throws ApplicationException {
        boolean orElseThrow = available && !Duration.between(LocalDateTime.now(),tableDetail.getUpdatedAt().plusSeconds(expired)).isNegative();
        if (!orElseThrow){
            throw ApplicationException.of(ExceptionResponse.of(Exceptions.TOKEN_EXPIRED_EXCEPTION.getMessage(), Exceptions.TOKEN_EXPIRED_EXCEPTION.getStatus()));
        }
    }


}
