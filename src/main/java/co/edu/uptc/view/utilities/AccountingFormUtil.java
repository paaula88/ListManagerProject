package co.edu.uptc.view.utilities;

import co.edu.uptc.config.Language;
import co.edu.uptc.pojo.Accounting;
import co.edu.uptc.pojo.enums.TypeOfMovement;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.function.Consumer;

public class AccountingFormUtil {

    private JTextField fieldDescription;
    private JComboBox<String> comboType;
    private JTextField fieldValue;

    public void build(Consumer<Accounting> onSave) {
        JDialog dialog = new JDialog();
        dialog.setTitle(Language.get("menu.accounting.add"));
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout(5, 5));
        dialog.add(buildFormPanel(), BorderLayout.CENTER);
        dialog.add(buildSaveButton(onSave, dialog), BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        fieldDescription = new JTextField();
        comboType = new JComboBox<>(new String[]{Language.get("msg.movement.income"), Language.get("msg.movement.expense")});
        fieldValue = new JTextField();
        panel.add(new JLabel(Language.get("msg.input.description.accounting")));
        panel.add(fieldDescription);
        panel.add(new JLabel(Language.get("msg.input.movementtype")));
        panel.add(comboType);
        panel.add(new JLabel(Language.get("msg.input.value.accounting")));
        panel.add(fieldValue);
        return panel;
    }

    private JButton buildSaveButton(Consumer<Accounting> onSave, JDialog dialog) {
        JButton btn = new JButton(Language.get("msg.save"));
        btn.addActionListener(e -> {
            Accounting accounting = buildAccountingFromForm();
            if (accounting == null) return;
            onSave.accept(accounting);
            dialog.dispose();
        });
        return btn;
    }

    private Accounting buildAccountingFromForm() {
        String description = fieldDescription.getText().trim();
        TypeOfMovement type = parseTypeOfMovement(comboType.getSelectedItem());
        Double value = parseValue();
        if (value == null || isEmpty(description)) return null;
        return new Accounting(description, type, value, LocalDateTime.now());
    }

    private Double parseValue() {
        try {
            double value = Double.parseDouble(fieldValue.getText().trim());
            if (value <= 0) {
                ViewUtil.showErrorMessage("msg.error.negative");
                return null;
            }
            return value;
        } catch (NumberFormatException e) {
            ViewUtil.showErrorMessage(Language.get("msg.error.onlynumbers"));
            return null;
        }
    }

    private TypeOfMovement parseTypeOfMovement(Object typeOfMovement) {
        String typString = (String) typeOfMovement;
        if (typString.equalsIgnoreCase(Language.get("msg.movement.expense"))) {
            return TypeOfMovement.EXPENSE;
        } else {
            return TypeOfMovement.INCOME;
        }
    }

    private boolean isEmpty(String description) {
        if (ViewUtil.verificatedIfIsEmpty(description)) {
            ViewUtil.showErrorMessage(Language.get("msg.error.empty"));
            return true;
        } else {
            return false;
        }
    }
}
