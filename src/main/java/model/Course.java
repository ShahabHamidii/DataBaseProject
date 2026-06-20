package model;

public class Course {

    private String courseId;

    private String title;

    private String deptName;

    private int credits;

    public Course(
            String courseId,
            String title,
            String deptName,
            int credits
    ) {

        this.courseId = courseId;

        this.title = title;

        this.deptName = deptName;

        this.credits = credits;
    }

    public String getCourseId() {
        return courseId;
    }


    public String getTitle() {
        return title;
    }

    public String getDeptName() {
        return deptName;
    }

    public int getCredits() {
        return credits;
    }

    @Override
    public String toString() {

        return courseId + " - " + title;
    }
}