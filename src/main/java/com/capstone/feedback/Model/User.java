package com.capstone.feedback.Model;

import com.capstone.feedback.Model.enums.User_Types;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="User_Id")
    private int User_Id;

    @Column(name ="Email")
    private String Email;

    @Column(name ="FirstName", nullable = false)
    private String FirstName;

    @Column(name ="LastName", nullable = false)
    private String LastName;

    @Column(name ="MobileNumer", nullable = false)
    private String MobileNum;

    @Column(name ="Role", nullable = false)
    private User_Types Role;

    public int getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(int user_Id) {
        User_Id = user_Id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMobileNum() {
        return MobileNum;
    }

    public void setMobileNum(String mobileNum) {
        MobileNum = mobileNum;
    }

    public User_Types getRole() {
        return Role;
    }

    public void setRole(User_Types role) {
        Role = role;
    }
}
