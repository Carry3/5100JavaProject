package edu.neu.mgen;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8888;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;
    private LoginFrame loginFrame;
    private MainChatFrame mainFrame;
    private String[] initialUserList = null; // Save initial user list

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatClient().start());
    }

    public void start() {
        loginFrame = new LoginFrame(this);
        loginFrame.setVisible(true);
    }

    public boolean connect() {
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to connect to server!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean login(String username, String password) {
        if (!connect())
            return false;

        out.println("LOGIN:" + username + ":" + password);
        try {
            String response = in.readLine();
            if (response.startsWith("SUCCESS")) {
                this.username = username;
                // Read user list message before starting listener (server will send immediately after login success)
                try {
                    String userListMsg = in.readLine();
                    if (userListMsg != null && userListMsg.startsWith("USERS:")) {
                        String userListStr = userListMsg.substring(6);
                        // Handle empty list case
                        String[] users = userListStr.isEmpty() ? new String[0] : userListStr.split(",");
                        initialUserList = users;
                        // If main window is already created, update user list immediately
                        if (mainFrame != null) {
                            mainFrame.updateUserList(users);
                        }
                    }
                } catch (IOException e) {
                    // If reading fails, the listener will handle subsequent user list updates
                    System.err.println("Failed to read user list: " + e.getMessage());
                }
                // Now start the message listener to handle subsequent messages
                startMessageListener();
                return true;
            } else {
                String error = response.split(":", 2)[1];
                JOptionPane.showMessageDialog(null, error, "Login Failed", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean signup(String username, String password) {
        if (!connect())
            return false;

        out.println("SIGNUP:" + username + ":" + password);
        try {
            String response = in.readLine();
            if (response.startsWith("SUCCESS")) {
                this.username = username;
                // Read user list message before starting listener (server will send immediately after signup success)
                try {
                    String userListMsg = in.readLine();
                    if (userListMsg != null && userListMsg.startsWith("USERS:")) {
                        String userListStr = userListMsg.substring(6);
                        // Handle empty list case
                        String[] users = userListStr.isEmpty() ? new String[0] : userListStr.split(",");
                        initialUserList = users;
                        // If main window is already created, update user list immediately
                        if (mainFrame != null) {
                            mainFrame.updateUserList(users);
                        }
                    }
                } catch (IOException e) {
                    // If reading fails, the listener will handle subsequent user list updates
                    System.err.println("Failed to read user list: " + e.getMessage());
                }
                // Now start the message listener to handle subsequent messages
                startMessageListener();
                return true;
            } else {
                String error = response.split(":", 2)[1];
                JOptionPane.showMessageDialog(null, error, "Sign Up Failed", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showMainFrame() {
        loginFrame.dispose();
        mainFrame = new MainChatFrame(this, username);
        // If initial user list is saved, update immediately
        if (initialUserList != null) {
            mainFrame.updateUserList(initialUserList);
            initialUserList = null; // Clear, subsequent updates handled by listener
        }
        mainFrame.setVisible(true);
    }

    private void startMessageListener() {
        new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("USERS:")) {
                        String[] users = message.substring(6).split(",");
                        if (mainFrame != null) {
                            mainFrame.updateUserList(users);
                        }
                    } else if (message.startsWith("MESSAGE:")) {
                        String msg = message.substring(8);
                        if (mainFrame != null) {
                            mainFrame.displayMessage(msg);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Connection lost");
            }
        }).start();
    }

    public void sendMessage(String recipient, String message) {
        out.println("MSG:" + recipient + ":" + message);
        if (mainFrame != null) {
            mainFrame.displayMessage("Me: " + message);
        }
    }

    public void disconnect() {
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}