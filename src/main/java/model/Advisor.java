package model;

public class Advisor {

    private int studentId;
    private int instructorId;

    public Advisor(
            int studentId,
            int instructorId
    ) {

        this.studentId = studentId;
        this.instructorId = instructorId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getInstructorId() {
        return instructorId;
    }
}