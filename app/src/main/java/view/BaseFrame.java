package view;

import javax.swing.*;

public class BaseFrame extends JFrame {
    private JPanel layout;

    public BaseFrame(String title, JPanel layout) {
        super(title);
        this.layout = layout;
        final int HEIGHT = 500;
        final int WIDTH = 500;
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if(layout != null) this.add(layout);
        this.setVisible(true);
    }

    public BaseFrame(String title) {
        this(title, null);
    }

    public BaseFrame() {
        this(null, null);
    }

    public void setView(JPanel layout) {
        this.add(layout);
        this.layout = layout;
        this.validate();
    }
}
