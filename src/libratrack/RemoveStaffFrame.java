/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libratrack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RemoveStaffFrame extends JFrame {

    private JTextField staffIdField;

    public RemoveStaffFrame() {
        setTitle("Remove Staff Member");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Enter Staff ID:"));
        staffIdField = new JTextField();
        panel.add(staffIdField);

        JButton removeBtn = new JButton("Remove");
        JButton cancelBtn = new JButton("Cancel");
        panel.add(removeBtn);
        panel.add(cancelBtn);

        add(panel);

        // Action Listeners
        removeBtn.addActionListener(e -> removeStaff());
        cancelBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void removeStaff() {
        String staffIdStr = staffIdField.getText().trim();

        if (staffIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Staff ID.");
            return;
        }

        int staffId;
        try {
            staffId = Integer.parseInt(staffIdStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Staff ID format.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String query = "DELETE FROM staff WHERE staff_id = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, staffId);

            int result = pst.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Staff member removed successfully.");
                staffIdField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Staff ID not found.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error while removing staff.");
            e.printStackTrace();
        }
    }
}


