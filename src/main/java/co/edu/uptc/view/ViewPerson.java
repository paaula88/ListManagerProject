package co.edu.uptc.view;

import co.edu.uptc.config.Language;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.pojo.Person;
import co.edu.uptc.view.utilities.MakeMenu;
import co.edu.uptc.view.utilities.PersonFormUtil;
import co.edu.uptc.view.utilities.PersonListUtil;
import co.edu.uptc.view.utilities.ViewUtil;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@NoArgsConstructor
public class ViewPerson implements ViewInterface<Person> {
    private PresenterInterface<Person> presenter;
    private JDialog dialog;


    @Override
    public String title() {
        return Language.get("menu.person.title");
    }

    @Override
    public void setPresenter(PresenterInterface presenter) {
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

    private void addPerson() {
        new PersonFormUtil().build(person -> {
            ViewUtil.showMessages(presenter.add(person));
        });

    }

    private void onOptionSelected(int index) {
        switch (index) {
            case 0 -> addPerson();
            case 1 -> deletePerson();
            case 2 -> showList();
            case 3 -> exportToCsv();
            case 4 -> goBack();
        }
    }

    private static ArrayList<String> buildOptions() {
        ArrayList<String> options = new ArrayList<>();
        options.add(Language.get("menu.person.add"));
        options.add(Language.get("menu.person.delete"));
        options.add(Language.get("menu.person.list"));
        options.add(Language.get("menu.person.export"));
        options.add(Language.get("menu.back"));
        return options;
    }

    private void deletePerson() {
        if (presenter.isEmpty()) {
            ViewUtil.showErrorMessage(Language.get("table.empty"));
        } else {
            showDeletePerson((Person) presenter.remove());
        }


    }

    private void showDeletePerson(Person person) {
        StringBuilder deletePerson = new StringBuilder();
        deletePerson.append(person.getName());
        deletePerson.append(" " + person.getLastName());
        deletePerson.append("\n" + Language.get("msg.id") + person.getId());
        ViewUtil.showDeletedObject(deletePerson.toString(), Language.get("msg.deleted.person"));
    }

    private void showList() {
        new PersonListUtil().build(presenter.getAll());
    }

    private void exportToCsv() {
        if (presenter.exportToCSV()) {
            ViewUtil.showMessage(Language.get("msg.success.process"));
        } else ViewUtil.showErrorMessage(Language.get("msg.failed.process"));
    }

    private void goBack() {
        dialog.dispose();

    }


}
