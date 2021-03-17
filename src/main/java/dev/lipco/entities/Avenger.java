package dev.lipco.entities;

import java.util.HashSet;

public class Avenger {

    private int id;
    private String firstName;
    private String lastName;
    private boolean manager;
    private String username;
    private String password;
    private HashSet<Expense> memberExpenses;
    private String jwt;

    public Avenger(){
        this.id = 0;
        this.firstName = "";
        this.lastName = "";
        this.manager = false;
        this.username = "";
        this.password = "";
        this.memberExpenses = null;
        this.jwt = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(id <= 0) {
            throw new IllegalArgumentException("ID is invalid");
        }
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
        if(username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        if(username.equals("")) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password == null) {
            throw new IllegalArgumentException("password cannot be null");
        }
        if(password.equals("")) {
            throw new IllegalArgumentException("password cannot be empty");
        }
        this.password = password;
    }

    public HashSet<Expense> getMemberExpenses() {
        return memberExpenses;
    }

    public void setMemberExpenses(HashSet<Expense> memberExpenses) {
        if(memberExpenses == null){
            throw new IllegalArgumentException("Expenses cannot be null");
        }
        this.memberExpenses = memberExpenses;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        if(jwt == null) {
            throw new IllegalArgumentException("jwt cannot be null");
        }
        if(jwt.equals("")) {
            throw new IllegalArgumentException("jwt cannot be empty");
        }
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "Avenger{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", manager=" + manager +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", memberExpenses=" + memberExpenses +
                ", jwt='" + jwt + '\'' +
                '}';
    }
}
