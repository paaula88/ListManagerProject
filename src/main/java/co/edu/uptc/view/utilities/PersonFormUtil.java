package co.edu.uptc.view.utilities;

import co.edu.uptc.config.Language;
import co.edu.uptc.pojo.Person;
import co.edu.uptc.pojo.enums.Gender;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Consumer;

public class PersonFormUtil {
    private JTextField fieldName;
    private JTextField fieldLastName;
    private JComboBox<String> comboGender;
    private JSpinner spinnerDate;

    public JFrame build(Consumer<Person> onSave) {
        JFrame frame = new JFrame(Language.get("menu.person.add"));
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout(5, 5));
        frame.add(buildFormPanel(), BorderLayout.CENTER);
        frame.add(buildSaveButton(onSave, frame), BorderLayout.SOUTH);
        frame.setVisible(true);
        return frame;
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        initFields();
        addFieldsToPanel(panel);
        return panel;
    }

    private void initFields() {
        fieldName = new JTextField();
        fieldLastName = new JTextField();
        comboGender = new JComboBox<>(new String[]{Language.get("msg.gender.masculine"), Language.get("msg.gender.femenine")});
        spinnerDate = buildDateSpinner();
    }

    private void addFieldsToPanel(JPanel panel) {
        panel.add(new JLabel(Language.get("msg.input.name")));
        panel.add(fieldName);
        panel.add(new JLabel(Language.get("msg.input.lastname")));
        panel.add(fieldLastName);
        panel.add(new JLabel(Language.get("msg.input.gender")));
        panel.add(comboGender);
        panel.add(new JLabel(Language.get("msg.input.birthdate")));
        panel.add(spinnerDate);
    }

    private JSpinner buildDateSpinner() {
        JSpinner spinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(editor);
        return spinner;
    }

    private JButton buildSaveButton(Consumer<Person> onSave, JFrame frame) {
        JButton btn = new JButton(Language.get("msg.save"));
        btn.addActionListener(e -> {
            try {
                validateInformation(fieldName.getText().trim());
                validateInformation(fieldLastName.getText().trim());
                Person person = buildPersonFromForm();
                onSave.accept(person);
            } catch (Exception ex) {
                ViewUtil.showErrorMessage(ex.getMessage());
            }
            frame.dispose();
        });
        return btn;
    }

    private Person buildPersonFromForm() {
        return new Person(
                0,
                fieldName.getText().trim(),
                fieldLastName.getText().trim(),
                readGender((String) comboGender.getSelectedItem()),
                extractDate()
        );
    }

    private void validateInformation(String message) throws Exception {
        if (message.matches(".*\\d.*")) {
            throw new Exception(Language.get("msg.error.nonumbers"));
        }
    }

    private LocalDate extractDate() {
        Date date = (Date) spinnerDate.getValue();
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Gender readGender(String op) {
        if (op.equals(Language.get("msg.gender.masculine"))) {
            return Gender.MASCULINE;
        } else if (op.equals(Language.get("msg.gender.femenine"))) {
            return Gender.FEMININE;
        }
        return null;
    }

}
