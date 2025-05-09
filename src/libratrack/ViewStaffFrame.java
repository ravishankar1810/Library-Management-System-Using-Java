/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libratrack;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewStaffFrame extends JFrame {

    private JTable staffTable;

    public ViewStaffFrame() {
        setTitle("View All Staff");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {"Staff ID", "Name", "Designation"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        staffTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(staffTable);
        add(scrollPane, BorderLayout.CENTER);

        loadStaff(model);

        setVisible(true);
    }

    private void loadStaff(DefaultTableModel model) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM staff";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("staff_id");
                String name = rs.getString("name");
                String designation = rs.getString("designation");

                Object[] row = {id, name, designation};
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load staff data.");
            e.printStackTrace();
        }
    }
}
