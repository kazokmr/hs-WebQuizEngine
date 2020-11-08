package engine.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "email is required.")
    @Email(message = "email is not correct.")
    @Pattern(regexp = ".*@.*\\..*")
    @Column(unique = true)
    @JsonProperty
    private String email;

    @NotNull(message = "password is required.")
    @Size(min = 5, message = "password is at least 5 characters.")
    @Column
    @JsonProperty
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void encodePassword() {
        password = new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(email, account.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}