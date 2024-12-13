package model;

public class Personnel {
    private int personID,departmentID;
    private double salary;
    private String name,surname,birthDate,hireDate;

    // Constructors
    public Personnel() {}

    public Personnel(String name, String surname, int personID, String birthDate, String hireDate, int departmentID, double salary) {
        this.name = name;
        this.surname = surname;
        this.personID = personID;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.departmentID = departmentID;
        this.salary=salary;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public int getPersonID() { return personID; }
    public void setPersonID(int personID) { this.personID = personID; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public String getHireDate() { return hireDate; }
    public void setHireDate(String hireDate) { this.hireDate = hireDate; }

    public int getDepartmentID() { return departmentID; }
    public void setDepartmentID(int departmentID) { this.departmentID = departmentID; }
    public double getSalary(){return salary;}
    public void setSalary(double salary){this.salary=salary;}

    // toString method
    @Override
    public String toString() {
        return "Personnel{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", personID=" + personID +
                ", birthDate='" + birthDate + '\'' +
                ", hireDate='" + hireDate + '\'' +
                ", departmentID='" + departmentID + '\'' +
                ", salary=" + salary +
                '}';
    }
}