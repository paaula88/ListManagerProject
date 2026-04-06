package co.edu.uptc.view;
import co.edu.uptc.config.Language;
import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.view.utilities.ViewUtil;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.LinkedHashMap;
import java.util.Map;

public class Menu extends JFrame {

    private static final Color PRIMARY    = new Color(0x3C3489);
    private static final Color BTN_BG     = new Color(0xF5F4FD);
    private static final Color BTN_HOVER  = new Color(0xE8E6FA);
    private static final Color BTN_BORDER = new Color(0xCECBF6);
    private static final Color EXIT_BG    = new Color(0xFCEBEB);
    private static final Color EXIT_FG    = new Color(0xA32D2D);
    private static final Color EXIT_BORD  = new Color(0xF09595);

    private Map<String, ViewInterface<?>> views = new LinkedHashMap<>();

    public Menu() {
        setTitle(Language.get("menu.main.title"));
        setSize(420, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.WHITE);
    }

    public void addView(ViewInterface<?> view) {
        views.put(view.title(), view);
    }

    public void start() {
        add(buildHeader(), BorderLayout.NORTH);
        add(buildButtonPanel(), BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY);
        header.setBorder(new EmptyBorder(24, 20, 20, 20));

        JLabel title = new JLabel(Language.get("menu.main.title"), SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(Language.get("msg.select.an.option"), SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(255, 255, 255, 165));

        JPanel text = new JPanel(new GridLayout(2, 1, 0, 4));
        text.setOpaque(false);
        text.add(title);
        text.add(subtitle);
        header.add(text, BorderLayout.CENTER);
        return header;
    }

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 28, 24, 28));

        views.forEach((title, view) -> {
            panel.add(buildNavButton(title, () -> openView(view), false));
            panel.add(Box.createVerticalStrut(10));
        });

        panel.add(Box.createVerticalStrut(6));
        panel.add(buildSeparator());
        panel.add(Box.createVerticalStrut(10));
        panel.add(buildNavButton(Language.get("msg.exit"), this::exit, true));

        return panel;
    }

    private JButton buildNavButton(String text, Runnable action, boolean isExit) {
        JButton btn = new JButton(text) {
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

        btn.setText((isExit ? "  " : "• ") + text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(true);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Color bg     = isExit ? EXIT_BG   : BTN_BG;
        Color hover  = isExit ? new Color(0xF7C1C1) : BTN_HOVER;
        Color border = isExit ? EXIT_BORD  : BTN_BORDER;
        Color fg     = isExit ? EXIT_FG    : new Color(0x3C3489);

        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(border, 1, true),
                new EmptyBorder(0, 14, 0, 14)
        ));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e)  { btn.setBackground(hover); }
            public void mouseExited(MouseEvent e)   { btn.setBackground(bg); }
        });

        btn.addActionListener(e -> action.run());
        return btn;
    }

    private JSeparator buildSeparator() {
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0xE8E6FA));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return sep;
    }

    private void openView(ViewInterface<?> view) { view.start(this); }

    private void exit() {
        ViewUtil.showMessage(Language.get("msg.final"));
        dispose();
        System.exit(0);
    }
}