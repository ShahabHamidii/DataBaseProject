package model;

public class Student {

    private int id;
    private String name;
    private String deptName;
    private int totalCredits;

    public Student() {
    }

    public Student(int id,
                   String name,
                   String deptName,
                   int totalCredits) {

        this.id = id;
        this.name = name;
        this.deptName = deptName;
        this.totalCredits = totalCredits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}