/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libratrack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EditAdminFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField currentPassField, newPassField;

    public EditAdminFrame() {
        setTitle("Edit Admin Credentials");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Current Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Current Password:"));
        currentPassField = new JPasswordField();
        panel.add(currentPassField);

        panel.add(new JLabel("New Password:"));
        newPassField = new JPasswordField();
        panel.add(newPassField);

        JButton updateBtn = new JButton("Update");
        JButton cancelBtn = new JButton("Cancel");
        panel.add(updateBtn);
        panel.add(cancelBtn);

        add(panel);

        // Action listeners
        updateBtn.addActionListener(e -> updateCredentials());
        cancelBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void updateCredentials() {
        String username = usernameField.getText().trim();
        String currentPass = String.valueOf(currentPassField.getPassword());
        String newPass = String.valueOf(newPassField.getPassword());

        if (username.isEmpty() || currentPass.isEmpty() || newPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String checkQuery = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement checkPst = conn.prepareStatement(checkQuery);
            checkPst.setString(1, username);
            checkPst.setString(2, currentPass);
            ResultSet rs = checkPst.executeQuery();

            if (rs.next()) {
                String updateQuery = "UPDATE admin SET password = ? WHERE username = ?";
                PreparedStatement updatePst = conn.prepareStatement(updateQuery);
                updatePst.setString(1, newPass);
                updatePst.setString(2, username);

                int updated = updatePst.executeUpdate();
                if (updated > 0) {
                    JOptionPane.showMessageDialog(this, "Password updated successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Update failed.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid current username/password.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error occurred.");
            e.printStackTrace();
        }
    }
}

