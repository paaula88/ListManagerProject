package co.edu.uptc.presenter;

import co.edu.uptc.interfaces.InterfaceCollection;
import co.edu.uptc.interfaces.PresenterInterface;


import java.util.List;

public class PresenterGlobal <T> implements PresenterInterface<T> {
    InterfaceCollection <T>managerCollection;

    public void setCollection(InterfaceCollection<T> managerCollection) {
        this.managerCollection = managerCollection;
    }

    public List add(T object){
           return managerCollection.add(object);
 }

    @Override
    public T remove() {
       return (T) managerCollection.remove();
    }

    @Override
    public List getAll() {
        return managerCollection.getAll();
    }

    @Override
    public boolean exportToCSV() {
        return managerCollection.exportCSV();
    }

    @Override
    public double getTotalValues() {
        return managerCollection.calculateTotalValue();
    }

    @Override
    public boolean isEmpty() {
        return managerCollection.isEmpty();
    }


    @Override
    public void setModel(InterfaceCollection model) {

    }

}
