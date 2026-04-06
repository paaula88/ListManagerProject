package co.edu.uptc.interfaces;

import java.util.List;

public interface InterfaceCSVWrite <T>{
    boolean write(List<T> list, String fileName);
}
