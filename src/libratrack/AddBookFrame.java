/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libratrack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddBookFrame extends JFrame {

    private JTextField titleField, authorField, quantityField;

    public AddBookFrame() {
        setTitle("Add New Book");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Book Title:"));
        titleField = new JTextField();
        panel.add(titleField);

        panel.add(new JLabel("Author:"));
        authorField = new JTextField();
        panel.add(authorField);

        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        panel.add(quantityField);

        JButton addBtn = new JButton("Add Book");
        JButton cancelBtn = new JButton("Cancel");
        panel.add(addBtn);
        panel.add(cancelBtn);

        add(panel);

        // Action Listeners
        addBtn.addActionListener(e -> addBook());
        cancelBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void addBook() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String quantityStr = quantityField.getText().trim();

        if (title.isEmpty() || author.isEmpty() || quantityStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity must be a non-negative integer.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO books (title, author, quantity) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, title);
            pst.setString(2, author);
            pst.setInt(3, quantity);

            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Book added successfully!");
                titleField.setText("");
                authorField.setText("");
                quantityField.setText("");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error while adding book.");
            ex.printStackTrace();
        }
    }
}
