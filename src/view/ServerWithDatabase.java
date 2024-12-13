package view;
import java.io.*;
import java.net.*;
import java.sql.*;

public class ServerWithDatabase {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Waiting for clients...");

            while (true) {
                // Accept a new client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("A client is connected!");

                // Communicate with the client
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            // Get username and password from client
            String username = in.readLine();
            String password = in.readLine();

            // Check username and password
            if (validateUser(username, password)) {
                out.println("1"); // Successful login
                System.out.println("Correct entry: " + username);
            } else {
                out.println("0"); // Failed login
                System.out.println("Incorrect entry: " + username);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean validateUser(String username, String password) {
        String url = "jdbc:sqlite:company.db";
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set query parameters
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Run the query and check the results
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if there is a result

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}