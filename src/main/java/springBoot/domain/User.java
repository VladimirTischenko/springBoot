package springBoot.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Created by Vladimir on 21.04.2020.
 */
@Entity
@Data
@NoArgsConstructor
@ToString(of = {"name"})
@EqualsAndHashCode(of = {"id"})
@Table(name="users")
public class User implements UserDetails{
    private static final long serialVersionUID = -5982381770729017785L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Password cannot be empty")
    private String password;
    private boolean enabled = false;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email is not correct")
    @Column(updatable = false, nullable = false, unique=true)
    private String email;

    private String activationCode;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = Collections.singleton(Role.USER);

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Message> messages;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
