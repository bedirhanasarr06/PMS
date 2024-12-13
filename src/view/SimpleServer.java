package view;
import java.io.*;
import java.net.*;
public class SimpleServer {
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
            if ("admin".equals(username) && "12345".equals(password)) {
                out.println("1"); // Login successful
                System.out.println("Correct entry: " + username);
            } else {
                out.println("0"); // Login failed
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
}