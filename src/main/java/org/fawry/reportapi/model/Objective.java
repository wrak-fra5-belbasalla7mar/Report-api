package org.fawry.reportapi.model;
import lombok.Data;

import java.time.LocalDate;

public class Objective {
    private Long id;
    private String state;
    private Long assignedUserId;
    private String title;
    private String description;
    private LocalDate deadline;
    private Long cycleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
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

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Long getCycleId() {
        return cycleId;
    }

    public void setCycleId(Long cycleId) {
        this.cycleId = cycleId;
    }

    public Objective(Long cycleId, LocalDate deadline, String description, String title, Long assignedUserId, String state, Long id) {
        this.cycleId = cycleId;
        this.deadline = deadline;
        this.description = description;
        this.title = title;
        this.assignedUserId = assignedUserId;
        this.state = state;
        this.id = id;
    }

    public Objective() {
    }
}
