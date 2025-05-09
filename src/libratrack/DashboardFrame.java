/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libratrack;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {
        setTitle("LibraTrack - Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load and set background image
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/libratrack/background.jpg"));
        BackgroundPanel background = new BackgroundPanel(bgIcon.getImage());
        background.setLayout(new GridLayout(3, 3, 15, 15));
        background.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Create buttons
        JButton addBookBtn = new JButton("Add Book");
        JButton removeBookBtn = new JButton("Remove Book");
        JButton viewBooksBtn = new JButton("View Books");
        JButton searchBookBtn = new JButton("Search Book");
        JButton addStaffBtn = new JButton("Add Staff");
        JButton removeStaffBtn = new JButton("Remove Staff");
        JButton viewStaffBtn = new JButton("View Staff");
        JButton editAdminBtn = new JButton("Edit Admin Credentials");
        JButton logoutBtn = new JButton("Logout");

        // Add and style buttons
        JButton[] buttons = {
            addBookBtn, removeBookBtn, viewBooksBtn,
            searchBookBtn, addStaffBtn, removeStaffBtn,
            viewStaffBtn, editAdminBtn, logoutBtn
        };

        for (JButton button : buttons) {
            styleButton(button);
            background.add(button);
        }

        // Add background panel
        add(background);

        // Action Listeners
        addBookBtn.addActionListener(e -> new AddBookFrame());
        removeBookBtn.addActionListener(e -> new RemoveBookFrame());
        viewBooksBtn.addActionListener(e -> new ViewBooksFrame());
        searchBookBtn.addActionListener(e -> new SearchBookFrame());
        addStaffBtn.addActionListener(e -> new AddStaffFrame());
        removeStaffBtn.addActionListener(e -> new RemoveStaffFrame());
        viewStaffBtn.addActionListener(e -> new ViewStaffFrame());
        editAdminBtn.addActionListener(e -> new EditAdminFrame());
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        setVisible(true);
    }

    // Reusable styling method
    private void styleButton(JButton button) {
        button.setOpaque(true);
        button.setBackground(new Color(30, 144, 255, 180)); // Semi-transparent blue
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    }

    // Custom JPanel with background image
    static class BackgroundPanel extends JPanel {
        private final Image bgImage;

        public BackgroundPanel(Image image) {
            this.bgImage = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bgImage != null) {
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}


