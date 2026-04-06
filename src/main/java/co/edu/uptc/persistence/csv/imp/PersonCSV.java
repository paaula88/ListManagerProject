package co.edu.uptc.persistence.csv.imp;

import co.edu.uptc.interfaces.InterfaceCSVWrite;
import co.edu.uptc.persistence.csv.FileCSV;
import co.edu.uptc.pojo.Person;

import java.util.List;

public class PersonCSV implements InterfaceCSVWrite<Person> {
    @Override
    public boolean write(List<Person> list, String fileName) {
        return FileCSV.write(list, fileName);
    }}
