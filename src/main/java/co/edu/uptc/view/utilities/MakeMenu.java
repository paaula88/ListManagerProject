package co.edu.uptc.view.utilities;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.function.Consumer;

public class MakeMenu {

    private static final Color PRIMARY     = new Color(0x3C3489);
    private static final Color BTN_BG      = new Color(0xF5F4FD);
    private static final Color BTN_HOVER   = new Color(0xE8E6FA);
    private static final Color BTN_BORDER  = new Color(0xCECBF6);
    private static final Color BTN_FG      = new Color(0x3C3489);
    private static final Color BACK_BG     = new Color(0xFCEBEB);
    private static final Color BACK_HOVER  = new Color(0xF7C1C1);
    private static final Color BACK_BORDER = new Color(0xF09595);
    private static final Color BACK_FG     = new Color(0xA32D2D);

    public static JPanel build(String title, ArrayList<String> options, Consumer<Integer> onSelect) {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);
        root.add(buildHeader(title), BorderLayout.NORTH);
        root.add(buildOptions(options, onSelect), BorderLayout.CENTER);
        return root;
    }

    private static JPanel buildHeader(String title) {
        JPanel header = new JPanel(new GridLayout(2, 1, 0, 4));
        header.setBackground(PRIMARY);
        header.setBorder(new EmptyBorder(24, 20, 20, 20));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Selecciona una opción", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(255, 255, 255, 165));

        header.add(titleLabel);
        header.add(subtitle);
        return header;
    }

    private static JPanel buildOptions(ArrayList<String> options, Consumer<Integer> onSelect) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 28, 24, 28));

        int lastIndex = options.size() - 1;

        for (int i = 0; i < options.size(); i++) {
            boolean isLast = (i == lastIndex);
            int index = i;

            if (isLast) {
                panel.add(Box.createVerticalStrut(6));
                panel.add(buildSeparator());
                panel.add(Box.createVerticalStrut(10));
            }

            panel.add(buildButton(options.get(i), isLast, () -> onSelect.accept(index)));

            if (!isLast) {
                panel.add(Box.createVerticalStrut(10));
            }
        }

        return panel;
    }

    private static JButton buildButton(String text, boolean isBack, Runnable action) {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setText((isBack ? "  " : "• ") + text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(true);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Color bg     = isBack ? BACK_BG     : BTN_BG;
        Color hover  = isBack ? BACK_HOVER  : BTN_HOVER;
        Color border = isBack ? BACK_BORDER : BTN_BORDER;
        Color fg     = isBack ? BACK_FG     : BTN_FG;

        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(border, 1, true),
                new EmptyBorder(0, 14, 0, 14)
        ));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(hover); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(bg); }
        });

        btn.addActionListener(e -> action.run());
        return btn;
    }

    private static JSeparator buildSeparator() {
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0xE8E6FA));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return sep;
    }
}