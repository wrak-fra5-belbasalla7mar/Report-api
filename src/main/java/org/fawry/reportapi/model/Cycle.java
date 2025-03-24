package org.fawry.reportapi.model;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;


public class Cycle {

    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String state;
    private List<Kpi> kpis;
    private List<Objective> objectives;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Kpi> getKpis() {
        return kpis;
    }

    public void setKpis(List<Kpi> kpis) {
        this.kpis = kpis;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

    public Cycle(Long id, String name, LocalDate startDate, LocalDate endDate, String state, List<Kpi> kpis, List<Objective> objectives) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.kpis = kpis;
        this.objectives = objectives;
    }

    public Cycle() {
    }
}
