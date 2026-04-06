package co.edu.uptc.pojo;

import co.edu.uptc.pojo.enums.TypeOfMovement;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import co.edu.uptc.persistence.csv.utilities.LocalDateTimeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Accounting {
    @CsvBindByName(column = "DESCRIPTION")
    private String description;

    @CsvBindByName(column = "TYPE OF MOVEMENT")
    private TypeOfMovement typeOfMovement;

    @CsvBindByName(column = "VALUE")
    private Double value;

    @CsvCustomBindByName(column = "DATE", converter = LocalDateTimeConverter.class)
    private LocalDateTime date;
}