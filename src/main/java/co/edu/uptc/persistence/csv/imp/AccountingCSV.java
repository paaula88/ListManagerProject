package co.edu.uptc.persistence.csv.imp;

import co.edu.uptc.interfaces.InterfaceCSVWriteRead;
import co.edu.uptc.persistence.csv.FileCSV;
import co.edu.uptc.pojo.Accounting;

import java.util.List;

public class AccountingCSV implements InterfaceCSVWriteRead<Accounting> {

    @Override
    public List<Accounting> read(String fileName) {
        return FileCSV.read(Accounting.class, fileName);
    }

    @Override
    public boolean append(Accounting item, String fileName) {
        return FileCSV.append(item, Accounting.class, fileName);
    }

    @Override
    public boolean write(List<Accounting> list, String fileName) {
        return FileCSV.write(list, fileName);
    }
}