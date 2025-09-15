package com.capstone.feedback.Model;

import com.capstone.feedback.Model.enums.Issue_Priority;
import com.capstone.feedback.Model.enums.Issue_Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name ="issues")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="Issue_Id")
    private int issue_id;

    @ManyToOne
    @JoinColumn(name ="User_Id")
    private User user;

    @ManyToOne
    @JoinColumn(name ="Facility_Id")
    private Facility facility;

    @Column(name ="Title")
    private String title;

    @Column(name ="Description")
    private String description;

    @Column(name="Priority")
    private Issue_Priority priority;

    @Column(name ="Status")
    private Issue_Status status;

    @Column(name ="created_at")
    private LocalDateTime createdAt;

    public int getIssue_id() {
        return issue_id;
    }

    public void setIssue_id(int issue_id) {
        this.issue_id = issue_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Issue_Priority getPriority() {
        return priority;
    }

    public void setPriority(Issue_Priority priority) {
        this.priority = priority;
    }

    public Issue_Status getStatus() {
        return status;
    }

    public void setStatus(Issue_Status status) {
        this.status = status;
    }

    public LocalDateTime getCreated_At() {
        return createdAt;
    }

    public void setCreated_At(LocalDateTime created_At) {
        createdAt = created_At;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "issue_id=" + issue_id +
                ", user=" + user +
                ", facility=" + facility +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", status='" + status + '\'' +
                ", Created_At=" + createdAt +
                '}';
    }
}
