package co.edu.uptc.persistence.csv.utilities;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter extends AbstractBeanField<LocalDateTime, String> {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    protected Object convert(String s) {
        return LocalDateTime.parse(s, FORMATTER);
    }

    @Override
    protected String convertToWrite(Object value) {
        return ((LocalDateTime) value).format(FORMATTER);
    }
}