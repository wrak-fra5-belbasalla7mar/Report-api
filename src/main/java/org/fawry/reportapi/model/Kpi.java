package org.fawry.reportapi.model;


import lombok.Data;
public class Kpi {

    private Long id;
    private String name;

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


    public Kpi(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Kpi() {
    }
}
