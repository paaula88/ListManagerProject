package co.edu.uptc.model.utils;

import co.edu.uptc.config.Language;
import co.edu.uptc.interfaces.InterfaceValidator;
import co.edu.uptc.pojo.Accounting;

import java.util.ArrayList;
import java.util.List;

public class ValidateAccounting implements InterfaceValidator <Accounting>{
    List<String>messages = new ArrayList<>();
    boolean isValide = true;
    @Override
    public List<String> validate(Accounting n) {
        messages.add(Language.get("msg.success.process.create.accounting"));
        return messages;
    }

    @Override
    public Boolean isValid() {
        return isValide;
    }
    //Listo para implementar posibles validaciones

}
