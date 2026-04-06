package co.edu.uptc.view.utilities;

import co.edu.uptc.config.AppConfig;
import co.edu.uptc.config.Language;
import co.edu.uptc.dto.PersonDTO;
import co.edu.uptc.pojo.Person;
import co.edu.uptc.pojo.enums.Gender;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PersonListUtil {

    private static final int PAGE_SIZE = AppConfig.getPropertyInt("pagination.size");
    private int currentPage = 0;
    private List<Person> persons;
    private DefaultTableModel tableModel;
    private JLabel labelPage;

    public void build(List<Person> persons) {
        this.persons = persons;
        JDialog dialog = new JDialog();
        dialog.setTitle(Language.get("person.list"));
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout(5, 5));
        dialog.add(buildTable(), BorderLayout.CENTER);
        dialog.add(buildPaginationPanel(), BorderLayout.SOUTH);
        loadPage();
        dialog.setVisible(true);
    }

    private JScrollPane buildTable() {
        String[] columns = {Language.get("table.person.name"), Language.get("table.person.lastname"), Language.get("table.person.gender"), Language.get("table.person.age")};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        applyAlignmentHeader(table);
        applyAlignment(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        return new JScrollPane(table);
    }

    private JPanel buildPaginationPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton btnPrev = new JButton(Language.get("msg.previous"));
        JButton btnNext = new JButton(Language.get("msg.next"));
        labelPage = new JLabel();
        btnPrev.addActionListener(e -> goToPreviousPage());
        btnNext.addActionListener(e -> goToNextPage());
        panel.add(btnPrev);
        panel.add(labelPage);
        panel.add(btnNext);
        return panel;
    }

    private void loadPage() {
        tableModel.setRowCount(0);
        int from = currentPage * PAGE_SIZE;
        int to = Math.min(from + PAGE_SIZE, persons.size());
        persons.subList(from, to).forEach(this::addRow);
        updatePageLabel();
    }

    private void addRow(Person person) {
        PersonDTO dto = new PersonDTO(person);
        tableModel.addRow(new Object[]{
                dto.getName(),
                dto.getLastName(),
                parseGenderToString(dto.getGender()),
                dto.getAge()
        });
    }

    private void goToNextPage() {
        if ((currentPage + 1) * PAGE_SIZE < persons.size()) {
            currentPage++;
            loadPage();
        }
    }

    private void goToPreviousPage() {
        if (currentPage > 0) {
            currentPage--;
            loadPage();
        }
    }

    private void updatePageLabel() {
        int totalPages = (int) Math.ceil((double) persons.size() / PAGE_SIZE);
        totalPages = Math.max(totalPages, 1);
        labelPage.setText(Language.get("table.page") + " " + (currentPage + 1) + " " + Language.get("table.of") + " " + totalPages);

    }

    private String parseGenderToString(Gender gender) {
        if (gender.equals(Gender.FEMININE)) {
            return Language.get("msg.gender.femenine");
        } else {
            return Language.get("msg.gender.masculine");
        }
    }

    private void applyAlignmentHeader(JTable table) {
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer)
                table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.LEFT);
    }

    private void applyAlignment(JTable table) {
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
        }
    }
}
