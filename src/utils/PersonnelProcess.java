package utils;
import model.*;
import java.sql.*;
public class PersonnelProcess {
    public static Personnel mapResultSetToPersonnel(ResultSet resultSet) throws SQLException {
        Personnel personnel = new Personnel();
        personnel.setName(resultSet.getString("name"));
        personnel.setSurname(resultSet.getString("surname"));
        personnel.setPersonID(resultSet.getInt("person_id"));
        personnel.setBirthDate(resultSet.getString("birth_date"));
        personnel.setHireDate(resultSet.getString("hire_date"));
        personnel.setDepartmentID(resultSet.getInt("department_id"));
        personnel.setSalary(resultSet.getDouble("salary"));
        return personnel;
    }
}