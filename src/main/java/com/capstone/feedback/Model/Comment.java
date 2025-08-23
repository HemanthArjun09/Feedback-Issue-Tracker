package com.capstone.feedback.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob // Good for long text
    private String content;

    private LocalDateTime createdAt;

    // Is this a special comment from a facility admin?
    private boolean isAdminComment;

    // The issue this comment belongs to
    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    // The user who posted the comment
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // --- For Nested Replies ---
    // The parent comment (if this is a reply)
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    // The list of replies to this comment
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> replies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isAdminComment() {
        return isAdminComment;
    }

    public void setAdminComment(boolean adminComment) {
        isAdminComment = adminComment;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }
}