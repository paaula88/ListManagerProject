package co.edu.uptc.persistence.csv;

import co.edu.uptc.config.AppConfig;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileCSV {

    public static <T> boolean write(List<T> list, String fileName) {
        try {
            File directory = new File(AppConfig.getProperty("output.path"));
            if (!directory.exists()) directory.mkdirs();
            try (Writer writer = new FileWriter(getFilePath(fileName))) {
                StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer).build();
                beanToCsv.write(list);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static <T> List<T> read(Class<T> clazz, String fileName) {
        File file = new File(getFilePath(fileName));
        if (!file.exists()) return new ArrayList<>();
        try (Reader reader = new FileReader(file)) {
            return new CsvToBeanBuilder<T>(reader)
                    .withType(clazz)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static <T> boolean append(T item, Class<T> clazz, String fileName) {
        List<T> list = read(clazz, fileName);
        list.add(item);
        return write(list, fileName);
    }

    private static String getFilePath(String fileName) {
        return AppConfig.getProperty("output.path") + fileName;
    }
}