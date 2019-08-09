package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Turn
{
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Adventure adventure;

    @Column
    private int turnNumber;

    @Column
    private String userNumber;

    @Column
    private int bullCount;

    @Column
    private int cowCount;

    @Column
    private boolean lastTurn;

    public Turn() {}

    public Turn(Adventure adventure, int turnNumber, String userNumber, int bullCount, int cowCount, boolean lastTurn)
    {
        this.adventure = adventure;
        this.turnNumber = turnNumber;
        this.userNumber = userNumber;
        this.bullCount = bullCount;
        this.cowCount = cowCount;
        this.lastTurn = lastTurn;
    }

    public Turn(int turnNumber, String userNumber, int bullCount, int cowCount, boolean lastTurn)
    {
        this(null, turnNumber, userNumber, bullCount, cowCount, lastTurn);
    }

    public Turn(String userNumber, int bullCount, int cowCount)
    {
        this( 0, userNumber, bullCount, cowCount, false);
    }

    @Override
    public String toString()
    {
        return String.format("#%d | %s %dБ%dК %s",
                turnNumber, userNumber,
                bullCount, cowCount,
                lastTurn ? "(Число отгадано)" : "");
    }
}
