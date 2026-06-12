package model;

public class Instructor {

    private int id;
    private String name;
    private String deptName;
    private double salary;

    public Instructor(
            int id,
            String name,
            String deptName,
            double salary
    ) {
        this.id = id;
        this.name = name;
        this.deptName = deptName;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDeptName() {
        return deptName;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}