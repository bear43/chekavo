package model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usr")
@Data
public class User implements Serializable
{
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private boolean active;

    @Column
    private int authority;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Adventure> adventureList = new ArrayList<>();


    public User()
    {}

    public User(String login, String password, boolean active, int authority)
    {
        this.login = login;
        this.password = password;
        this.active = active;
        this.authority = authority;
    }

    public User(String login, String password)
    {
        this(login, password, true, 0);
    }

    @Override
    public String toString()
    {
        return String.format("login: %s", login);
    }
}
