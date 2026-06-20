package model;

public class User {
    public enum Role { ADMIN, STUDENT, INSTRUCTOR }

    private String username;
    private Role role;

    public User(String username, Role role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() { return username; }
    public Role getRole() { return role; }

}