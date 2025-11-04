package edu.neu.mgen;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainChatFrame extends JFrame {
    private ChatClient client;
    private String username;
    private JList<String> userList;
    private DefaultListModel<String> userListModel;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private String currentRecipient = null;
    private Map<String, StringBuilder> chatHistory = new HashMap<>(); // Save chat history for each user
    private static final String CHAT_DIR = "chat_history";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public MainChatFrame(ChatClient client, String username) {
        this.client = client;
        this.username = username;
        // Create chat history directory
        try {
            Files.createDirectories(Paths.get(CHAT_DIR));
        } catch (IOException e) {
            System.err.println("Failed to create chat history directory: " + e.getMessage());
        }
        // Load historical chat records
        loadChatHistory();
        initComponents();
    }

    private String getChatFileName(String otherUser) {
        // Use sorted username combination as filename to ensure chat history for the same pair of users is saved in the same file
        String[] users = {username, otherUser};
        java.util.Arrays.sort(users);
        return CHAT_DIR + File.separator + users[0] + "_" + users[1] + ".txt";
    }

    private void loadChatHistory() {
        // This method will be called after user list update to load all possible chat records
        // Load for now, wait for user list update then load corresponding user records
    }

    private void loadChatHistoryForUser(String otherUser) {
        String fileName = getChatFileName(otherUser);
        try {
            if (Files.exists(Paths.get(fileName))) {
                StringBuilder history = new StringBuilder();
                try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // File format: [timestamp] message, use directly
                        history.append(line).append("\n");
                    }
                }
                chatHistory.put(otherUser, history);
            } else {
                // If no history record, initialize an empty StringBuilder
                chatHistory.putIfAbsent(otherUser, new StringBuilder());
            }
        } catch (IOException e) {
            System.err.println("Failed to load chat history: " + e.getMessage());
        }
    }

    private void saveMessageToFile(String otherUser, String message) {
        String fileName = getChatFileName(otherUser);
        try {
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
                String timestamp = LocalDateTime.now().format(TIME_FORMATTER);
                // Add timestamp when saving to file
                writer.println("[" + timestamp + "] " + message);
            }
        } catch (IOException e) {
            System.err.println("Failed to save chat history: " + e.getMessage());
        }
    }

    private void initComponents() {
        setTitle("Chat Application - " + username);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left user list
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setPreferredSize(new Dimension(180, 0));

        JLabel userListLabel = new JLabel("Online Users");
        userListLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        userListLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setFont(new Font("SansSerif", Font.PLAIN, 13));
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane userScrollPane = new JScrollPane(userList);

        leftPanel.add(userListLabel, BorderLayout.NORTH);
        leftPanel.add(userScrollPane, BorderLayout.CENTER);

        // Right chat area
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));

        // Chat title
        JLabel chatLabel = new JLabel("Please select a user to start chatting");
        chatLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        chatLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Chat history
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);

        // Message input area
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        messageField = new JTextField();
        messageField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        messageField.setEnabled(false);

        sendButton = new JButton("Send");
        sendButton.setPreferredSize(new Dimension(80, 35));
        sendButton.setEnabled(false);

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        rightPanel.add(chatLabel, BorderLayout.NORTH);
        rightPanel.add(chatScrollPane, BorderLayout.CENTER);
        rightPanel.add(inputPanel, BorderLayout.SOUTH);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Event listeners
        userList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = userList.getSelectedValue();
                if (selected != null && !selected.equals(username)) {
                    currentRecipient = selected;
                    chatLabel.setText("Chatting with " + selected);
                    messageField.setEnabled(true);
                    sendButton.setEnabled(true);
                    // Load chat history for this user (if not loaded yet)
                    if (!chatHistory.containsKey(selected)) {
                        loadChatHistoryForUser(selected);
                    }
                    // Display chat history for this user
                    String history = chatHistory.getOrDefault(selected, new StringBuilder()).toString();
                    // If history is empty, display empty string
                    chatArea.setText(history.trim().isEmpty() ? "" : history);
                    chatArea.setCaretPosition(chatArea.getDocument().getLength());
                } else {
                    currentRecipient = null;
                    chatLabel.setText("Please select a user to start chatting");
                    messageField.setEnabled(false);
                    sendButton.setEnabled(false);
                    chatArea.setText("");
                }
            }
        });

        sendButton.addActionListener(e -> sendMessage());
        messageField.addActionListener(e -> sendMessage());

        // Disconnect when closing window
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                client.disconnect();
                System.exit(0);
            }
        });
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty() && currentRecipient != null) {
            client.sendMessage(currentRecipient, message);
            messageField.setText("");
        }
    }

    public void updateUserList(String[] users) {
        SwingUtilities.invokeLater(() -> {
            userListModel.clear();
            for (String user : users) {
                if (!user.equals(username)) {
                    userListModel.addElement(user);
                }
            }
        });
    }

    public void displayMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            // Parse message, extract sender
            // Message format: "username: message" or "Me: message"
            String sender = null;
            String actualMessage = message;
            
            if (message.startsWith("Me: ")) {
                // Message sent by self, sent to currently selected recipient
                sender = currentRecipient;
                actualMessage = message;
            } else if (message.contains(": ")) {
                // Message sent by others, extract sender
                int colonIndex = message.indexOf(": ");
                sender = message.substring(0, colonIndex);
                actualMessage = message;
            }
            
            // If sender exists, save to corresponding chat history
            if (sender != null && !sender.equals(username)) {
                // Generate message with timestamp
                String timestamp = LocalDateTime.now().format(TIME_FORMATTER);
                String messageWithTimestamp = "[" + timestamp + "] " + actualMessage;
                
                chatHistory.putIfAbsent(sender, new StringBuilder());
                // Save to memory (with timestamp)
                chatHistory.get(sender).append(messageWithTimestamp).append("\n");
                // Save to file (with timestamp)
                saveMessageToFile(sender, actualMessage);
                
                // Only display message if this user is currently selected
                if (sender.equals(currentRecipient)) {
                    chatArea.append(messageWithTimestamp + "\n");
                    chatArea.setCaretPosition(chatArea.getDocument().getLength());
                }
            } else if (message.startsWith("Me: ") && currentRecipient != null) {
                // Message sent by self, save to current recipient's history
                // Generate message with timestamp
                String timestamp = LocalDateTime.now().format(TIME_FORMATTER);
                String messageWithTimestamp = "[" + timestamp + "] " + actualMessage;
                
                chatHistory.putIfAbsent(currentRecipient, new StringBuilder());
                // Save to memory (with timestamp)
                chatHistory.get(currentRecipient).append(messageWithTimestamp).append("\n");
                // Save to file (with timestamp)
                saveMessageToFile(currentRecipient, actualMessage);
                chatArea.append(messageWithTimestamp + "\n");
                chatArea.setCaretPosition(chatArea.getDocument().getLength());
            }
        });
    }
}