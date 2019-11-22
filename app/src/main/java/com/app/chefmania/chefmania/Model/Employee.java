package com.app.chefmania.chefmania.Model;

public class Employee {
    String name;
    int salary;
    String type;
    int availability;

    public Employee(String name, int salary, String type, int availability) {
        this.name = name;
        this.salary = salary;
        this.type = type;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }
}
