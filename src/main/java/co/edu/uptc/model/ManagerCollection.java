package co.edu.uptc.model;


import co.edu.uptc.interfaces.InterfaceCSVWrite;
import co.edu.uptc.interfaces.InterfaceCSVWriteRead;
import co.edu.uptc.interfaces.InterfaceCollection;
import co.edu.uptc.interfaces.InterfaceValidator;
import co.edu.uptc.model.list.DoubleList;
import co.edu.uptc.pojo.Accounting;
import co.edu.uptc.pojo.enums.TypeOfMovement;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ManagerCollection <T> implements InterfaceCollection<T> {
    private final DoubleList<T> list = new DoubleList<>();
    private final BiConsumer <DoubleList<T>, T>addStrategy;
    private final Function <DoubleList<T>, T>removeStrategy;
    private InterfaceValidator<T> validator;
    private InterfaceCSVWrite<T> exporter;
    private String fileName;

    public ManagerCollection(BiConsumer<DoubleList<T>, T> addStrategy, Function<DoubleList<T>, T> removeStratery) {
        this.addStrategy = addStrategy;
        this.removeStrategy = removeStratery;
    }

    @Override
    public List add(T element) {
        List<String> messages= validator.validate(element);
        if (validator.isValid()){
            addStrategy.accept(list, element);
        } if (exporter instanceof InterfaceCSVWriteRead) {
            ((InterfaceCSVWriteRead<T>) exporter).append(element, fileName);
        }
        return messages;
    }

    @Override
    public T remove() {
        if (!isEmpty()){
          return   removeStrategy.apply(list);
        }
        return null;
    }

    @Override
    public List getAll() {
        return list.getAllList();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void setValidator(InterfaceValidator<T> validator) {
        this.validator= validator;

    }

    @Override
    public void setCsv(InterfaceCSVWrite<T> csv, String fileName) {
        this.exporter = csv;
        this.fileName = fileName;
        if (csv instanceof InterfaceCSVWriteRead) {
            ((InterfaceCSVWriteRead<T>) csv).read(fileName)
                    .forEach(element -> addStrategy.accept(list, element));
        }
    }

    @Override
    public boolean exportCSV() {
        return exporter.write(list.getAllList(), fileName);
    }
    public double calculateTotalValue() {
      return 0;
    }
}
