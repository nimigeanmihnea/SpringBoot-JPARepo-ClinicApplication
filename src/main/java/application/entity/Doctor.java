package application.entity;

import javax.persistence.*;

/**
 * Created by Mihnea on 02/05/2017.
 */

@Entity
public class Doctor {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "PNC", nullable = false, unique = true)
    private String pnc;

    @Column(name = "speciality", nullable = false)
    private String speciality;

    @OneToOne
    private User user;

    public Doctor (){}

    public Doctor(String name, String PNC, String speciality, User user) {
        this.name = name;
        this.pnc = PNC;
        this.speciality = speciality;
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
    public String getSpeciality() {
        return speciality;
    }
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", PNC='" + pnc + '\'' +
                ", speciality='" + speciality + '\'' +
                ", user=" + user +
                '}';
    }
}
