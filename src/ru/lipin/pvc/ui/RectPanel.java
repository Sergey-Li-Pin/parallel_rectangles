package ru.lipin.pvc.ui;

import ru.lipin.pvc.model.RectModel;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Панель отрисовки прямоугольника.
 * Режим задаётся флагом filled:
 *  - filled=true  -> закрашенный прямоугольник
 *  - filled=false -> контурный прямоугольник
 */
public class RectPanel extends JPanel {

    private final RectModel model;
    private final boolean filled;

    public RectPanel(RectModel model, boolean filled) {
        this.model = model;
        this.filled = filled;
        setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Берём текущее состояние модели
        int x = model.getX();
        int y = model.getY();
        int w = model.getW();
        int h = model.getH();

        g2.setColor(model.getColor());

        if (filled) {
            g2.fillRect(x, y, w, h);
        } else {
            g2.drawRect(x, y, w, h);
        }
    }
}
