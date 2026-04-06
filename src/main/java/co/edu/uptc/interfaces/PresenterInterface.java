package co.edu.uptc.interfaces;

import java.util.List;

public interface PresenterInterface<T>{
 void setModel(InterfaceCollection<T> model);
    List<String> add(T element);
    T remove();
    List<T> getAll();
    boolean exportToCSV();
    double getTotalValues();
    boolean isEmpty();
}
