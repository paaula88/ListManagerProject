package co.edu.uptc.model.utils;

import co.edu.uptc.config.AppConfig;
import co.edu.uptc.config.Language;
import co.edu.uptc.dto.PersonDTO;
import co.edu.uptc.interfaces.InterfaceValidator;
import co.edu.uptc.pojo.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ValidatePerson implements InterfaceValidator<Person> {
    List<String> messages = new ArrayList<>();
    boolean isValide = true;
    int id = 0;
    PersonDTO dto;

    @Override
    public List<String> validate(Person person) {
        emptyList();
        isValide = true;
        dto = new PersonDTO(person);
        validateName(person.getName());
        validateLastName(person.getLastName());
        validateDate(person.getBirthDate(), dto.getAge());
        addResultMessage(person);
        return messages;
    }

    private void addResultMessage(Person person) {
        if (isValide) {
            person.setId(id++);
            messages.add(Language.get("msg.success.process.create.person"));
        } else {
            messages.addFirst(Language.get("msg.process.failed"));
        }
    }

    @Override
    public Boolean isValid() {
        return isValide;
    }

    private void validateName(String name) {
        int max = AppConfig.getPropertyInt("maxNameLength");
        int min = AppConfig.getPropertyInt("minNameLength");
        if (name.length() > max) {
            isValide = false;
            messages.add(Language.get("msg.error.validating.name.max") + max);
        } else if (name.length() < min) {
            isValide = false;
            messages.add(Language.get("msg.error.validating.name.min") + min);
        }
    }

    private void validateLastName(String lastName) {
        int max = AppConfig.getPropertyInt("maxLastNameLength");
        int min = AppConfig.getPropertyInt("minLastNameLength");
        if (lastName.length() > max) {
            isValide = false;
            messages.add(Language.get("msg.error.validating.lastname.max") + max);
        } else if (lastName.length() < min) {
            isValide = false;
            messages.add(Language.get("msg.error.validating.lastname.min") + min);

        }
    }

    private void validateDate(LocalDate date, int age) {
        if (date.isAfter(LocalDate.now()) || age > 120) {
            isValide = false;
            messages.add(Language.get("msg.errorvalidatebirthday"));
        }
    }

    private void emptyList() {
        messages.clear();
    }

}
