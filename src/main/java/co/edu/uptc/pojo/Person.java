package co.edu.uptc.pojo;

import co.edu.uptc.pojo.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {

    private int id;
    private String name;
    private String lastName;
    private Gender gender;
    private LocalDate birthDate;

}