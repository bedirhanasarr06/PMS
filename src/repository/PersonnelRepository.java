package repository;

import model.Personnel;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonnelRepository {

    public List<Personnel> getAllPersonnel() {
        List<Personnel> personnelList = new ArrayList<>();
        String query = "SELECT * FROM Personnels";

        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Personnel personnel = new Personnel();
                personnel.setName(resultSet.getString("name"));
                personnel.setSurname(resultSet.getString("surname"));
                personnel.setPersonID(resultSet.getInt("person_id"));
                personnel.setBirthDate(resultSet.getString("birth_date"));
                personnel.setHireDate(resultSet.getString("hire_date"));
                personnel.setDepartmentID(resultSet.getInt("department_id"));
                personnel.setSalary(resultSet.getDouble("salary"));
                personnelList.add(personnel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to retrieve personnel: " + e.getMessage());
        }

        return personnelList;
    }

    public Personnel getPersonnelById(int personID) {
        String query = "SELECT * FROM Personnels WHERE person_id = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, personID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to retrieve personnel by ID: " + e.getMessage());
        }
        return null;
    }

    public void addPersonnel(Personnel personnel) {
        String query = "INSERT INTO Personnels (name, surname, person_id, birth_date, hire_date, department_id, salary) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, personnel.getName());
            preparedStatement.setString(2, personnel.getSurname());
            preparedStatement.setInt(3, personnel.getPersonID());
            preparedStatement.setString(4, personnel.getBirthDate());
            preparedStatement.setString(5, personnel.getHireDate());
            preparedStatement.setInt(6, personnel.getDepartmentID());
            preparedStatement.setDouble(7,personnel.getSalary());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add personnel: " + e.getMessage());
        }
    }

    public void updatePersonnel(Personnel personnel) {
        String query = "UPDATE Personnels SET name = ?, surname = ?, birth_date = ?, hire_date = ?, department_id = ?, salary = ? WHERE person_id = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, personnel.getName());
            preparedStatement.setString(2, personnel.getSurname());
            preparedStatement.setString(3, personnel.getBirthDate());
            preparedStatement.setString(4, personnel.getHireDate());
            preparedStatement.setInt(5, personnel.getDepartmentID());
            preparedStatement.setDouble(6,personnel.getSalary());
            preparedStatement.setInt(7, personnel.getPersonID());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("No personnel found with ID: " + personnel.getPersonID());
            }

            System.out.println("Personnel updated successfully. ID: " + personnel.getPersonID());

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update personnel: " + e.getMessage());
        }
    }

    public void deletePersonnelById(int personID) {
        String query = "DELETE FROM Personnels WHERE person_id = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, personID);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("No personnel found with ID: " + personID);
            }

            System.out.println("Deleted personnel with ID: " + personID);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete personnel: " + e.getMessage());
        }
    }
}