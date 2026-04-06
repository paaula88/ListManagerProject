package co.edu.uptc.view.utilities;

import co.edu.uptc.config.Language;
import co.edu.uptc.pojo.Product;
import co.edu.uptc.pojo.enums.UnitOfMeasure;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.function.Consumer;

public class ProductFormUtil {

    private JTextField fieldDescription;
    private JComboBox<String> comboUnit;
    private JTextField fieldPrice;
    private static final Map<String, UnitOfMeasure> UNIT_MAP = Map.of(
            "msg.unit.kilo", UnitOfMeasure.KILO,
            "msg.unit.ton", UnitOfMeasure.TON,
            "msg.unit.bundle", UnitOfMeasure.BUNDLE,
            "msg.unit.pound", UnitOfMeasure.POUND
    );

    public void build(Consumer<Product> onSave) {
        JDialog dialog = new JDialog();
        dialog.setTitle(Language.get("menu.product.add"));
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
        comboUnit = new JComboBox<>(new String[]{Language.get("msg.unit.kilo"), Language.get("msg.unit.pound"), Language.get("msg.unit.bundle"), Language.get("msg.unit.ton")});
        fieldPrice = new JTextField();
        panel.add(new JLabel(Language.get("msg.input.description")));
        panel.add(fieldDescription);
        panel.add(new JLabel(Language.get("msg.input.unitmeasure")));
        panel.add(comboUnit);
        panel.add(new JLabel(Language.get("msg.input.price")));
        panel.add(fieldPrice);
        return panel;
    }

    private JButton buildSaveButton(Consumer<Product> onSave, JDialog dialog) {
        JButton btn = new JButton(Language.get("msg.save"));
        btn.addActionListener(e -> {
            Product product = buildProductFromForm();
            if (product == null) return;
            onSave.accept(product);
            dialog.dispose();
        });
        return btn;
    }

    private Product buildProductFromForm() {
        String description = fieldDescription.getText();
        UnitOfMeasure unit = parseUnitOfMeasure((String) comboUnit.getSelectedItem());
        Double price = parsePrice();
        if (price == null || isEmpty(description)) return null;
        return new Product(0, description, unit, price);
    }

    private Double parsePrice() {
        try {
            double price = Double.parseDouble(fieldPrice.getText().trim());
            if (price <= 0) {
                ViewUtil.showErrorMessage("msg.error.negative");
                return null;
            }
            return price;
        } catch (NumberFormatException e) {
            ViewUtil.showErrorMessage(Language.get("msg.error.onlynumbers"));
            return null;
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

    private UnitOfMeasure parseUnitOfMeasure(String unitOfMeasure) {
        return UNIT_MAP.entrySet().stream()
                .filter(e -> unitOfMeasure.equalsIgnoreCase(Language.get(e.getKey())))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(UnitOfMeasure.POUND);
    }
}