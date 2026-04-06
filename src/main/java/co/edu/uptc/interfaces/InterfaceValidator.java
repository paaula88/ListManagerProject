package co.edu.uptc.interfaces;

import java.util.List;

public interface InterfaceValidator <T>{
    List<String> validate(T n);
    Boolean isValid();
}
