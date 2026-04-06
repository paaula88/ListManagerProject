package co.edu.uptc.dto;

import co.edu.uptc.pojo.Person;
import co.edu.uptc.pojo.enums.Gender;
import lombok.Data;

import java.time.LocalDate;
import java.time.Period;
@Data
public class PersonDTO {
    private String name;
    private String lastName;
    private Gender gender;
    private int age;

    public PersonDTO(Person person) {
        this.name = person.getName();
        this.lastName = person.getLastName();
        this.gender = person.getGender();
        this.age = Period.between(person.getBirthDate(), LocalDate.now()).getYears();
    }
}
