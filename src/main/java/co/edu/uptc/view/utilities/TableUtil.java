package co.edu.uptc.view.utilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TableUtil {

    public void build(String title, String[] columns, List<Object[]> rows) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout(5, 5));
        dialog.add(buildTable(columns, rows), BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private JScrollPane buildTable(String[] columns, List<Object[]> rows) {
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        rows.forEach(model::addRow);
        return new JScrollPane(new JTable(model));
    }
}