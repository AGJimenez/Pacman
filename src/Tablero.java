import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JProgressBar;


/* 
 * 18/01/24: Vaya fumadón de práctica Daniel
 * 19/01/24: Me quiero pegar un tiro
 * 20/01/24: Actualización: he conseguido que el pacman se mueva
 * 21/01/24: no sé ni lo que estoy haciendo
 * 22/01/24: 4o dia, esto parece algo
 * 23/01/24: Actualización: soy programador
 * 
 */
public class Tablero extends JFrame implements KeyListener {

	//Variables globales
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel tablero;
	private Personaje jugador; //creo el pacman
	private Personaje fantasmaRojo, fantasmaNaranja, fantasmaAzul, fantasmaRosa; //declaro los fantasmicos
	private Rectangle[] limitesPaneles; //creamos un array de la librería graphics para recorrer los obstáculos en el mapa
	private int direccionX; //direccion hacia los lados. Predeterminada para el pacman hacia la derecha
	private int vidas = 3;
	private JLabel lbl_vida1, lbl_vida2, lbl_vida3;
	Timer temporizador; //Clase Timer que me crea el temporizador
	private int direccionY;
	private Semaphore semaforoColision; //Con esto manejo colisiones entre fantasma y jugador
	private AtomicBoolean colisionDetectada = new AtomicBoolean(false); //Tengo que usar "Atomic", que no lo he visto en mi vida, para que no pete
	private  int tiempo_inicial = 120; // segundos
	private int tiempoRestante;
	private JProgressBar progressBar;
	private JLabel lbl_temporizador;
	private Thread hiloFantasmaRojo;
	private Thread hiloFantasmaNaranja;
	private Thread hiloFantasmaAzul;
	private Thread hiloFantasmaRosa;
	private Thread hiloMovimientoPacman;
	private boolean pausaTemporal = false;
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
		
		lbl_temporizador = new JLabel("New label");
		lbl_temporizador.setBounds(70, 46, 60, 14);
		marcador.add(lbl_temporizador);
		lbl_temporizador.setText(String.valueOf(tiempoRestante));
		
		JLabel lblNewLabel = new JLabel("TIEMPO:");
		lblNewLabel.setBounds(10, 21, 100, 14);
		marcador.add(lblNewLabel);
		
		tiempoRestante = tiempo_inicial;
		
		progressBar = new JProgressBar();
		progressBar.setString("");
		progressBar.setBorderPainted(false);
		progressBar.setBorder(null);
		progressBar.setStringPainted(true);
		progressBar.setBounds(10, 42, 182, 25);
		marcador.add(progressBar);
		
		temporizador();

		progressBar.setMaximum(tiempo_inicial); // Pone el valor máximo de la barra de progreso
		progressBar.setValue(tiempo_inicial);
		progressBar.setForeground(new Color(255, 128, 0)); 
		
		JLabel lbl_vidas = new JLabel("VIDAS:");
		lbl_vidas.setBounds(10, 91, 46, 14);
		marcador.add(lbl_vidas);
		
		JPanel panel_vidas = new JPanel();
		panel_vidas.setBackground(Color.DARK_GRAY);
		panel_vidas.setBounds(10, 116, 182, 48);
		marcador.add(panel_vidas);
		panel_vidas.setLayout(null);
		
		lbl_vida1 = new JLabel("");
		lbl_vida1.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/pacman32.png")));
		lbl_vida1.setBounds(20, 9, 32, 32);
		panel_vidas.add(lbl_vida1);
		
		lbl_vida2 = new JLabel("");
		lbl_vida2.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/pacman32.png")));
		lbl_vida2.setBounds(75, 9, 32, 32);
		panel_vidas.add(lbl_vida2);
		
		lbl_vida3 = new JLabel("");
		lbl_vida3.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/pacman32.png")));
		lbl_vida3.setBounds(129, 9, 32, 32);
		panel_vidas.add(lbl_vida3);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				 temporizador.stop();
			}
		});
		
		  tablero = new JPanel() {
	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                jugador.dibujar(g); // Usa el método dibujar de la clase Personaje
	                fantasmaRojo.dibujar(g);
	                fantasmaNaranja.dibujar(g);
	                fantasmaRosa.dibujar(g);
	                fantasmaAzul.dibujar(g);
	                semaforoColision = new Semaphore(1); 
	            }
	        };
	        tablero.setBackground(Color.DARK_GRAY);
	        tablero.setBounds(10, 11, 498, 498);
	        panel.add(tablero);
	        tablero.setLayout(null);
	        tablero.setFocusable(true);
	        tablero.addKeyListener(this);

		//Muchos paneles para crear el laberinto, esto después se recorrerá
	        
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
		
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(189, 260, 121, 10);
        tablero.add(panel_1);
        
        JPanel panel_2 = new JPanel();
        panel_2.setBounds(300, 219, 10, 51);
        tablero.add(panel_2);
        
        JPanel panel_2_1 = new JPanel();
        panel_2_1.setBounds(188, 219, 10, 51);
        tablero.add(panel_2_1);
        
        JPanel panel_1_1 = new JPanel();
        panel_1_1.setBounds(188, 209, 39, 10);
        tablero.add(panel_1_1);
        
        JPanel panel_1_1_1 = new JPanel();
        panel_1_1_1.setBounds(271, 209, 39, 10);
        tablero.add(panel_1_1_1);
		
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
		
		 
		 // saca todos los componentes (paneles) dentro de tablero
	        limitesPaneles = new Rectangle[tablero.getComponentCount()];
	        for (int i = 0; i < tablero.getComponentCount(); i++) {
	            limitesPaneles[i] = tablero.getComponent(i).getBounds();
	        }
	        //Inicializo los fantasmas
	        fantasmaRosa = new Personaje(208, 232, 25, "/imagenes/pink.png", limitesPaneles, true);
	        fantasmaAzul = new Personaje(238, 232, 25, "/imagenes/blue.png", limitesPaneles, true);
	        fantasmaNaranja = new Personaje(269, 232, 25, "/imagenes/orange.png", limitesPaneles, true);
	        fantasmaRojo = new Personaje(238, 207, 25, "/imagenes/red.png", limitesPaneles, true);
	        
	        
	        
	        //Inicializo el jugador
	        jugador = new Personaje(238, 272, 25, "/imagenes/pacmangif.gif", limitesPaneles, true);
	        
	        direccionX = 5;  // Dirección inicial hacia la derecha
	        direccionY = 0;
	        
	        //Usamos un hilo para mover al jugador y actualizar su posicion en el tablero con el método repaint
	        hiloMovimientoPacman = new Thread(() -> {
	            while (true) {
	                moverJugador(direccionX, direccionY);
	                tablero.repaint();

	                try {
	                    Thread.sleep(100); //Cin esto puedo ajustar la velocidad del pacman
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	        hiloMovimientoPacman.start(); //El hilo comienza a moverse
	        
	        
	       //Inicializo los fantasmas como hiulos, llamando al método mover 
	      hiloFantasmaRojo = new Thread(() -> moverFantasma(fantasmaRojo));
	    hiloFantasmaNaranja = new Thread(() -> moverFantasma(fantasmaNaranja));
	      hiloFantasmaRosa = new Thread(() -> moverFantasma(fantasmaRosa));
	       hiloFantasmaAzul = new Thread(() -> moverFantasma(fantasmaAzul));
	        
	        
	        hiloFantasmaRojo.start();
	        hiloFantasmaNaranja.start();
	        hiloFantasmaRosa.start();
	        hiloFantasmaAzul.start();
	        
	        addKeyListener(this);
	        tablero.setFocusable(true);
	        

	        tablero.requestFocusInWindow();
	    
	}
	
	
	private void temporizador() {
		temporizador = new Timer(1000, e -> {
		    if (!pausaTemporal) {
		        tiempoRestante--;

		        // Actualiza la barra de progreso con el tiempo restante
		        progressBar.setValue(tiempoRestante);

		        // Actualiza la etiqueta con el tiempo restante, uso las utilidadess de swing porque si no, no muestra la etiqueta bien
		        SwingUtilities.invokeLater(() -> lbl_temporizador.setText(String.valueOf(tiempoRestante)));

		        // mria si el tiempo ha llegado a cero
		        if (tiempoRestante <= 0) {
		            
		            ((Timer) e.getSource()).stop();
		            terminarJuego();
		            colisionDetectada.set(false);
		        }
		    }
		});


		// Inicia el temporizador
		temporizador.start();
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
        	// System.out.println("Posición del jugador después de mover: (" + jugador.getX() + ", " + jugador.getY() + ")");
            jugador.mover(deltaX, deltaY);
            tablero.repaint();  // 
            verificarColisiones(); // Después de mover al player, verifico la colision
        }
    }

    
    private void verificarColisiones() {
        Rectangle jugadorRect = new Rectangle(jugador.getX(), jugador.getY(), jugador.getDiametro(), jugador.getDiametro());

        // Verifica colisiones con los fantasmas
        Personaje[] fantasmas = {fantasmaRojo, fantasmaNaranja, fantasmaRosa, fantasmaAzul};
        for (Personaje fantasma : fantasmas) {
            Rectangle fantasmaRect = new Rectangle(fantasma.getX(), fantasma.getY(), fantasma.getDiametro(), fantasma.getDiametro());
            if (jugadorRect.intersects(fantasmaRect)) {
                // Colisión detectada
            	
                mostrarDialogoColision();
                return;  // Salgo del bucle después de la primera colisiópn encontrada
            }
        }
    }
    
    private void restarVida() {
        vidas--;

        // Actualizaaz la representacióin de los iconitos
        switch (vidas) {
            case 2:
                lbl_vida3.setVisible(false);
                break;
            case 1:
                lbl_vida2.setVisible(false);
                break;
            case 0:
                lbl_vida1.setVisible(false);
                terminarJuego();
                break;
        }
    }


    private void mostrarDialogoColision() {
        if (!colisionDetectada.getAndSet(true)) {
        	pausaTemporal = true;
            // Muestra un joption para la colision
            try {
                semaforoColision.acquire(); // cojo el semáforo para evitar colisiones concurrentes (wtf)
                JOptionPane.showMessageDialog(this, "Ñam,ñam, qué rico... Tienes una vida menos");
                restarVida(); //restamos vida
                // Reinicia las posiciones del jugador y los fantasmas
                reiniciarJuego();
                colisionDetectada.set(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaforoColision.release(); // Libera el semáforo para permitir otras colisiones
                pausaTemporal = false;
            }
        }
    }


    private void reiniciarJuego() {
        // Reinicia las posiciones del jugador y los fantasmas
        jugador.setX(238);
        jugador.setY(272);

        fantasmaRojo.setX(208);
        fantasmaRojo.setY(232);

        fantasmaAzul.setX(238);
        fantasmaAzul.setY(232);

        fantasmaNaranja.setX(269);
        fantasmaNaranja.setY(232);

        fantasmaRosa.setX(238);
        fantasmaRosa.setY(207);

        // Repinta el tablero para reflejar los cambios
        tablero.repaint();
    }

    
    private void moverFantasma(Personaje fantasma) {
        Random random = new Random();
        int duracionMovimiento = 500; // Duración total del movimiento en milisegundos
        int cantidadPasos = 20; // Número de pasos para cada movimiento

        while (!Thread.currentThread().isInterrupted()) {
            try {
                int pasoX = (random.nextInt(3) - 1) * 2; // Genera -2, 0, o 2
                int pasoY = (random.nextInt(3) - 1) * 2; // Genera -2, 0, o 2

                for (int i = 0; i < cantidadPasos; i++) {
                    int nuevaPosX = fantasma.getX() + pasoX;
                    int nuevaPosY = fantasma.getY() + pasoY;

                    // Verifica colisiones con otros fantasmas y actualiza la posición
                    Rectangle nuevoRect = new Rectangle(nuevaPosX, nuevaPosY, fantasma.getDiametro(), fantasma.getDiametro());
                    boolean colision = false;

                    for (Personaje otroFantasma : new Personaje[]{fantasmaRojo, fantasmaNaranja, fantasmaRosa, fantasmaAzul}) {
                        if (otroFantasma != fantasma) {
                            Rectangle otroRect = new Rectangle(otroFantasma.getX(), otroFantasma.getY(), otroFantasma.getDiametro(), otroFantasma.getDiametro());
                            if (nuevoRect.intersects(otroRect)) {
                                // Hay colisión con otro fantasma, evita la colisión
                                colision = true;
                                break; // Sal del bucle interno
                            }
                        }
                    }

                    if (!colision && esPosicionValida(nuevaPosX, nuevaPosY)) {
                        fantasma.mover(pasoX, pasoY);
                        tablero.repaint();
                        verificarColisiones();

                        // Espera antes de realizar el siguiente paso
                        Thread.sleep(duracionMovimiento / cantidadPasos);
                    } else {
                        // Cambia la dirección para evitar la colisión
                        pasoX = (random.nextInt(3) - 1) * 2;
                        pasoY = (random.nextInt(3) - 1) * 2;
                    }
                }
            } catch (InterruptedException e) {
                // Maneja la interrupción saliendo del bucle
                break;
            }
        }
    } //MENCION HONORIFICA A CHATTY POR HACERME LOS RANDOM, SÍ, NO SÉ DE MATEMÁTICAS.


    private void terminarJuego() {
      
            temporizador.stop();
        

        //Me cargo los hilos y next
        hiloMovimientoPacman.interrupt();
        hiloFantasmaAzul.interrupt();
        hiloFantasmaNaranja.interrupt();
        hiloFantasmaRojo.interrupt();
        hiloFantasmaRosa.interrupt();
        
   
       //INTENTAR FINALIZAR HILO DE JUGADOR, QUE NO LO CONSIGO

        JOptionPane.showMessageDialog(this, "GAME OVER");
        dispose();
        PantallaFinal finJuego = new PantallaFinal();
        finJuego.setVisible(true);
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