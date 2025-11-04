package edu.neu.mgen;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private ChatClient client;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;

    public LoginFrame(ChatClient client) {
        this.client = client;
        initComponents();
    }

    private void initComponents() {
        setTitle("Chat Application - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Title
        JLabel titleLabel = new JLabel("Welcome to Chat Application");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Username
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setPreferredSize(new Dimension(80, 25));
        usernameField = new JTextField(15);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        mainPanel.add(usernamePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Password
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setPreferredSize(new Dimension(80, 25));
        passwordField = new JPasswordField(15);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        mainPanel.add(passwordPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        loginButton = new JButton("Login");
        signupButton = new JButton("Sign Up");

        loginButton.setPreferredSize(new Dimension(100, 35));
        signupButton.setPreferredSize(new Dimension(100, 35));

        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        mainPanel.add(buttonPanel);

        // Hint
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        JLabel hintLabel = new JLabel("Hint: Preset users alice/bob/charlie, password: 123456");
        hintLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        hintLabel.setForeground(Color.GRAY);
        hintLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(hintLabel);

        add(mainPanel);

        // Event listeners
        loginButton.addActionListener(e -> handleLogin());
        signupButton.addActionListener(e -> handleSignup());

        // Press Enter to login
        passwordField.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password!", "Hint", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (client.login(username, password)) {
            client.showMainFrame();
        }
    }

    private void handleSignup() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password!", "Hint", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (client.signup(username, password)) {
            client.showMainFrame();
        }
    }
}