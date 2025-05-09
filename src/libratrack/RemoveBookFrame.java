/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libratrack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RemoveBookFrame extends JFrame {

    private JTextField bookIdField;

    public RemoveBookFrame() {
        setTitle("Remove Book");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Enter Book ID:"));
        bookIdField = new JTextField();
        panel.add(bookIdField);

        JButton removeBtn = new JButton("Remove");
        JButton cancelBtn = new JButton("Cancel");
        panel.add(removeBtn);
        panel.add(cancelBtn);

        add(panel);

        // Action listeners
        removeBtn.addActionListener(e -> removeBook());
        cancelBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void removeBook() {
        String bookIdStr = bookIdField.getText().trim();

        if (bookIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Book ID.");
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Book ID format.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String query = "DELETE FROM books WHERE book_id = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, bookId);

            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Book deleted successfully!");
                bookIdField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Book ID not found.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error while deleting book.");
            e.printStackTrace();
        }
    }
}

