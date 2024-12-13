package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
public class SimpleClientGUI {
    public static void main(String[] args) {
        // Main window
        JFrame frame = new JFrame("Log in");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        frame.add(usernameLabel);
        frame.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        frame.add(passwordLabel);
        frame.add(passwordField);

        JButton loginButton = new JButton("Log in");
        frame.add(loginButton);

        JLabel messageLabel = new JLabel("");
        frame.add(messageLabel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get username and password
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());

                    // Connect to server
                    Socket socket = new Socket("localhost", 12345);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    // Send username and password
                    out.println(username);
                    out.println(password);

                    // Get response from server
                    String response = in.readLine();
                    if ("1".equals(response)) {
                        messageLabel.setText("Login successful!");
                        showMenu(frame);
                    } else {
                        messageLabel.setText("Login failed!");
                    }

                    // Kaynakları kapat
                    socket.close();
                } catch (IOException ex) {
                    messageLabel.setText("Failed to connect to server!");
                }
            }
        });
        frame.setVisible(true);
    }

    private static void showMenu(JFrame frame) {
        frame.dispose();

        JFrame menuFrame = new JFrame("Main Window");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(300, 200);
        menuFrame.setLayout(new GridLayout(3, 1));

        JButton profileButton = new JButton("Profile");
        JButton settingsButton = new JButton("Settings");
        JButton logoutButton = new JButton("Exit");

        menuFrame.add(profileButton);
        menuFrame.add(settingsButton);
        menuFrame.add(logoutButton);

        menuFrame.setVisible(true);
    }
}
