package co.edu.uptc.model.utils;

import co.edu.uptc.config.AppConfig;
import co.edu.uptc.config.Language;
import co.edu.uptc.interfaces.InterfaceValidator;
import co.edu.uptc.pojo.Product;
import co.edu.uptc.util.Format;

import java.util.ArrayList;
import java.util.List;

public class ValidateProduct implements InterfaceValidator<Product> {
    ArrayList<String> messages = new ArrayList<>();
    boolean isValid = true;
    Product product;
    int id = 0;

    @Override
    public List<String> validate(Product n) {
        this.product = (Product) n;
        validateProduct(product);
        return messages;
    }

    @Override
    public Boolean isValid() {
        return isValid;
    }

    private void validateDescription(String description) {
        String option = AppConfig.getProperty("description.format");
        if (option.equalsIgnoreCase("uppercase")) {
            validateUpperCase(description);
        } else {
            validateCapitalized(description);
        }
    }

    private void validateUpperCase(String description) {
        if (!description.equals(description.toUpperCase())) {
            messages.add(Language.get("format.corrected.to.all.capital.letters"));
            product.setDescription(Format.formatToAllUpperCase(description));
        }
    }

    private void validateCapitalized(String description) {
        if (!isCapitalized(description)) {
            messages.add(Language.get("format.corrected.to.capitalized"));
            product.setDescription(Format.formatToCapitalize(description));
        }
    }

    private boolean isCapitalized(String description) {
        for (String word : description.split(" ")) {
            if (!word.isEmpty() && !Character.isUpperCase(word.charAt(0))) {
                return false;
            }
        }
        return true;
    }

    public void validateProduct(Product product) {
        emptyList();
        isValid = true;
        validatePrice(product.getPrice());
        if (isValid) {
            validateDescription(product.getDescription());
            product.setId(id++);
            messages.add(Language.get("msg.success.process.create.product"));
        } else messages.addFirst(Language.get("msg.process.failed"));

    }

    private void emptyList() {
        messages.clear();
    }

    private void validatePrice(double price) {
        double max = AppConfig.getPropertyDouble("maxPrice");
        if (price > max) {
            isValid = false;
            messages.add(Language.get("msg.error.validate.price") + max);
        }
    }

}
