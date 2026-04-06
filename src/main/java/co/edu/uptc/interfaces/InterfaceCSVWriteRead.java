package co.edu.uptc.interfaces;

import java.util.List;

public interface InterfaceCSVWriteRead<T> extends InterfaceCSVWrite<T> {
    List<T> read(String fileName);
    boolean append(T item, String fileName);
}
