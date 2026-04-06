package co.edu.uptc.view;

import co.edu.uptc.config.Language;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.pojo.Product;
import co.edu.uptc.pojo.enums.UnitOfMeasure;
import co.edu.uptc.view.utilities.MakeMenu;
import co.edu.uptc.view.utilities.ProductFormUtil;
import co.edu.uptc.view.utilities.TableUtil;
import co.edu.uptc.view.utilities.ViewUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;


public class ViewProduct implements ViewInterface<Product> {
    private PresenterInterface<Product> presenter;
    private JDialog dialog;

    @Override
    public String title() {
        return Language.get("menu.product.title");
    }

    @Override
    public void setPresenter(PresenterInterface<Product> presenter) {
        this.presenter = presenter;

    }

    @Override
    public void start(JFrame parent) {
        dialog = new JDialog(parent);
        dialog.setSize(300, 300);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());
        dialog.add(MakeMenu.build(title(), buildOptions(), this::onOptionSelected));
        dialog.setVisible(true);
    }

    private static ArrayList<String> buildOptions() {
        ArrayList<String> options = new ArrayList<>();
        options.add(Language.get("menu.product.add"));
        options.add(Language.get("menu.product.delete"));
        options.add(Language.get("menu.product.list"));
        options.add(Language.get("menu.product.export"));
        options.add(Language.get("menu.back"));
        return options;
    }

    private void onOptionSelected(int index) {
        switch (index) {
            case 0 -> addProduct();
            case 1 -> deleteProduct();
            case 2 -> showList();
            case 3 -> exportToCsv();
            case 4 -> goBack();
        }
    }

    private void addProduct() {
        new ProductFormUtil().build(product ->
                ViewUtil.showMessages(presenter.add(product))
        );

    }

    private void deleteProduct() {
        if (presenter.isEmpty()) {
            ViewUtil.showErrorMessage(Language.get("table.empty"));
        } else {
            showDeleteProduct(presenter.remove());
        }
    }

    private void showDeleteProduct(Product product) {
        StringBuilder deleteProduct = new StringBuilder();
        deleteProduct.append(Language.get("msg.description") + product.getDescription() + "\n");
        deleteProduct.append(Language.get("msg.price") + product.getPrice());
        ViewUtil.showDeletedObject(deleteProduct.toString(), Language.get("msg.deleted.product"));
    }

    private void showList() {
        String[] columns = {Language.get("msg.id"), Language.get("msg.description"), Language.get("msg.unit"), Language.get("msg.price")};
        List<Object[]> rows = new ArrayList<>();
        List<Product> products = (presenter.getAll());
        for (Product p : products) {
            rows.add(new Object[]{
                    p.getId(),
                    p.getDescription(),
                    parseUnitOfMeasureToString(p.getUnitOfMeasure()),
                    p.getPrice()
            });
        }
        new TableUtil().build(Language.get("products.list"), columns, rows);
    }

    private void exportToCsv() {
        if (presenter.exportToCSV()) {
            ViewUtil.showMessage(Language.get("msg.success.process"));
        } else ViewUtil.showErrorMessage(Language.get("msg.failed.process"));
    }

    private void goBack() {
        dialog.dispose();
    }

    private String parseUnitOfMeasureToString(UnitOfMeasure type) {
        return switch (type) {
            case BUNDLE -> Language.get("msg.unit.bundle");
            case KILO -> Language.get("msg.unit.kilo");
            case TON -> Language.get("msg.unit.ton");
            case POUND -> Language.get("msg.unit.pound");
        };
    }
}
