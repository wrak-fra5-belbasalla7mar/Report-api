package org.fawry.reportapi.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class Rating {

    private Long submitterId;
    private Long ratedPersonId;
    private Double score;

    private String feedback;
    private Kpi kpi;
    private Cycle cycle;

    public Long getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(Long submitterId) {
        this.submitterId = submitterId;
    }

    public Long getRatedPersonId() {
        return ratedPersonId;
    }

    public void setRatedPersonId(Long ratedPersonId) {
        this.ratedPersonId = ratedPersonId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Kpi getKpi() {
        return kpi;
    }

    public void setKpi(Kpi kpi) {
        this.kpi = kpi;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }
}
