/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libratrack;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SearchBookFrame extends JFrame {

    private JTextField searchField;
    private JTable resultTable;
    private DefaultTableModel model;

    public SearchBookFrame() {
        setTitle("Search Books");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchField = new JTextField();
        JButton searchBtn = new JButton("Search");

        topPanel.add(new JLabel("Enter Book Title or Author:"), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchBtn, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"Book ID", "Title", "Author", "Quantity"};
        model = new DefaultTableModel(columns, 0);
        resultTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);

        searchBtn.addActionListener(e -> searchBooks());

        setVisible(true);
    }

    private void searchBooks() {
        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search keyword.");
            return;
        }

        model.setRowCount(0); // Clear previous results

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ?";
            PreparedStatement pst = conn.prepareStatement(query);
            String likeKeyword = "%" + keyword + "%";
            pst.setString(1, likeKeyword);
            pst.setString(2, likeKeyword);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int qty = rs.getInt("quantity");

                Object[] row = {id, title, author, qty};
                model.addRow(row);
            }

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No books found.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error while searching books.");
            e.printStackTrace();
        }
    }
}
