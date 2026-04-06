package co.edu.uptc.util;

public class Format {
    public static String formatToAllUpperCase(String description) {
        return description.toUpperCase();
    }

    public static String formatToCapitalize(String description) {
        StringBuilder result = new StringBuilder();
        for (String word : description.toLowerCase().split(" ")) {
            result.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }
        return result.toString().trim();

    }

    public static String formarToBoldType(String text) {
        return "<html><b>" + text + "</b></html>";
    }
}
