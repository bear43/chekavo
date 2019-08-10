package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Adventure
{
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @OneToMany(mappedBy = "adventure", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Turn> turns = new ArrayList<>();

    @Column
    private String number;

    @ManyToOne
    @JsonBackReference
    private User user;

    @Column
    private boolean done;

    public Adventure(User user, List<Turn> turns, String number)
    {
        this.user = user;
        if(turns != null)
            this.turns.addAll(turns);
        this.number = number;
    }

    public Adventure(List<Turn> turns, String number)
    {
        this(null, turns, number);
    }

    public Adventure() {}

    public Adventure(String number)
    {
        this(null, number);
    }

    @Override
    public String toString()
    {
        return String.format("#%s, attempts: %d", number, turns.size());
    }
}
