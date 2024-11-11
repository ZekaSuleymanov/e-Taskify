package az.atlacademy.etaskify.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Size(min = 3,message = "Userin adi minimum 3 simvol olmalidir !!!")
    @Size(min=20,message = "Userin adi maksimum 20 simvol olmalidir")
    private String name;
    @Size(min = 5,message = "Userin soyadi minimum 5 simvol olmalidir !!!")

    private String surname;
    @Column(unique = true)
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    @ManyToOne
    private Role role;
    @ManyToOne
    private OrganizationEntity organizationEntity;
    @OneToOne(cascade = {PERSIST, REMOVE})
    private TableDetail tableDetail;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }
}
