package model;

public class Prerequisite {

    private String courseId;
    private String prereqId;

    public Prerequisite(
            String courseId,
            String prereqId
    ) {
        this.courseId = courseId;
        this.prereqId = prereqId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getPrereqId() {
        return prereqId;
    }
}