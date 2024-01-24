import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Personaje {
    private int x;
    private int y;
    private boolean esJugador;
    private ImageIcon icono;
    private String rutaImagen;
    private Image imagen;
    private int diametro;
    private int direccion;
    private Color color;
    private Rectangle[] limitesPaneles;

    public Personaje(int x, int y, int diametro, String rutaImagen, Rectangle[] limitesPaneles, boolean esJugador) {
        this.x = x;
        this.y = y;
        this.diametro = diametro;
        this.rutaImagen = rutaImagen;
        this.limitesPaneles = limitesPaneles;
        this.esJugador = esJugador;  // Inicializa el nuevo atributo
        cargarImagen();  // Método para cargar la imagen desde la ruta
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
        Graphics2D g2d = (Graphics2D) g;

        // Ajusta la posición y la dirección de la imagen según el valor de 'direccion'
        if (esJugador) {
            if (direccion == 1) {
                g2d.drawImage(imagen, x, y, diametro, diametro, null);
            } else if (direccion == -1) {
                // Si la dirección es -1, invierte la imagen horizontalmente
                g2d.drawImage(imagen, x + diametro, y, -diametro, diametro, null);
            } else if (direccion == 2) {
                // Rotar 90 grados en dirección a las agujas del reloj
                g2d.rotate(Math.toRadians(90), x + diametro / 2, y + diametro / 2);
                g2d.drawImage(imagen, x, y, diametro, diametro, null);
                g2d.rotate(-Math.toRadians(90), x + diametro / 2, y + diametro / 2);
            } else if (direccion == -2) {
                // Rotar 90 grados en dirección opuesta a las agujas del reloj
                g2d.rotate(-Math.toRadians(90), x + diametro / 2, y + diametro / 2);
                g2d.drawImage(imagen, x, y, diametro, diametro, null);
                g2d.rotate(Math.toRadians(90), x + diametro / 2, y + diametro / 2);
            }
        } else {
            // Si no es el jugador, simplemente dibuja la imagen sin rotación
            g2d.drawImage(imagen, x, y, diametro, diametro, null);
        }
    }

    public void mover(int deltaX, int deltaY) {
        // Actualiza la dirección basándote en el movimiento horizontal y vertical
        if (deltaX > 0) {
            direccion = 1; // Movimiento hacia la derecha
        } else if (deltaX < 0) {
            direccion = -1; // Movimiento hacia la izquierda
        } else if (deltaY > 0) {
            // Si el movimiento es hacia abajo, establece la dirección en 2
            direccion = 2;
        } else if (deltaY < 0) {
            // Si el movimiento es hacia arriba, establece la dirección en -2
            direccion = -2;
        }

        x += deltaX;
        y += deltaY;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    private void cargarImagen() {
        ImageIcon ii = new ImageIcon(getClass().getResource(rutaImagen));
        imagen = ii.getImage();
    }
}

