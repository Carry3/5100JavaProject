package edu.neu.mgen;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final int PORT = 8888;
    private static final String USERS_FILE = "users.txt";
    private static Map<String, ClientHandler> clients = new ConcurrentHashMap<>();
    private static Map<String, String> users = new ConcurrentHashMap<>(); // username:password
    private static final Object fileLock = new Object(); // Lock for file operations

    public static void main(String[] args) {
        // Load users from file
        loadUsersFromFile();

        System.out.println("Chat Server started on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadUsersFromFile() {
        try {
            if (Files.exists(Paths.get(USERS_FILE))) {
                try (BufferedReader reader = Files.newBufferedReader(Paths.get(USERS_FILE))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        line = line.trim();
                        if (!line.isEmpty() && line.contains(":")) {
                            String[] parts = line.split(":", 2);
                            if (parts.length == 2) {
                                users.put(parts[0], parts[1]);
                            }
                        }
                    }
                }
                System.out.println("Loaded " + users.size() + " users from " + USERS_FILE);
            } else {
                // Create default users file if it doesn't exist
                createDefaultUsersFile();
            }
        } catch (IOException e) {
            System.err.println("Failed to load users from file: " + e.getMessage());
            // Create default users file on error
            createDefaultUsersFile();
        }
    }

    private static void createDefaultUsersFile() {
        try {
            try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
                writer.println("alice:123456");
                writer.println("bob:123456");
                writer.println("wilmon:123456");
            }
            // Load the default users
            users.put("alice", "123456");
            users.put("bob", "123456");
            users.put("wilmon", "123456");
            System.out.println("Created default users file: " + USERS_FILE);
        } catch (IOException e) {
            System.err.println("Failed to create default users file: " + e.getMessage());
        }
    }

    private static void saveUserToFile(String username, String password) {
        synchronized (fileLock) {
            try {
                // Append new user to file
                try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE, true))) {
                    writer.println(username + ":" + password);
                }
            } catch (IOException e) {
                System.err.println("Failed to save user to file: " + e.getMessage());
            }
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Handle login/register
                String authRequest;
                while ((authRequest = in.readLine()) != null) {
                    String[] parts = authRequest.split(":");
                    String action = parts[0];
                    String user = parts[1];
                    String pass = parts[2];

                    if (action.equals("LOGIN")) {
                        if (users.containsKey(user) && users.get(user).equals(pass)) {
                            if (clients.containsKey(user)) {
                                out.println("ERROR:User already logged in");
                            } else {
                                username = user;
                                clients.put(username, this);
                                out.println("SUCCESS:Login successful");
                                // Send user list to newly logged in user immediately
                                sendUserList();
                                broadcastUserList();
                                break;
                            }
                        } else {
                            out.println("ERROR:Invalid credentials");
                        }
                    } else if (action.equals("SIGNUP")) {
                        if (users.containsKey(user)) {
                            out.println("ERROR:Username already exists");
                        } else {
                            users.put(user, pass);
                            // Save new user to file
                            saveUserToFile(user, pass);
                            username = user;
                            clients.put(username, this);
                            out.println("SUCCESS:Signup successful");
                            // Send user list to newly registered user immediately
                            sendUserList();
                            broadcastUserList();
                            break;
                        }
                    }
                }

                // Handle messages
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("MSG:")) {
                        String[] parts = message.substring(4).split(":", 2);
                        String recipient = parts[0];
                        String msg = parts[1];
                        sendMessage(recipient, username + ": " + msg);
                    }
                }
            } catch (IOException e) {
                System.out.println("Client disconnected: " + username);
            } finally {
                cleanup();
            }
        }

        private void sendMessage(String recipient, String message) {
            ClientHandler recipientHandler = clients.get(recipient);
            if (recipientHandler != null) {
                recipientHandler.out.println("MESSAGE:" + message);
            }
        }

        private void sendUserList() {
            // Send user list to current client
            String userList = "USERS:" + String.join(",", clients.keySet());
            out.println(userList);
        }

        private void broadcastUserList() {
            String userList = "USERS:" + String.join(",", clients.keySet());
            for (ClientHandler client : clients.values()) {
                client.out.println(userList);
            }
        }

        private void cleanup() {
            if (username != null) {
                clients.remove(username);
                broadcastUserList();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}