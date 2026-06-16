package model;

public class Department {

    private String deptName;
    private String building;
    private double budget;

    public Department(
            String deptName,
            String building,
            double budget
    ) {
        this.deptName = deptName;
        this.building = building;
        this.budget = budget;
    }

    public String getDeptName() {
        return deptName;
    }

    public String getBuilding() {
        return building;
    }

    public double getBudget() {
        return budget;
    }

    @Override
    public String toString() {
        return deptName;
    }
}