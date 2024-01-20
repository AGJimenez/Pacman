import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Personaje implements Runnable {

    private int x;
    private int y;
    private int diametro;
    private Color color;
    private Rectangle[] limitesPaneles;
    private boolean enMovimiento;
    private int direccionX;
    
    public Personaje(int x, int y, int diametro, Color color, Rectangle[] limitesPaneles) {
        this.x = x;
        this.y = y;
        this.diametro = diametro;
        this.color = color;
        this.limitesPaneles = limitesPaneles;
        this.enMovimiento = true;
        this.direccionX = 1;
        while (colisionConLimites()) {
            this.x = (int) (Math.random() * (498 - diametro));
            this.y = (int) (Math.random() * (498 - diametro));
        }
        Thread hiloMovimiento = new Thread();
        hiloMovimiento.start();
    }
    
    public void setDireccionX(int direccionX) {
        this.direccionX = direccionX;
    }

    public void detenerMovimiento() {
        enMovimiento = false;
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

    public void mover(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
    }

    public void dibujar(Graphics g) {
    	 System.out.println("Dibujando personaje en: (" + x + ", " + y + ")");
        g.setColor(color);
        g.fillOval(x, y, diametro, diametro);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, diametro, diametro);
    }

    public boolean colisionConLimites() {
        for (Rectangle limitePanel : limitesPaneles) {
            if (getBounds().intersects(limitePanel)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void run() {
        while (enMovimiento) {
            mover(direccionX, 0);
            // Agrega un pequeño retraso para controlar la velocidad del movimiento
            try {
                Thread.sleep(50); // Ajusta el valor según la velocidad deseada
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    
}

