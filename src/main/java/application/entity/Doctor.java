package application.entity;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Column(name = "startHour", nullable = false)
    private Date startHour;

    @Column(name = "endHour", nullable = false)
    private Date endHour;

    @OneToOne
    private User user;

    public Doctor (){}

    public Doctor(String name, String PNC, String speciality, Date startHour, Date endHour, User user) {
        this.name = name;
        this.pnc = PNC;
        this.speciality = speciality;
        this.startHour = startHour;
        this.endHour = endHour;
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
    public String getPnc() {
        return pnc;
    }
    public void setPnc(String pnc) {
        this.pnc = pnc;
    }
    public String getStartHour() {
        DateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(startHour);
    }
    public void setStartHour(Date startHour) {
        this.startHour = startHour;
    }
    public String getEndHour() {
        DateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(endHour);
    }
    public void setEndHour(Date endHour) {
        this.endHour = endHour;
    }
    public Date getStart(){
        return this.startHour;
    }
    public Date getEnd(){ return this.endHour; }

    @Override
    public String toString() {
        return "Doctor " + name;
                //"id=" + id +
//                ", name='" + name + '\'' +
//                ", PNC='" + pnc + '\'' +
//                ", speciality='" + speciality + '\'' +
//                ", user=" + user +
//                '}';
    }
}
