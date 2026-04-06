package co.edu.uptc.view.utilities;

import co.edu.uptc.config.Language;

import javax.swing.*;
import java.util.List;

public class ViewUtil {
    public static void showMessages(List<String> messages) {
        String text = String.join("\n", messages);
        JOptionPane.showMessageDialog(null, text, Language.get("msg.warning"), JOptionPane.INFORMATION_MESSAGE);

    }

    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, Language.get("msg.warning"), JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, Language.get("msg.error"), JOptionPane.ERROR_MESSAGE);
    }

    public static void showDeletedObject(String message, String typeOfObject) {
        JOptionPane.showMessageDialog(null, message, typeOfObject, JOptionPane.INFORMATION_MESSAGE);

    }

    public static boolean verificatedIfIsEmpty(String text) {
        return text == null || text.isEmpty();
    }
}
