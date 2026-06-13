package model;

public class TeachingAssignment {

    private int instructorId;

    private String courseId;

    private String secId;

    private String semester;

    private int year;

    public TeachingAssignment(
            int instructorId,
            String courseId,
            String secId,
            String semester,
            int year
    ) {

        this.instructorId = instructorId;
        this.courseId = courseId;
        this.secId = secId;
        this.semester = semester;
        this.year = year;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getSecId() {
        return secId;
    }

    public String getSemester() {
        return semester;
    }

    public int getYear() {
        return year;
    }
}