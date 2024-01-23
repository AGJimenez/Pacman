import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Personaje {
    private int x;
    private int y;
    private int diametro;
    private Color color;
    private Rectangle[] limitesPaneles;

    public Personaje(int x, int y, int diametro, Color color, Rectangle[] limitesPaneles) {
        this.x = x;
        this.y = y;
        this.diametro = diametro;
        this.color = color;
        this.limitesPaneles = limitesPaneles;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDiametro() {
        return diametro;
    }

    public void dibujar(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, diametro, diametro);
    }

    public void mover(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
