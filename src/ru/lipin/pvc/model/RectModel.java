package ru.lipin.pvc.model;

import java.awt.Color;

/**
 * Модель прямоугольника.
 */
public class RectModel {

    private int x;
    private int y;
    private int w;
    private int h;
    private Color color = Color.BLACK;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = (color != null) ? color : Color.BLACK;
    }
}
