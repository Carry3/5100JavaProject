package edu.neu.mgen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {
    private JComboBox<String> colorDropdown;
    private JTextField outputField;
    private CirclePanel circlePanel;
    
    public App() {
        setTitle("Color Circle GUI");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Create the circle panel
        circlePanel = new CirclePanel();
        circlePanel.setPreferredSize(new Dimension(300, 300));
        add(circlePanel, BorderLayout.CENTER);
        
        // Create the control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 2, 10, 10));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Dropdown label and options
        JLabel dropdownLabel = new JLabel("Select Color:");
        String[] colors = {"", "Red", "Blue", "Green", "Yellow", "Orange", "Purple"};
        colorDropdown = new JComboBox<>(colors);
        
        // Output label and text field
        JLabel outputLabel = new JLabel("Current Color:");
        outputField = new JTextField();
        outputField.setEditable(false);
        outputField.setBackground(Color.WHITE);
        
        controlPanel.add(dropdownLabel);
        controlPanel.add(colorDropdown);
        controlPanel.add(outputLabel);
        controlPanel.add(outputField);
        
        add(controlPanel, BorderLayout.SOUTH);
        
        // Add event listener
        colorDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedColor = (String) colorDropdown.getSelectedItem();
                updateColor(selectedColor);
            }
        });
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void updateColor(String colorName) {
        Color color = null;
        
        if (colorName == null || colorName.isEmpty()) {
            color = null;
            outputField.setText("");
        } else {
            switch (colorName) {
                case "Red":
                    color = Color.RED;
                    break;
                case "Blue":
                    color = Color.BLUE;
                    break;
                case "Green":
                    color = Color.GREEN;
                    break;
                case "Yellow":
                    color = Color.YELLOW;
                    break;
                case "Orange":
                    color = Color.ORANGE;
                    break;
                case "Purple":
                    color = new Color(128, 0, 128);
                    break;
            }
            outputField.setText(colorName);
        }
        
        circlePanel.setCircleColor(color);
    }
    
    // Inner class: Circle drawing panel
    class CirclePanel extends JPanel {
        private Color circleColor = null;
        
        public void setCircleColor(Color color) {
            this.circleColor = color;
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                 RenderingHints.VALUE_ANTIALIAS_ON);
            
            int diameter = Math.min(getWidth(), getHeight()) - 40;
            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;
            
            // Draw the circle border
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(x, y, diameter, diameter);
            
            // Fill the circle with color (if selected)
            if (circleColor != null) {
                g2d.setColor(circleColor);
                g2d.fillOval(x, y, diameter, diameter);
                
                // Redraw the border for clarity
                g2d.setColor(Color.BLACK);
                g2d.drawOval(x, y, diameter, diameter);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App();
            }
        });
    }
}
