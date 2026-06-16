package model;

public class TimeSlot {

    private String timeSlotId;
    private String day;
    private String startTime;
    private String endTime;

    public TimeSlot(
            String timeSlotId,
            String day,
            String startTime,
            String endTime
    ) {

        this.timeSlotId = timeSlotId;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTimeSlotId() {
        return timeSlotId;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {

        return timeSlotId +
                " - " +
                day;
    }
}