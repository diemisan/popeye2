package net.dms.fsync.synchronizer.fenix.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dminanos on 14/04/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssueFields implements Serializable {
    private Assignee assignee;
    private Status status;
    private String summary;
    private String description;
    @JsonProperty (value="issuetype")
    private JiraIssueType issueType;
    private JiraIssue parent;

    // TODO FIXME
    @JsonProperty (value="customfield_20707")
    private Integer odmStoryPoints;

    public Assignee getAssignee() {
        return assignee;
    }

    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JiraIssue getParent() {
        return parent;
    }

    public void setParent(JiraIssue parent) {
        this.parent = parent;
    }

    public JiraIssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(JiraIssueType issueType) {
        this.issueType = issueType;
    }

    public Integer getOdmStoryPoints() {
        return odmStoryPoints;
    }

    public void setOdmStoryPoints(Integer odmStoryPoints) {
        this.odmStoryPoints = odmStoryPoints;
    }

    @Override
    public String toString() {
        return "JiraIssueFields{" +
                "assignee=" + assignee +
                ", status=" + status +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
