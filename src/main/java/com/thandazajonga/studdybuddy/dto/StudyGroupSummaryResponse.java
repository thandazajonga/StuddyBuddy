package com.thandazajonga.studdybuddy.dto;

public class StudyGroupSummaryResponse {
    private Integer studyGroupId;

    private String studyGroupName;

    private String description;

    private String courseCode;

    private Integer maxMembers;

    private Integer currentMembers;

    private String ownerName;

    public StudyGroupSummaryResponse() {
    }

    public StudyGroupSummaryResponse(Integer studyGroupId, String studyGroupName, String description, String courseCode, Integer maxMembers, Integer currentMembers, String ownerName) {
        this.studyGroupId = studyGroupId;
        this.studyGroupName = studyGroupName;
        this.description = description;
        this.courseCode = courseCode;
        this.maxMembers = maxMembers;
        this.currentMembers = currentMembers;
        this.ownerName = ownerName;
    }

    public Integer getStudyGroupId() {
        return studyGroupId;
    }

    public void setStudyGroupId(Integer id) {
        this.studyGroupId = id;
    }

    public String getStudyGroupName() {
        return studyGroupName;
    }

    public void setStudyGroupName(String name) {
        this.studyGroupName = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Integer getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(Integer maxMembers) {
        this.maxMembers = maxMembers;
    }

    public Integer getCurrentMembers() {
        return currentMembers;
    }

    public void setCurrentMembers(Integer currentMembers) {
        this.currentMembers = currentMembers;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
