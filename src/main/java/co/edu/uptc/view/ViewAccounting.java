package co.edu.uptc.view;

import co.edu.uptc.config.Language;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.pojo.Accounting;
import co.edu.uptc.pojo.enums.TypeOfMovement;
import co.edu.uptc.util.Format;
import co.edu.uptc.view.utilities.AccountingFormUtil;
import co.edu.uptc.view.utilities.MakeMenu;
import co.edu.uptc.view.utilities.TableUtil;
import co.edu.uptc.view.utilities.ViewUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ViewAccounting implements ViewInterface<Accounting> {
    private JDialog dialog;
    private PresenterInterface<Accounting> presenter;

    @Override
    public String title() {
        return Language.get("menu.accounting.title");
    }

    @Override
    public void setPresenter(PresenterInterface<Accounting> presenter) {
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

    private void onOptionSelected(int index) {
        switch (index) {
            case 0 -> addMovement();
            case 1 -> showList();
            case 2 -> exportToCsv();
            case 3 -> goBack();
        }
    }

    private static ArrayList<String> buildOptions() {
        ArrayList<String> options = new ArrayList<>();
        options.add(Language.get("menu.accounting.add"));
        options.add(Language.get("menu.accounting.list"));
        options.add(Language.get("menu.accounting.export"));
        options.add(Language.get("menu.back"));
        return options;
    }

    private void addMovement() {
        new AccountingFormUtil().build(accounting ->
                ViewUtil.showMessages(presenter.add(accounting))
        );

    }

    private void showList() {
        String[] columns = {Language.get("msg.description"), Language.get("msg.input.movementtype"), Language.get("msg.value")};
        java.util.List<Object[]> rows = new ArrayList<>();
        java.util.List<Accounting> movements = (List<Accounting>) presenter.getAll();
            for (Accounting p : movements) {
                rows.add(new Object[]{
                        p.getDescription(),
                        parseToStringTypeOfMovement(p.getTypeOfMovement()),
                        p.getValue()
                });
            }
            rows.add(new Object[]{Format.formarToBoldType(Language.get("msg.total")), Format.formarToBoldType(String.valueOf(presenter.getTotalValues()))});
            new TableUtil().build(Language.get("accounting.list"), columns, rows);

    }

    private void exportToCsv() {
        if (presenter.exportToCSV()) {
            ViewUtil.showMessage(Language.get("msg.success.process"));
        } else ViewUtil.showErrorMessage(Language.get("msg.failed.process"));

    }

    private void goBack() {
        dialog.dispose();

    }

    private String parseToStringTypeOfMovement(TypeOfMovement type) {
        switch (type) {
            case TypeOfMovement.EXPENSE -> {
                return Language.get("msg.movement.expense");
            }
            case TypeOfMovement.INCOME -> {
                return Language.get("msg.movement.income");
            }
        }
        return null;
    }

}
