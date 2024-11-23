package org.sysc4806g30.graduateadmissionsmanagementsystem.system.model;

public class SelectionRateUpdate {
    private Long studentId;
    private Integer rating;

    // Default constructor
    public SelectionRateUpdate() {}

    // Getters and setters
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
