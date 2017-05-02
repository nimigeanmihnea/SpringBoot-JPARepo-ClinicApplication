package application.entity;

import javax.persistence.*;

/**
 * Created by Mihnea on 02/05/2017.
 */

@Entity
public class Secretary {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "PNC", nullable = false, unique = true)
    private String pnc;

    @OneToOne
    private User user;

    public Secretary() {}

    public Secretary(String name, String PNC, User user) {
        this.name = name;
        this.pnc = PNC;
        this.user = user;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPNC() {
        return pnc;
    }
    public void setPNC(String PNC) {
        this.pnc = PNC;
    }

    @Override
    public String toString() {
        return "Secretary{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", PNC='" + pnc + '\'' +
                ", user=" + user +
                '}';
    }
}
