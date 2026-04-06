package co.edu.uptc.interfaces;

import javax.swing.*;

public interface ViewInterface <T>{
    String title();
void setPresenter(PresenterInterface<T> presenter);
void start(JFrame parent);
}
