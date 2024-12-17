package repository;
import model.*;
import java.sql.*;
import java.util.*;

import java.sql.*;
import java.util.*;

public class PersonnelDAO {
    private static final String DB_URL = "jdbc:sqlite:company.db"; // SQLite veritabanı URL

    // Veritabanı bağlantısını kuran metod
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Personel ekleme
    public void addPersonnel(Personnel personnel) {
        String sql = "INSERT INTO personnel (name, surname, person_id, birth_date, hire_date, department_id, salary) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personnel.getName());
            stmt.setString(2, personnel.getSurname());
            stmt.setInt(3, personnel.getPersonID());
            stmt.setString(4, personnel.getBirthDate());
            stmt.setString(5, personnel.getHireDate());
            stmt.setInt(6, personnel.getDepartmentID());
            stmt.setDouble(7, personnel.getSalary());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Personel güncelleme
    public void updatePersonnel(Personnel personnel) {
        String sql = "UPDATE personnel SET name = ?, surname = ?, birth_date = ?, hire_date = ?, department_id = ?, salary = ? WHERE person_id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personnel.getName());
            stmt.setString(2, personnel.getSurname());
            stmt.setString(3, personnel.getBirthDate());
            stmt.setString(4, personnel.getHireDate());
            stmt.setInt(5, personnel.getDepartmentID());
            stmt.setDouble(6, personnel.getSalary());
            stmt.setInt(7, personnel.getPersonID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Personel silme
    public void deletePersonnel(int personID) {
        String sql = "DELETE FROM personnel WHERE person_id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, personID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Personel arama (arama terimi ile)
    public List<Personnel> searchPersonnel(String searchTerm) {
        List<Personnel> resultList = new ArrayList<>();
        String sql = "SELECT * FROM personnel WHERE name LIKE ? OR surname LIKE ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchTerm + "%");
            stmt.setString(2, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Personnel personnel = new Personnel(
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("person_id"),
                        rs.getString("birth_date"),
                        rs.getString("hire_date"),
                        rs.getInt("department_id"),
                        rs.getDouble("salary")
                );
                resultList.add(personnel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    // Personel tüm listeyi getirme
    public List<Personnel> getAllPersonnel() {
        List<Personnel> resultList = new ArrayList<>();
        String sql = "SELECT * FROM personnel";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Personnel personnel = new Personnel(
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("person_id"),
                        rs.getString("birth_date"),
                        rs.getString("hire_date"),
                        rs.getInt("department_id"),
                        rs.getDouble("salary")
                );
                resultList.add(personnel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}