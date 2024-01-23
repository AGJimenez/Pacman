import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;

public class Tablero extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	 private JPanel tablero;
	  private Personaje jugador;
	   private Rectangle[] limitesPaneles;
	   private int direccionX;
	    private int direccionY;
	/**
	 * Launch the application.
	 */
	    public static void main(String[] args) {
	        EventQueue.invokeLater(() -> {
	            try {
	            	Tablero frame = new Tablero();
	                frame.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        });
	    }
	/**
	 * Create the frame.
	 */
	public Tablero() {
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setBounds(100, 100, 805, 559);
	        contentPane = new JPanel();
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	        setContentPane(contentPane);
	        contentPane.setLayout(null);

		
		 JPanel panel = new JPanel();
	        panel.setBackground(new Color(31, 31, 31));
	        panel.setBounds(0, 0, 884, 520);
	        contentPane.add(panel);
	        panel.setLayout(null);
		
		JPanel marcador = new JPanel();
		marcador.setBackground(new Color(255, 215, 0));
		marcador.setBounds(573, 11, 202, 498);
		panel.add(marcador);
		marcador.setLayout(null);
		
		  tablero = new JPanel() {
	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                jugador.dibujar(g); // Usa el método dibujar de la clase Personaje
	            }
	        };
	        tablero.setBackground(Color.DARK_GRAY);
	        tablero.setBounds(10, 11, 498, 498);
	        panel.add(tablero);
	        tablero.setLayout(null);
	        tablero.setFocusable(true);
	        tablero.addKeyListener(this);

		
		JPanel panel_3_1_1_4 = new JPanel();
		panel_3_1_1_4.setBounds(0, 395, 45, 16);
		tablero.add(panel_3_1_1_4);
		
		JPanel panel_3_1_1_4_1 = new JPanel();
		panel_3_1_1_4_1.setBounds(453, 395, 45, 16);
		tablero.add(panel_3_1_1_4_1);
		
		JPanel panel_6_2_1_1_1 = new JPanel();
		panel_6_2_1_1_1.setBounds(348, 250, 17, 63);
		tablero.add(panel_6_2_1_1_1);
		
		JPanel panel_6_2_1_1_2 = new JPanel();
		panel_6_2_1_1_2.setBounds(241, 300, 17, 63);
		tablero.add(panel_6_2_1_1_2);
		
		JPanel panel_6_2_1_1 = new JPanel();
		panel_6_2_1_1.setBounds(134, 250, 17, 63);
		tablero.add(panel_6_2_1_1);
		
		JPanel panel_6_1 = new JPanel();
		panel_6_1.setBounds(348, 395, 17, 51);
		tablero.add(panel_6_1);
		
		JPanel panel_6_2_1 = new JPanel();
		panel_6_2_1.setBounds(81, 347, 17, 64);
		tablero.add(panel_6_2_1);
		
		JPanel panel_6_2 = new JPanel();
		panel_6_2.setBounds(400, 347, 17, 64);
		tablero.add(panel_6_2);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(134, 395, 17, 51);
		tablero.add(panel_6);
		
		JPanel panel_3_1_1_1_4 = new JPanel();
		panel_3_1_1_1_4.setBounds(188, 297, 122, 16);
		tablero.add(panel_3_1_1_1_4);
		
		JPanel panel_3_1_1_1_2 = new JPanel();
		panel_3_1_1_1_2.setBounds(44, 347, 54, 16);
		tablero.add(panel_3_1_1_1_2);
		
		JPanel panel_3_1_1_3 = new JPanel();
		panel_3_1_1_3.setBounds(145, 154, 54, 16);
		tablero.add(panel_3_1_1_3);
		
		JPanel panel_3_1_1_1_1 = new JPanel();
		panel_3_1_1_1_1.setBounds(294, 154, 54, 16);
		tablero.add(panel_3_1_1_1_1);
		
		JPanel panel_3_1_1_1_3 = new JPanel();
		panel_3_1_1_1_3.setBounds(400, 347, 54, 16);
		tablero.add(panel_3_1_1_1_3);
		
		JPanel panel_5_1_2_1 = new JPanel();
		panel_5_1_2_1.setBounds(348, 105, 17, 114);
		tablero.add(panel_5_1_2_1);
		
		JPanel panel_5_1_2 = new JPanel();
		panel_5_1_2.setBounds(134, 105, 17, 114);
		tablero.add(panel_5_1_2);
		
		JPanel panel_5_1_1 = new JPanel();
		panel_5_1_1.setBounds(241, 105, 17, 65);
		tablero.add(panel_5_1_1);
		
		JPanel panel_5_1 = new JPanel();
		panel_5_1.setBounds(241, 395, 17, 65);
		tablero.add(panel_5_1);
		
		JPanel panel_3_1_1_2_2_2 = new JPanel();
		panel_3_1_1_2_2_2.setBounds(187, 395, 123, 16);
		tablero.add(panel_3_1_1_2_2_2);
		
		JPanel panel_3_1_1_2_2_1_1 = new JPanel();
		panel_3_1_1_2_2_1_1.setBounds(294, 444, 160, 16);
		tablero.add(panel_3_1_1_2_2_1_1);
		
		JPanel panel_3_1_1_2_2_1 = new JPanel();
		panel_3_1_1_2_2_1.setBounds(44, 444, 160, 16);
		tablero.add(panel_3_1_1_2_2_1);
		
		JPanel panel_3_1_1_2_2 = new JPanel();
		panel_3_1_1_2_2.setBounds(187, 105, 123, 16);
		tablero.add(panel_3_1_1_2_2);
		
		JPanel panel_3_1_1_2_1 = new JPanel();
		panel_3_1_1_2_1.setBounds(294, 347, 71, 16);
		tablero.add(panel_3_1_1_2_1);
		
		JPanel panel_3_1_1_2 = new JPanel();
		panel_3_1_1_2.setBounds(133, 347, 71, 16);
		tablero.add(panel_3_1_1_2);
		
		JPanel panel_3_1_1_1 = new JPanel();
		panel_3_1_1_1.setBounds(400, 105, 54, 16);
		tablero.add(panel_3_1_1_1);
		
		JPanel panel_3_1_1 = new JPanel();
		panel_3_1_1.setBounds(44, 105, 54, 16);
		tablero.add(panel_3_1_1);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(241, 0, 17, 73);
		tablero.add(panel_5);
		
		JPanel panel_4_1_1_1 = new JPanel();
		panel_4_1_1_1.setBounds(133, 40, 71, 33);
		tablero.add(panel_4_1_1_1);
		
		JPanel panel_4_1_1 = new JPanel();
		panel_4_1_1.setBounds(294, 40, 71, 33);
		tablero.add(panel_4_1_1);
		
		JPanel panel_4_1 = new JPanel();
		panel_4_1.setBounds(400, 40, 54, 33);
		tablero.add(panel_4_1);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(44, 40, 54, 33);
		tablero.add(panel_4);
		
		JPanel panel_3_4_1 = new JPanel();
		panel_3_4_1.setBounds(0, 209, 10, 51);
		tablero.add(panel_3_4_1);
		
		JPanel panel_3_4 = new JPanel();
		panel_3_4.setBounds(488, 209, 10, 51);
		tablero.add(panel_3_4);
		
		JPanel panel_3_3 = new JPanel();
		panel_3_3.setBounds(400, 250, 10, 66);
		tablero.add(panel_3_3);
		
		JPanel panel_3_2 = new JPanel();
		panel_3_2.setBounds(88, 250, 10, 66);
		tablero.add(panel_3_2);
		
		JPanel panel_3_1 = new JPanel();
		panel_3_1.setBounds(88, 152, 10, 67);
		tablero.add(panel_3_1);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(400, 152, 10, 67);
		tablero.add(panel_3);
		
		JPanel parte_super_1_1_1_3 = new JPanel();
		parte_super_1_1_1_3.setBounds(0, 306, 98, 10);
		tablero.add(parte_super_1_1_1_3);
		
		JPanel parte_super_1_1_1_2 = new JPanel();
		parte_super_1_1_1_2.setBounds(400, 306, 98, 10);
		tablero.add(parte_super_1_1_1_2);
		
		JPanel parte_super_1_1_1 = new JPanel();
		parte_super_1_1_1.setBounds(0, 250, 98, 10);
		tablero.add(parte_super_1_1_1);
		
		JPanel parte_super_1_1_1_1 = new JPanel();
		parte_super_1_1_1_1.setBounds(400, 250, 98, 10);
		tablero.add(parte_super_1_1_1_1);
		
		JPanel parte_super_1_1 = new JPanel();
		parte_super_1_1.setBounds(0, 209, 98, 10);
		tablero.add(parte_super_1_1);
		
		JPanel parte_super_1_2 = new JPanel();
		parte_super_1_2.setBounds(0, 152, 98, 10);
		tablero.add(parte_super_1_2);
		
		JPanel parte_super_1 = new JPanel();
		parte_super_1.setBounds(400, 209, 98, 10);
		tablero.add(parte_super_1);
		
		JPanel parte_super = new JPanel();
		parte_super.setBounds(400, 152, 98, 10);
		tablero.add(parte_super);
		
		JPanel parte_inferior_der_1 = new JPanel();
		parte_inferior_der_1.setBounds(488, 306, 10, 192);
		tablero.add(parte_inferior_der_1);
		
		JPanel parte_inferior_izq = new JPanel();
		parte_inferior_izq.setBounds(0, 306, 10, 192);
		tablero.add(parte_inferior_izq);
		
		JPanel parte_inferior = new JPanel();
		parte_inferior.setBounds(0, 488, 498, 10);
		tablero.add(parte_inferior);
		
		JPanel parte_superior_izquierda = new JPanel();
		parte_superior_izquierda.setBounds(0, 0, 10, 161);
		tablero.add(parte_superior_izquierda);
		
		JPanel parte_superior_derecha = new JPanel();
		parte_superior_derecha.setBounds(488, 0, 10, 161);
		tablero.add(parte_superior_derecha);
		
		JPanel parte_superior_exterior = new JPanel();
		parte_superior_exterior.setBounds(0, 0, 498, 10);
		tablero.add(parte_superior_exterior);
		
	//	 inicializarLimitesPaneles();
        // Agrega el KeyListener al JFrame para recibir eventos de teclado.
		 
		 // Obtiene todos los componentes (paneles) dentro de tablero
	        limitesPaneles = new Rectangle[tablero.getComponentCount()];
	        for (int i = 0; i < tablero.getComponentCount(); i++) {
	            limitesPaneles[i] = tablero.getComponent(i).getBounds();
	        }
	        
	        jugador = new Personaje(50, 50, 20, Color.YELLOW, limitesPaneles);
	        
	        direccionX = 5;  // Dirección inicial hacia la derecha
	        direccionY = 0;
	        
	        Thread hiloMovimiento = new Thread(() -> {
	            while (true) {
	                moverJugador(direccionX, direccionY);
	                tablero.repaint();

	                try {
	                    Thread.sleep(100);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	        hiloMovimiento.start();

	        addKeyListener(this);
	        tablero.setFocusable(true);
	        tablero.requestFocusInWindow();
	    
	}
	
	
    // Inicializa los límites de los paneles dentro del tablero
    private void inicializarLimitesPaneles() {
        // Ajusta estos valores según las posiciones y tamaños reales de tus paneles
        limitesPaneles = new Rectangle[] {
                new Rectangle(0, 250, 98, 10),
                new Rectangle(400, 250, 98, 10),
                // Agrega más rectángulos según sea necesario
        };
    }

	
    // Método para verificar si la nueva posición del jugador está dentro de los límites del tablero y sin colisiones con los paneles
    private boolean esPosicionValida(int x, int y) {
        if (x < 0 || y < 0 || x + jugador.getDiametro() > 498 || y + jugador.getDiametro() > 498) {
            return false;
        }

        // Verifica colisiones con los límites de los paneles
        Rectangle jugadorRect = new Rectangle(x, y, jugador.getDiametro(), jugador.getDiametro());
        for (Rectangle limitePanel : limitesPaneles) {
            if (jugadorRect.intersects(limitePanel)) {
                return false; // Hay colisión con un panel, la posición no es válida
            }
        }

        return true; // La posición es válida
    }


    // Método para mover el jugador y verificar las colisiones.
    private void moverJugador(int deltaX, int deltaY) {
        int nuevaPosX = jugador.getX() + deltaX;
        int nuevaPosY = jugador.getY() + deltaY;

        if (esPosicionValida(nuevaPosX, nuevaPosY)) {
        	 System.out.println("Posición del jugador después de mover: (" + jugador.getX() + ", " + jugador.getY() + ")");
            jugador.mover(deltaX, deltaY);
            tablero.repaint();  // Asegúrate de llamar a repaint después de mover al jugador
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP:
                direccionX = 0;
                direccionY = -5;
                break;
            case KeyEvent.VK_DOWN:
                direccionX = 0;
                direccionY = 5;
                break;
            case KeyEvent.VK_LEFT:
                direccionX = -5;
                direccionY = 0;
                break;
            case KeyEvent.VK_RIGHT:
                direccionX = 5;
                direccionY = 0;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}