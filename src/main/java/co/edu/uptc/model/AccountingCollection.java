package co.edu.uptc.model;

import co.edu.uptc.model.list.DoubleList;
import co.edu.uptc.pojo.Accounting;
import co.edu.uptc.pojo.enums.TypeOfMovement;

public class AccountingCollection extends ManagerCollection<Accounting> {

    public AccountingCollection() {
        super(DoubleList::addLast, DoubleList::removeFirst);
    }

    @Override
    public double calculateTotalValue() {
        double income = 0;
        double expenses = 0;
        ;
        for (Object a : getAll()) {
            Accounting accounting = (Accounting) a;
            if (accounting.getTypeOfMovement() == TypeOfMovement.INCOME)
                income += accounting.getValue();
            else
                expenses += accounting.getValue();
        }
        return income - expenses;
    }
}
