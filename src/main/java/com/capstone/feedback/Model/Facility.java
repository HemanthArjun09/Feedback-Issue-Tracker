package com.capstone.feedback.Model;
import com.capstone.feedback.Model.enums.Facility_Types;
import jakarta.persistence.*;
import java.util.List;
@Entity
@Table(name="facility")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="Facility_id")
    private int Facility_id;
    @Column(name ="Facility_Name", nullable = false)
    private String Facility_Name;
    @Column(name ="Facility_Location", nullable = false)
    private String Facility_Location;
    @Column(name ="Facility_Type", nullable = false)
    private Facility_Types Facility_Type;
    @ManyToOne
    @JoinColumn(name = "facility_admin_user_id")
    private User facilityAdmin;

    public User getFacilityAdmin() {
        return facilityAdmin;
    }

    public void setFacilityAdmin(User facilityAdmin) {
        this.facilityAdmin = facilityAdmin;
    }

    public int getFacility_id() {
        return Facility_id;
    }

    public void setFacility_id(int facility_id) {
        Facility_id = facility_id;
    }

    public String getFacility_Name() {
        return Facility_Name;
    }

    public void setFacility_Name(String facility_Name) {
        Facility_Name = facility_Name;
    }

    public String getFacility_Location() {
        return Facility_Location;
    }

    public void setFacility_Location(String facility_Location) {
        Facility_Location = facility_Location;
    }

    public Facility_Types getFacility_Type() {
        return Facility_Type;
    }

    public Facility() {
    }
    public void setFacility_Type(Facility_Types facility_Type) {
        Facility_Type = facility_Type;
    }

    @Override
    public String toString() {
        return "Facility{" +
                "Facility_id=" + Facility_id +
                ", Facility_Name='" + Facility_Name + '\'' +
                ", Facility_Location='" + Facility_Location + '\'' +
                ", Facility_Type='" + Facility_Type + '\'' +
                '}';
    }
}
