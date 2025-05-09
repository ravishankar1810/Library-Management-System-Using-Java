/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libratrack;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewBooksFrame extends JFrame {

    private JTable bookTable;

    public ViewBooksFrame() {
        setTitle("View All Books");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {"Book ID", "Title", "Author", "Quantity"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        bookTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);

        loadBooks(model);

        setVisible(true);
    }

    private void loadBooks(DefaultTableModel model) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM books";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int qty = rs.getInt("quantity");

                Object[] row = {id, title, author, qty};
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load books.");
            e.printStackTrace();
        }
    }
}

