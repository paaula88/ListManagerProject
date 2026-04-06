package co.edu.uptc.pojo;

import co.edu.uptc.pojo.enums.UnitOfMeasure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    private int id;
    private String description;
    private UnitOfMeasure unitOfMeasure;
    private Double price;

}