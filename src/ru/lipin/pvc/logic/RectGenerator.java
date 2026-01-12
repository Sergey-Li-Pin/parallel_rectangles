package ru.lipin.pvc.logic;

import ru.lipin.pvc.model.RectModel;
import ru.lipin.pvc.util.Constants;


import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Генератор случайных параметров прямоугольника.
 */
public class RectGenerator {

    public void update(RectModel model, int panelW, int panelH) {
        if (model == null) return;

        // Защита от "нулевых" размеров
        if (panelW <= 0) panelW = Constants.MAX_W;
        if (panelH <= 0) panelH = Constants.MAX_H;

        int w = randBetween(Constants.MIN_W, Math.min(Constants.MAX_W, panelW));
        int h = randBetween(Constants.MIN_H, Math.min(Constants.MAX_H, panelH));

        // Если панель меньше минимальных размеров — поджимаем фигуру
        w = Math.min(w, panelW);
        h = Math.min(h, panelH);

        int xMax = Math.max(0, panelW - w);
        int yMax = Math.max(0, panelH - h);

        int x = randBetween(0, xMax);
        int y = randBetween(0, yMax);

        Color color = new Color(
                ThreadLocalRandom.current().nextInt(256),
                ThreadLocalRandom.current().nextInt(256),
                ThreadLocalRandom.current().nextInt(256)
        );

        model.setW(w);
        model.setH(h);
        model.setX(x);
        model.setY(y);
        model.setColor(color);
    }

    private int randBetween(int minIncl, int maxIncl) {
        if (maxIncl <= minIncl) return minIncl;
        return ThreadLocalRandom.current().nextInt(minIncl, maxIncl + 1);
    }
}
