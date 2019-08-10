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
public class User
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

    @Column
    private int score;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Adventure> adventureList = new ArrayList<>();


    public User()
    {}

    public User(String login, String password, boolean active, int authority, int score)
    {
        this.login = login;
        this.password = password;
        this.active = active;
        this.authority = authority;
        this.score = score;
    }

    public User(String login, String password, int score)
    {
        this(login, password, true, 0, score);
    }

    public User(String login, String password)
    {
        this(login, password, 0);
    }

    @Override
    public String toString()
    {
        return String.format("login: %s | avg score: %d", login, score);
    }
}
