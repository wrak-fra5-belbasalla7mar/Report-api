package org.fawry.reportapi.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
public class Team {
    private Long id;
    private Long managerId;
    private String teamName;
    private List<TeamMember> members;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<TeamMember> getMembers() {
        return members;
    }

    public void setMembers(List<TeamMember> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", managerId=" + managerId +
                ", name='" + teamName + '\'' +
                ", members=" + members +
                '}';
    }
}
