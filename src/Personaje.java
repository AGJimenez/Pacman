import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Personaje implements Runnable {

    private int x;
    private int y;
    private int diametro;
    private Color color;
    private boolean esPacman; // Indica si el personaje es Pacman

    private Tablero tablero;

    public Personaje(int x, int y, int diametro, Color color, boolean esPacman, Tablero tablero) {
        this.x = x;
        this.y = y;
        this.diametro = diametro;
        this.color = color;
        this.esPacman = esPacman;
        this.tablero = tablero;
    }

    public void dibujar(Graphics g) {
        g.setColor(color);
        if (esPacman) {
            g.fillArc(x, y, diametro, diametro, 45, 270);
        } else {
            g.fillOval(x, y, diametro, diametro);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, diametro, diametro);
    }

    @Override
    public void run() {
        try {
            while (true) {
                mover();
                Thread.sleep(1000); // Sleep de 1 segundo
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void mover() {
        if (esPacman) {
            tablero.moverPacman(this);
        } else {
            tablero.moverFantasma(this);
        }
    }

    // Métodos adicionales para obtener y establecer la posición del personaje
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
