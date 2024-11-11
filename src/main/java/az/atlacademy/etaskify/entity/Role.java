package az.atlacademy.etaskify.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.swing.event.DocumentEvent;

import static javax.swing.JFormattedTextField.PERSIST;
import static javax.swing.event.DocumentEvent.EventType.REMOVE;

@Entity
@Setter
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToOne
    private TableDetail tableDetail;

}
