/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libratrack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddStaffFrame extends JFrame {

    private JTextField nameField, designationField;

    public AddStaffFrame() {
        setTitle("Add Staff Member");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Staff Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Designation:"));
        designationField = new JTextField();
        panel.add(designationField);

        JButton addBtn = new JButton("Add");
        JButton cancelBtn = new JButton("Cancel");
        panel.add(addBtn);
        panel.add(cancelBtn);

        add(panel);

        // Action Listeners
        addBtn.addActionListener(e -> addStaff());
        cancelBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void addStaff() {
        String name = nameField.getText().trim();
        String designation = designationField.getText().trim();

        if (name.isEmpty() || designation.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO staff (name, designation) VALUES (?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, designation);

            int result = pst.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Staff member added successfully.");
                nameField.setText("");
                designationField.setText("");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error while adding staff.");
            e.printStackTrace();
        }
    }
}

