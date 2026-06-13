package model;

public class Section {

    private String courseId;
    private String secId;
    private String semester;
    private int year;
    private String building;
    private String roomNumber;
    private String timeSlotId;

    public Section(
            String courseId,
            String secId,
            String semester,
            int year,
            String building,
            String roomNumber,
            String timeSlotId
    ) {

        this.courseId = courseId;
        this.secId = secId;
        this.semester = semester;
        this.year = year;
        this.building = building;
        this.roomNumber = roomNumber;
        this.timeSlotId = timeSlotId;
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

    public String getBuilding() {
        return building;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getTimeSlotId() {
        return timeSlotId;
    }
}