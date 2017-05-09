package application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mihnea on 02/05/2017.
 */

@Entity
public class Patient {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "PNC", nullable = false, unique = true)
    private String pnc;

    @Column(name = "INC", nullable = false, unique = true)
    private String inc;

    @Column(name = "birthDate", nullable = false)
    private Date birthDate;

    @Column(name = "address", nullable = false)
    private String address;

    public Patient(){ }

    public Patient(String name, String PNC, String INC, Date birthDate, String address) {
        this.name = name;
        this.pnc = PNC;
        this.inc = INC;
        this.birthDate = birthDate;
        this.address = address;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPNC(String PNC) {
        this.pnc = PNC;
    }
    public void setINC(String INC) {
        this.inc = INC;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPnc() {
        return pnc;
    }
    public String getInc() {
        return inc;
    }
    public String getBirthDate() {
        DateFormat format = new SimpleDateFormat("dd/MM/YYYY");
        return format.format(birthDate);
    }
    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Patient " + name;
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", PNC='" + pnc + '\'' +
//                ", INC='" + inc + '\'' +
//                ", birthDate=" + birthDate +
//                ", address='" + address + '\'' +
//                '}';
    }
}
