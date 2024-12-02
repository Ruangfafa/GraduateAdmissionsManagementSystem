package org.sysc4806g30.graduateadmissionsmanagementsystem.system.requestPackage;

public class FileRequest {
    // This class is used for sending required value from assignedStudents.js to EventController
    private String fileType;
    private Long studentUID;
    public String getFileType() {return this.fileType;}
    public Long getStudentUID() {return this.studentUID;}
}
