package application.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Mihnea on 02/05/2017.
 */

@Entity
public class Consult {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne
    private Doctor doctor;

    @OneToOne
    private Patient patient;

    @Column(name = "startHour", nullable = false)
    private Date startHour;

    @Column(name = "endHour", nullable = false)
    private Date endHour;

    @Column(name = "details")
    private String details;

    public Consult(){}

    public Consult(String name, Doctor doctor, Patient patient, Date startHour, Date endHour) {
        this.name = name;
        this.doctor = doctor;
        this.patient = patient;
        this.startHour = startHour;
        this.endHour = endHour;
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
    public Doctor getDoctor() {
        return doctor;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public Date getStartHour() {
        return startHour;
    }
    public void setStartHour(Date startHour) {
        this.startHour = startHour;
    }
    public Date getEndHour() {
        return endHour;
    }
    public void setEndHour(Date endHour) {
        this.endHour = endHour;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Consult{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", doctor=" + doctor +
                ", patient=" + patient +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                '}';
    }


}
