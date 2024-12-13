package model;
import utils.*;
import java.sql.*;
import java.util.*;
public class PersonnelSearch implements Runnable {

    private final int searchID;
    private List<Personnel> personnelList = new ArrayList<>();

    public PersonnelSearch(int searchID) {
        this.searchID = searchID;
    }

    public List<Personnel> getPersonnelList() {
        return personnelList;
    }

    @Override
    public void run() {
        String query = "SELECT * FROM Personnels WHERE person_id = ?";

        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, searchID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Personnel personnel = PersonnelProcess.mapResultSetToPersonnel(resultSet);
                    personnelList.add(personnel);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while retrieving personnel data: " + e.getMessage());
        }
    }
}