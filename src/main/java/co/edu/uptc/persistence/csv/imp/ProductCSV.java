package co.edu.uptc.persistence.csv.imp;

import co.edu.uptc.interfaces.InterfaceCSVWrite;
import co.edu.uptc.persistence.csv.FileCSV;
import co.edu.uptc.pojo.Product;

import java.util.List;

public class ProductCSV implements InterfaceCSVWrite <Product>{
    @Override
    public boolean write(List <Product>list, String fileName) {
        return FileCSV.write(list, fileName);
    }
}
