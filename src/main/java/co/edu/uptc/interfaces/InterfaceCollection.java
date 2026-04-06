package co.edu.uptc.interfaces;

import java.util.List;

public interface InterfaceCollection <T>{
    List<String> add(T element);
    T remove();
    List<T> getAll();
    boolean isEmpty();
    void setValidator(InterfaceValidator<T> validator);
    void setCsv(InterfaceCSVWrite<T> csv, String fileName);
    boolean exportCSV();
    double calculateTotalValue();
}
