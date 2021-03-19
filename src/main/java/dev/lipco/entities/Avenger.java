package dev.lipco.entities;

public class Avenger {

    private int id;

    private String firstName;

    private String lastName;

    private boolean manager;

    private String username;

    private String password;

    public Avenger() {}

    public Avenger(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Avenger(int id, String firstName, String lastName, boolean manager, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.manager = manager;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Avenger{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", manager='" + manager + '\'' +
                ", username='" + username + '\'' +
                ", password" +
                '}';
    }
}
