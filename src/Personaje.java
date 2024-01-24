import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Personaje {
    private int x;
    private int y;
    private ImageIcon icono;
    private String rutaImagen;
    private Image imagen;
    private int diametro;
    private int direccion;
    private Color color;
    private Rectangle[] limitesPaneles;

    public Personaje(int x, int y, int diametro, String rutaImagen, Rectangle[] limitesPaneles) {
        this.x = x;
        this.y = y;
        this.diametro = diametro;
        this.rutaImagen = rutaImagen;
        this.limitesPaneles = limitesPaneles;
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
    	 g.drawImage(imagen, x, y, diametro, diametro, null);
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
    private void cargarImagen() {
        ImageIcon ii = new ImageIcon(getClass().getResource(rutaImagen));
        imagen = ii.getImage();
    }
}
