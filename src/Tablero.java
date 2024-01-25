import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JProgressBar;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;


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
	private int totalLabels;
	private Personaje jugador; //creo el pacman
	private Personaje fantasmaRojo, fantasmaNaranja, fantasmaAzul, fantasmaRosa; //declaro los fantasmicos
	private Rectangle[] limitesPaneles; //creamos un array de la librería graphics para recorrer los obstáculos en el mapa
	private int direccionX; //direccion hacia los lados. Predeterminada para el pacman hacia la derecha
	private int vidas = 3;
	private int puntos = 0;
	private JLabel lb_puntos;
	private JLabel lbl_vida1, lbl_vida2, lbl_vida3;
	Timer temporizador; //Clase Timer que me crea el temporizador
	private int direccionY;
	private Semaphore semaforoColision; //Con esto manejo colisiones entre fantasma y jugador
	private AtomicBoolean colisionDetectada = new AtomicBoolean(false); //Tengo que usar "Atomic", que no lo he visto en mi vida, para que no pete
	private  int tiempo_inicial = 135; // segundos
	private int tiempoRestante;
	private JProgressBar progressBar;
	private JLabel lbl_temporizador;
	private Thread hiloFantasmaRojo;
	private Thread hiloFantasmaNaranja;
	private Thread hiloFantasmaAzul;
	private Thread hiloFantasmaRosa;
	private Thread hiloMovimientoPacman;
	private boolean pausaTemporal = false;
	private Clip sound;
	private Clip dead;
	private Clip tema;
	private String name;
	private boolean reproduciendo = true;
	/**
	 * Launch the application.
	 */
	    public static void main(String[] args) {
	        EventQueue.invokeLater(() -> {
	            try {
	            	Tablero frame = new Tablero(null);
	                frame.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        });
	    }
	/**
	 * Create the frame.
	 * @param name 
	 */
	public Tablero(String name) {
		setUndecorated(true);
		this.name = name;
		setIconImage(Toolkit.getDefaultToolkit().getImage(Tablero.class.getResource("/imagenes/pacman32.png")));
		setResizable(false);
		setTitle("Pacman");

		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setBounds(100, 100, 883, 590);
	        contentPane = new JPanel();
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	        setContentPane(contentPane);
	        contentPane.setLayout(null);
	        setLocationRelativeTo(this);
		
		 JPanel panel = new JPanel();
	        panel.setBackground(new Color(31, 31, 31));
	        panel.setBounds(0, 0, 884, 591);
	        contentPane.add(panel);
	        panel.setLayout(null);
		
		JPanel marcador = new JPanel();
		marcador.setOpaque(false);
		marcador.setBackground(new Color(255, 215, 0));
		marcador.setBounds(621, 48, 202, 498);
		panel.add(marcador);
		marcador.setLayout(null);
		
		JButton btn_music = new JButton("");
		btn_music.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_music.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(reproduciendo == false) {
					musicaStart();
					reproduciendo = true;
					btn_music.setIcon(new ImageIcon(PantallaPrincipal.class.getResource("/imagenes/botonMusicOn.png")));
				}else {
					musicaStop();
					reproduciendo = false;
					btn_music.setIcon(new ImageIcon(PantallaPrincipal.class.getResource("/imagenes/botonMusicOff.png")));
				}
		        tablero.requestFocusInWindow();
			}
			
			
		});
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				terminarJuego();
				
			}
		});
		btnNewButton.setBounds(55, 444, 90, 40);
		marcador.add(btnNewButton);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setOpaque(false);
		btnNewButton.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/exit.png")));
		btn_music.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/botonMusicOn.png")));
		btn_music.setOpaque(false);
		btn_music.setContentAreaFilled(false);
		btn_music.setBorderPainted(false);
		btn_music.setBorder(null);
		btn_music.setBounds(126, 67, 32, 32);
		marcador.add(btn_music);
		
		lb_puntos = new JLabel("");
		lb_puntos.setFont(new Font("I pixel u", Font.PLAIN, 22));
		lb_puntos.setHorizontalAlignment(SwingConstants.CENTER);
		lb_puntos.setForeground(new Color(255, 255, 255));
		lb_puntos.setBounds(29, 375, 143, 25);
		marcador.add(lb_puntos);
		
		JLabel lblNewLabel_2_1 = new JLabel("");
		lblNewLabel_2_1.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/panelVidas.png")));
		lblNewLabel_2_1.setBounds(10, 364, 182, 48);
		marcador.add(lblNewLabel_2_1);
		
		JLabel lbl_vidas_1 = new JLabel("puntos");
		lbl_vidas_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_vidas_1.setForeground(Color.WHITE);
		lbl_vidas_1.setFont(new Font("PacFont", Font.PLAIN, 22));
		lbl_vidas_1.setBounds(10, 316, 182, 31);
		marcador.add(lbl_vidas_1);
		
		lbl_temporizador = new JLabel("New label");
		lbl_temporizador.setFont(new Font("I pixel u", Font.PLAIN, 20));
		lbl_temporizador.setBounds(51, 162, 109, 31);
		marcador.add(lbl_temporizador);
		lbl_temporizador.setText(String.valueOf(tiempoRestante));
		
		JLabel lblNewLabel = new JLabel("tiempo");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("PacFont", Font.PLAIN, 22));
		lblNewLabel.setBounds(41, 110, 119, 48);
		marcador.add(lblNewLabel);
		
		tiempoRestante = tiempo_inicial;
		
		progressBar = new JProgressBar();
		progressBar.setBackground(new Color(43, 29, 23));
		progressBar.setString("");
		progressBar.setBorderPainted(false);
		progressBar.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		progressBar.setStringPainted(true);
		progressBar.setBounds(10, 165, 182, 25);
		marcador.add(progressBar);
		
		temporizador();

		progressBar.setMaximum(tiempo_inicial); // Pone el valor máximo de la barra de progreso
		progressBar.setValue(tiempo_inicial);
		progressBar.setForeground(new Color(252, 209, 37)); 
		
		JLabel lbl_vidas = new JLabel("vidas");
		lbl_vidas.setForeground(new Color(255, 255, 255));
		lbl_vidas.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_vidas.setFont(new Font("PacFont", Font.PLAIN, 22));
		lbl_vidas.setBounds(10, 209, 182, 31);
		marcador.add(lbl_vidas);
		
		JPanel panel_vidas = new JPanel();
		panel_vidas.setOpaque(false);
		panel_vidas.setBorder(null);
		panel_vidas.setBackground(Color.WHITE);
		panel_vidas.setBounds(10, 251, 182, 48);
		marcador.add(panel_vidas);
		panel_vidas.setLayout(null);
		
		lbl_vida1 = new JLabel("");
		lbl_vida1.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/vidas.png")));
		lbl_vida1.setBounds(20, 9, 32, 32);
		panel_vidas.add(lbl_vida1);
		
		lbl_vida2 = new JLabel("");
		lbl_vida2.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/vidas.png")));
		lbl_vida2.setBounds(75, 9, 32, 32);
		panel_vidas.add(lbl_vida2);
		
		lbl_vida3 = new JLabel("");
		lbl_vida3.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/vidas.png")));
		lbl_vida3.setBounds(129, 9, 32, 32);
		panel_vidas.add(lbl_vida3);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/panelVidas.png")));
		lblNewLabel_2.setBounds(0, 0, 182, 48);
		panel_vidas.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/clock.gif")));
		lblNewLabel_1.setBounds(41, 60, 42, 42);
		marcador.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("\r\n");
		lblNewLabel_3.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/fondopanel.png")));
		lblNewLabel_3.setBounds(0, 0, 202, 498);
		marcador.add(lblNewLabel_3);
		
		
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
	        tablero.setBackground(new Color(39, 39, 39));
	        tablero.setBounds(58, 48, 498, 498);
	        panel.add(tablero);
	        tablero.setLayout(null);
	        tablero.setFocusable(true);
	        tablero.addKeyListener(this);

		//Muchos paneles para crear el laberinto, esto después se recorrerá
	        
		JPanel panel_3_1_1_4 = new JPanel();
		panel_3_1_1_4.setBackground(new Color(0, 128, 255));
		panel_3_1_1_4.setBounds(0, 395, 45, 16);
		tablero.add(panel_3_1_1_4);
		
		JPanel panel_3_1_1_4_1 = new JPanel();
		panel_3_1_1_4_1.setBackground(new Color(0, 128, 255));
		panel_3_1_1_4_1.setBounds(453, 395, 45, 16);
		tablero.add(panel_3_1_1_4_1);
		
		JPanel panel_6_2_1_1_1 = new JPanel();
		panel_6_2_1_1_1.setBackground(new Color(0, 128, 255));
		panel_6_2_1_1_1.setBounds(348, 250, 17, 63);
		tablero.add(panel_6_2_1_1_1);
		
		JPanel panel_6_2_1_1_2 = new JPanel();
		panel_6_2_1_1_2.setBackground(new Color(0, 128, 255));
		panel_6_2_1_1_2.setBounds(241, 300, 17, 63);
		tablero.add(panel_6_2_1_1_2);
		
		JPanel panel_6_2_1_1 = new JPanel();
		panel_6_2_1_1.setBackground(new Color(0, 128, 255));
		panel_6_2_1_1.setBounds(134, 250, 17, 63);
		tablero.add(panel_6_2_1_1);
		
		JPanel panel_6_1 = new JPanel();
		panel_6_1.setBackground(new Color(0, 128, 255));
		panel_6_1.setBounds(348, 395, 17, 51);
		tablero.add(panel_6_1);
		
		JPanel panel_6_2_1 = new JPanel();
		panel_6_2_1.setBackground(new Color(0, 128, 255));
		panel_6_2_1.setBounds(81, 347, 17, 64);
		tablero.add(panel_6_2_1);
		
		JPanel panel_6_2 = new JPanel();
		panel_6_2.setBackground(new Color(0, 128, 255));
		panel_6_2.setBounds(400, 347, 17, 64);
		tablero.add(panel_6_2);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(0, 128, 255));
		panel_6.setBounds(134, 395, 17, 51);
		tablero.add(panel_6);
		
		JPanel panel_3_1_1_1_4 = new JPanel();
		panel_3_1_1_1_4.setBackground(new Color(0, 128, 255));
		panel_3_1_1_1_4.setBounds(188, 297, 122, 16);
		tablero.add(panel_3_1_1_1_4);
		
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(0, 128, 255));
        panel_1.setBounds(189, 260, 121, 10);
        tablero.add(panel_1);
        
        JPanel panel_2 = new JPanel();
        panel_2.setBackground(new Color(0, 128, 255));
        panel_2.setBounds(300, 219, 10, 51);
        tablero.add(panel_2);
        
        JPanel panel_2_1 = new JPanel();
        panel_2_1.setBackground(new Color(0, 128, 255));
        panel_2_1.setBounds(188, 219, 10, 51);
        tablero.add(panel_2_1);
        
        JPanel panel_1_1 = new JPanel();
        panel_1_1.setBackground(new Color(0, 128, 255));
        panel_1_1.setBounds(188, 209, 39, 10);
        tablero.add(panel_1_1);
        
        JPanel panel_1_1_1 = new JPanel();
        panel_1_1_1.setBackground(new Color(0, 128, 255));
        panel_1_1_1.setBounds(271, 209, 39, 10);
        tablero.add(panel_1_1_1);
		
		JPanel panel_3_1_1_1_2 = new JPanel();
		panel_3_1_1_1_2.setBackground(new Color(0, 128, 255));
		panel_3_1_1_1_2.setBounds(44, 347, 54, 16);
		tablero.add(panel_3_1_1_1_2);
		
		JPanel panel_3_1_1_3 = new JPanel();
		panel_3_1_1_3.setBackground(new Color(0, 128, 255));
		panel_3_1_1_3.setBounds(145, 154, 54, 16);
		tablero.add(panel_3_1_1_3);
		
		JPanel panel_3_1_1_1_1 = new JPanel();
		panel_3_1_1_1_1.setBackground(new Color(0, 128, 255));
		panel_3_1_1_1_1.setBounds(294, 154, 54, 16);
		tablero.add(panel_3_1_1_1_1);
		
		JPanel panel_3_1_1_1_3 = new JPanel();
		panel_3_1_1_1_3.setBackground(new Color(0, 128, 255));
		panel_3_1_1_1_3.setBounds(400, 347, 54, 16);
		tablero.add(panel_3_1_1_1_3);
		
		JPanel panel_5_1_2_1 = new JPanel();
		panel_5_1_2_1.setBackground(new Color(0, 128, 255));
		panel_5_1_2_1.setBounds(348, 105, 17, 114);
		tablero.add(panel_5_1_2_1);
		
		JPanel panel_5_1_2 = new JPanel();
		panel_5_1_2.setBackground(new Color(0, 128, 255));
		panel_5_1_2.setBounds(134, 105, 17, 114);
		tablero.add(panel_5_1_2);
		
		JPanel panel_5_1_1 = new JPanel();
		panel_5_1_1.setBackground(new Color(0, 128, 255));
		panel_5_1_1.setBounds(241, 105, 17, 65);
		tablero.add(panel_5_1_1);
		
		JPanel panel_5_1 = new JPanel();
		panel_5_1.setBackground(new Color(0, 128, 255));
		panel_5_1.setBounds(241, 395, 17, 65);
		tablero.add(panel_5_1);
		
		JPanel panel_3_1_1_2_2_2 = new JPanel();
		panel_3_1_1_2_2_2.setBackground(new Color(0, 128, 255));
		panel_3_1_1_2_2_2.setBounds(187, 395, 123, 16);
		tablero.add(panel_3_1_1_2_2_2);
		
		JPanel panel_3_1_1_2_2_1_1 = new JPanel();
		panel_3_1_1_2_2_1_1.setBackground(new Color(0, 128, 255));
		panel_3_1_1_2_2_1_1.setBounds(294, 444, 160, 16);
		tablero.add(panel_3_1_1_2_2_1_1);
		
		JPanel panel_3_1_1_2_2_1 = new JPanel();
		panel_3_1_1_2_2_1.setBackground(new Color(0, 128, 255));
		panel_3_1_1_2_2_1.setBounds(44, 444, 160, 16);
		tablero.add(panel_3_1_1_2_2_1);
		
		JPanel panel_3_1_1_2_2 = new JPanel();
		panel_3_1_1_2_2.setBackground(new Color(0, 128, 255));
		panel_3_1_1_2_2.setBounds(187, 105, 123, 16);
		tablero.add(panel_3_1_1_2_2);
		
		JPanel panel_3_1_1_2_1 = new JPanel();
		panel_3_1_1_2_1.setBackground(new Color(0, 128, 255));
		panel_3_1_1_2_1.setBounds(294, 347, 71, 16);
		tablero.add(panel_3_1_1_2_1);
		
		JPanel panel_3_1_1_2 = new JPanel();
		panel_3_1_1_2.setBackground(new Color(0, 128, 255));
		panel_3_1_1_2.setBounds(133, 347, 71, 16);
		tablero.add(panel_3_1_1_2);
		
		JPanel panel_3_1_1_1 = new JPanel();
		panel_3_1_1_1.setBackground(new Color(0, 128, 255));
		panel_3_1_1_1.setBounds(400, 105, 54, 16);
		tablero.add(panel_3_1_1_1);
		
		JPanel panel_3_1_1 = new JPanel();
		panel_3_1_1.setBackground(new Color(0, 128, 255));
		panel_3_1_1.setBounds(44, 105, 54, 16);
		tablero.add(panel_3_1_1);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(0, 128, 255));
		panel_5.setBounds(241, 0, 17, 73);
		tablero.add(panel_5);
		
		JPanel panel_4_1_1_1 = new JPanel();
		panel_4_1_1_1.setBackground(new Color(0, 128, 255));
		panel_4_1_1_1.setBounds(133, 40, 71, 33);
		tablero.add(panel_4_1_1_1);
		
		JPanel panel_4_1_1 = new JPanel();
		panel_4_1_1.setBackground(new Color(0, 128, 255));
		panel_4_1_1.setBounds(294, 40, 71, 33);
		tablero.add(panel_4_1_1);
		
		JPanel panel_4_1 = new JPanel();
		panel_4_1.setBackground(new Color(0, 128, 255));
		panel_4_1.setBounds(400, 40, 54, 33);
		tablero.add(panel_4_1);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(0, 128, 255));
		panel_4.setBounds(44, 40, 54, 33);
		tablero.add(panel_4);
		
		JPanel panel_3_3 = new JPanel();
		panel_3_3.setBackground(new Color(0, 128, 255));
		panel_3_3.setBounds(400, 250, 10, 66);
		tablero.add(panel_3_3);
		
		JPanel panel_3_2 = new JPanel();
		panel_3_2.setBackground(new Color(0, 128, 255));
		panel_3_2.setBounds(88, 250, 10, 66);
		tablero.add(panel_3_2);
		
		JPanel panel_3_1 = new JPanel();
		panel_3_1.setBackground(new Color(0, 128, 255));
		panel_3_1.setBounds(88, 152, 10, 67);
		tablero.add(panel_3_1);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(0, 128, 255));
		panel_3.setBounds(400, 152, 10, 67);
		tablero.add(panel_3);
		
		JPanel parte_super_1_1_1_3 = new JPanel();
		parte_super_1_1_1_3.setBackground(new Color(0, 128, 255));
		parte_super_1_1_1_3.setBounds(0, 306, 98, 10);
		tablero.add(parte_super_1_1_1_3);
		
		JPanel parte_super_1_1_1_2 = new JPanel();
		parte_super_1_1_1_2.setBackground(new Color(0, 128, 255));
		parte_super_1_1_1_2.setBounds(400, 306, 98, 10);
		tablero.add(parte_super_1_1_1_2);
		
		JPanel parte_super_1_1_1 = new JPanel();
		parte_super_1_1_1.setBackground(new Color(0, 128, 255));
		parte_super_1_1_1.setBounds(0, 250, 98, 10);
		tablero.add(parte_super_1_1_1);
		
		JPanel parte_super_1_1_1_1 = new JPanel();
		parte_super_1_1_1_1.setBackground(new Color(0, 128, 255));
		parte_super_1_1_1_1.setBounds(400, 250, 98, 10);
		tablero.add(parte_super_1_1_1_1);
		
		JPanel parte_super_1_1 = new JPanel();
		parte_super_1_1.setBackground(new Color(0, 128, 255));
		parte_super_1_1.setBounds(0, 209, 98, 10);
		tablero.add(parte_super_1_1);
		
		JPanel parte_super_1_2 = new JPanel();
		parte_super_1_2.setBackground(new Color(0, 128, 255));
		parte_super_1_2.setBounds(0, 152, 98, 10);
		tablero.add(parte_super_1_2);
		
		JPanel parte_super_1 = new JPanel();
		parte_super_1.setBackground(new Color(0, 128, 255));
		parte_super_1.setBounds(400, 209, 98, 10);
		tablero.add(parte_super_1);
		
		JPanel parte_super = new JPanel();
		parte_super.setBackground(new Color(0, 128, 255));
		parte_super.setBounds(400, 152, 98, 10);
		tablero.add(parte_super);
		
		JPanel parte_inferior_der_1 = new JPanel();
		parte_inferior_der_1.setBackground(new Color(0, 128, 255));
		parte_inferior_der_1.setBounds(488, 306, 10, 192);
		tablero.add(parte_inferior_der_1);
		
		JPanel parte_inferior_izq = new JPanel();
		parte_inferior_izq.setBackground(new Color(0, 128, 255));
		parte_inferior_izq.setBounds(0, 306, 10, 192);
		tablero.add(parte_inferior_izq);
		
		JPanel parte_inferior = new JPanel();
		parte_inferior.setBackground(new Color(0, 128, 255));
		parte_inferior.setBounds(0, 488, 498, 10);
		tablero.add(parte_inferior);
		
		JPanel parte_superior_izquierda = new JPanel();
		parte_superior_izquierda.setBackground(new Color(0, 128, 255));
		parte_superior_izquierda.setBounds(0, 0, 10, 161);
		tablero.add(parte_superior_izquierda);
		
		JPanel parte_superior_derecha = new JPanel();
		parte_superior_derecha.setBackground(new Color(0, 128, 255));
		parte_superior_derecha.setBounds(488, 0, 10, 161);
		tablero.add(parte_superior_derecha);
		
		JPanel parte_superior_exterior = new JPanel();
		parte_superior_exterior.setBackground(new Color(0, 128, 255));
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

	    //    tablero.setComponentZOrder(fantasmaRosa, 1);
	        
	        //Inicializo el jugador
	        jugador = new Personaje(238, 272, 25, "/imagenes/pacmangif.gif", limitesPaneles, true);
	        
	        direccionX = 5;  // Dirección inicial hacia la derecha
	        direccionY = 0;
	        
	        //Usamos un hilo para mover al jugador y actualizar su posicion en el tablero con el método repaint
	        hiloMovimientoPacman = new Thread(() -> {
	            while (!Thread.currentThread().isInterrupted()) {
	                moverJugador(direccionX, direccionY);
	                tablero.repaint();

	                try {
	                    Thread.sleep(100); // Con esto puedes ajustar la velocidad del pacman
	                } catch (InterruptedException e) {
	                    // Lanzar una nueva InterruptedException cuando se produce una interrupción
	                    Thread.currentThread().interrupt();
	                    throw new RuntimeException("Hilo interrumpido", e);
	                }
	            }
	        });

	        hiloMovimientoPacman.start(); // El hilo comienza a moverse

	        
	        
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
	        
	        
	        JLabel lblNewLabel_111;
	        lblNewLabel_111 = new JLabel("");
	        lblNewLabel_111.setOpaque(true);
	        lblNewLabel_111.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_111.setBounds(161, 465, 12, 12);
	        tablero.add(lblNewLabel_111);
	        
	        JLabel lblNewLabel_1111;
	        lblNewLabel_1111 = new JLabel("");
	        lblNewLabel_1111.setOpaque(true);
	        lblNewLabel_1111.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_1111.setBounds(326, 471, 12, 12);
	        tablero.add(lblNewLabel_1111);
	        
	        JLabel lblNewLabel_12;
	        lblNewLabel_12 = new JLabel("");
	        lblNewLabel_12.setOpaque(true);
	        lblNewLabel_12.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_12.setBounds(106, 230, 12, 12);
	        tablero.add(lblNewLabel_12);
	        
	        JLabel lblNewLabel_13;
	        lblNewLabel_13 = new JLabel("");
	        lblNewLabel_13.setOpaque(true);
	        lblNewLabel_13.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_13.setBounds(373, 230, 12, 12);
	        tablero.add(lblNewLabel_13);
	        
	        JLabel lblNewLabel_14;
	        lblNewLabel_14 = new JLabel("");
	        lblNewLabel_14.setOpaque(true);
	        lblNewLabel_14.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_14.setBounds(106, 326, 12, 12);
	        tablero.add(lblNewLabel_14);
	        
	        JLabel lblNewLabel_15;
	        lblNewLabel_15 = new JLabel("");
	        lblNewLabel_15.setOpaque(true);
	        lblNewLabel_15.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_15.setBounds(373, 326, 12, 12);
	        tablero.add(lblNewLabel_15);
	        
	        JLabel lblNewLabel_16;
	        lblNewLabel_16 = new JLabel("");
	        lblNewLabel_16.setOpaque(true);
	        lblNewLabel_16.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_16.setBounds(373, 85, 12, 12);
	        tablero.add(lblNewLabel_16);
	        
	        JLabel lblNewLabel_17;
	        lblNewLabel_17 = new JLabel("");
	        lblNewLabel_17.setOpaque(true);
	        lblNewLabel_17.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_17.setBounds(106, 85, 12, 12);
	        tablero.add(lblNewLabel_17);
	        
	        JLabel lblNewLabel_18;
	        lblNewLabel_18 = new JLabel("");
	        lblNewLabel_18.setOpaque(true);
	        lblNewLabel_18.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_18.setBounds(161, 421, 12, 12);
	        tablero.add(lblNewLabel_18);
	        
	        JLabel lblNewLabel_19;
	        lblNewLabel_19 = new JLabel("");
	        lblNewLabel_19.setOpaque(true);
	        lblNewLabel_19.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_19.setBounds(300, 374, 12, 12);
	        tablero.add(lblNewLabel_19);
	        
	        JLabel lblNewLabel_10;
	        lblNewLabel_10 = new JLabel("");
	        lblNewLabel_10.setOpaque(true);
	        lblNewLabel_10.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_10.setBounds(54, 374, 12, 12);
	        tablero.add(lblNewLabel_10);
	        
	        JLabel lblNewLabel_122;
	        lblNewLabel_122 = new JLabel("");
	        lblNewLabel_122.setOpaque(true);
	        lblNewLabel_122.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_122.setBounds(106, 421, 12, 12);
	        tablero.add(lblNewLabel_122);
	        
	        JLabel lblNewLabel_123;
	        lblNewLabel_123 = new JLabel("");
	        lblNewLabel_123.setOpaque(true);
	        lblNewLabel_123.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_123.setBounds(326, 422, 12, 12);
	        tablero.add(lblNewLabel_123);
	        
	        JLabel lblNewLabel_124;
	        lblNewLabel_124 = new JLabel("");
	        lblNewLabel_124.setOpaque(true);
	        lblNewLabel_124.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_124.setBounds(375, 421, 12, 12);
	        tablero.add(lblNewLabel_124);
	        
	        JLabel lblNewLabel_144;
	        lblNewLabel_144 = new JLabel("");
	        lblNewLabel_144.setOpaque(true);
	        lblNewLabel_144.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_144.setBounds(466, 465, 12, 12);
	        tablero.add(lblNewLabel_144);
	        
	        JLabel lblNewLabel_155;
	        lblNewLabel_155 = new JLabel("");
	        lblNewLabel_155.setOpaque(true);
	        lblNewLabel_155.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_155.setBounds(466, 327, 12, 12);
	        tablero.add(lblNewLabel_155);
	        
	        JLabel lblNewLabel_166;
	        lblNewLabel_166 = new JLabel("");
	        lblNewLabel_166.setOpaque(true);
	        lblNewLabel_166.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_166.setBounds(20, 21, 12, 12);
	        tablero.add(lblNewLabel_166);
	        
	        JLabel lblNewLabel_188;
	        lblNewLabel_188 = new JLabel("");
	        lblNewLabel_188.setOpaque(true);
	        lblNewLabel_188.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_188.setBounds(20, 129, 12, 12);
	        tablero.add(lblNewLabel_188);
	        
	        JLabel lblNewLabel_199;
	        lblNewLabel_199 = new JLabel("");
	        lblNewLabel_199.setOpaque(true);
	        lblNewLabel_199.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_199.setBounds(215, 21, 12, 12);
	        tablero.add(lblNewLabel_199);
	        
	        JLabel lblNewLabel_1212;
	        lblNewLabel_1212 = new JLabel("");
	        lblNewLabel_1212.setOpaque(true);
	        lblNewLabel_1212.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_1212.setBounds(271, 21, 12, 12);
	        tablero.add(lblNewLabel_1212);
	        
	        JLabel lblNewLabel_1123;
	        lblNewLabel_1123 = new JLabel("");
	        lblNewLabel_1123.setOpaque(true);
	        lblNewLabel_1123.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_1123.setBounds(466, 21, 12, 12);
	        tablero.add(lblNewLabel_1123);
	        
	        JLabel lblNewLabel_01;
	        lblNewLabel_01 = new JLabel("");
	        lblNewLabel_01.setOpaque(true);
	        lblNewLabel_01.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_01.setBounds(466, 129, 12, 12);
	        tablero.add(lblNewLabel_01);
	        
	        JLabel lblNewLabel_02;
	        lblNewLabel_02 = new JLabel("");
	        lblNewLabel_02.setOpaque(true);
	        lblNewLabel_02.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_02.setBounds(271, 82, 12, 12);
	        tablero.add(lblNewLabel_02);
	        
	        JLabel lblNewLabel_03;
	        lblNewLabel_03 = new JLabel("");
	        lblNewLabel_03.setOpaque(true);
	        lblNewLabel_03.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_03.setBounds(106, 21, 12, 12);
	        tablero.add(lblNewLabel_03);
	        
	        JLabel lblNewLabel_04;
	        lblNewLabel_04 = new JLabel("");
	        lblNewLabel_04.setOpaque(true);
	        lblNewLabel_04.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_04.setBounds(466, 227, 12, 12);
	        tablero.add(lblNewLabel_04);
	        
	        JLabel lblNewLabel_05;
	        lblNewLabel_05 = new JLabel("");
	        lblNewLabel_05.setOpaque(true);
	        lblNewLabel_05.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_05.setBounds(166, 374, 12, 12);
	        tablero.add(lblNewLabel_05);
	        
	        JLabel lblNewLabel_06;
	        lblNewLabel_06 = new JLabel("");
	        lblNewLabel_06.setOpaque(true);
	        lblNewLabel_06.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_06.setBounds(20, 327, 12, 12);
	        tablero.add(lblNewLabel_06);
	        
	        JLabel lblNewLabel_07;
	        lblNewLabel_07 = new JLabel("");
	        lblNewLabel_07.setOpaque(true);
	        lblNewLabel_07.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_07.setBounds(219, 324, 12, 12);
	        tablero.add(lblNewLabel_07);
	        
	        JLabel lblNewLabel_08;
	        lblNewLabel_08 = new JLabel("");
	        lblNewLabel_08.setOpaque(true);
	        lblNewLabel_08.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_08.setBounds(271, 324, 12, 12);
	        tablero.add(lblNewLabel_08);
	        
	        JLabel lblNewLabel_09;
	        lblNewLabel_09 = new JLabel("");
	        lblNewLabel_09.setOpaque(true);
	        lblNewLabel_09.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_09.setBounds(166, 186, 12, 12);
	        tablero.add(lblNewLabel_09);
	        
	        JLabel lblNewLabel_012;
	        lblNewLabel_012 = new JLabel("");
	        lblNewLabel_012.setOpaque(true);
	        lblNewLabel_012.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_012.setBounds(326, 186, 12, 12);
	        tablero.add(lblNewLabel_012);
	        
	        JLabel lblNewLabel_0123;
	        lblNewLabel_0123 = new JLabel("");
	        lblNewLabel_0123.setOpaque(true);
	        lblNewLabel_0123.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_0123.setBounds(161, 105, 12, 12);
	        tablero.add(lblNewLabel_0123);
	        
	        JLabel lblNewLabel_0122;
	        lblNewLabel_0122 = new JLabel("");
	        lblNewLabel_0122.setOpaque(true);
	        lblNewLabel_0122.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_0122.setBounds(320, 105, 12, 12);
	        tablero.add(lblNewLabel_0122);
	        
	        JLabel lblNewLabel_009;
	        lblNewLabel_009 = new JLabel("");
	        lblNewLabel_009.setOpaque(true);
	        lblNewLabel_009.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_009.setBounds(161, 301, 12, 12);
	        tablero.add(lblNewLabel_009);
	        
	        JLabel lblNewLabel_0221;
	        lblNewLabel_0221 = new JLabel("");
	        lblNewLabel_0221.setOpaque(true);
	        lblNewLabel_0221.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_0221.setBounds(20, 422, 12, 12);
	        tablero.add(lblNewLabel_0221);
	        
	        JLabel lblNewLabel_0124;
	        lblNewLabel_0124 = new JLabel("");
	        lblNewLabel_0124.setOpaque(true);
	        lblNewLabel_0124.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_0124.setBounds(20, 465, 12, 12);
	        tablero.add(lblNewLabel_0124);
	        
	        JLabel lblNewLabel_081;
	        lblNewLabel_081 = new JLabel("");
	        lblNewLabel_081.setOpaque(true);
	        lblNewLabel_081.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_081.setBounds(10, 230, 12, 12);
	        tablero.add(lblNewLabel_081);
	        
	        JLabel lblNewLabel_14_1 = new JLabel("");
	        lblNewLabel_14_1.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_14_1.setOpaque(true);
	        lblNewLabel_14_1.setBounds(108, 276, 12, 12);
	        tablero.add(lblNewLabel_14_1);
	        
	        JLabel lblNewLabel_14_2 = new JLabel("");
	        lblNewLabel_14_2.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_14_2.setOpaque(true);
	        lblNewLabel_14_2.setBounds(229, 372, 12, 12);
	        tablero.add(lblNewLabel_14_2);
	        
	        JLabel lblNewLabel_14_3 = new JLabel("");
	        lblNewLabel_14_3.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_14_3.setOpaque(true);
	        lblNewLabel_14_3.setBounds(241, 186, 12, 12);
	        tablero.add(lblNewLabel_14_3);
	        
	        JLabel lblNewLabel_14_4 = new JLabel("");
	        lblNewLabel_14_4.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_14_4.setOpaque(true);
	        lblNewLabel_14_4.setBounds(375, 129, 12, 12);
	        tablero.add(lblNewLabel_14_4);
	        
	        JLabel lblNewLabel_14_5 = new JLabel("");
	        lblNewLabel_14_5.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_14_5.setOpaque(true);
	        lblNewLabel_14_5.setBounds(373, 21, 12, 12);
	        tablero.add(lblNewLabel_14_5);
	        
	        JLabel lblNewLabel_14_6 = new JLabel("");
	        lblNewLabel_14_6.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_14_6.setOpaque(true);
	        lblNewLabel_14_6.setBounds(161, 21, 12, 12);
	        tablero.add(lblNewLabel_14_6);
	        
	        JLabel lblNewLabel_14_7 = new JLabel("");
	        lblNewLabel_14_7.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_14_7.setOpaque(true);
	        lblNewLabel_14_7.setBounds(203, 82, 12, 12);
	        tablero.add(lblNewLabel_14_7);
	        
	        JLabel lblNewLabel_14_8 = new JLabel("");
	        lblNewLabel_14_8.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_14_8.setOpaque(true);
	        lblNewLabel_14_8.setBounds(20, 85, 12, 12);
	        tablero.add(lblNewLabel_14_8);
	        
	        JLabel lblNewLabel_14_9 = new JLabel("");
	        lblNewLabel_14_9.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_14_9.setOpaque(true);
	        lblNewLabel_14_9.setBounds(108, 165, 12, 12);
	        tablero.add(lblNewLabel_14_9);
	        
	        JLabel lblNewLabel_14_10 = new JLabel("");
	        lblNewLabel_14_10.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_14_10.setOpaque(true);
	        lblNewLabel_14_10.setBounds(215, 132, 12, 12);
	        tablero.add(lblNewLabel_14_10);
	        
	        JLabel lblNewLabel_14_11 = new JLabel("");
	        lblNewLabel_14_11.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_14_11.setOpaque(true);
	        lblNewLabel_14_11.setBounds(271, 132, 12, 12);
	        tablero.add(lblNewLabel_14_11);
	        
	        JLabel lblNewLabel_14_12 = new JLabel("");
	        lblNewLabel_14_12.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_14_12.setOpaque(true);
	        lblNewLabel_14_12.setBounds(326, 324, 12, 12);
	        tablero.add(lblNewLabel_14_12);
	        
	        JLabel lblNewLabel_14_13 = new JLabel("");
	        lblNewLabel_14_13.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_14_13.setOpaque(true);
	        lblNewLabel_14_13.setBounds(427, 395, 12, 12);
	        tablero.add(lblNewLabel_14_13);
	        
	        JLabel lblNewLabel_14_14 = new JLabel("");
	        lblNewLabel_14_14.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_14_14.setOpaque(true);
	        lblNewLabel_14_14.setBounds(81, 465, 12, 12);
	        tablero.add(lblNewLabel_14_14);
	        
	        JLabel lblNewLabel_14_15 = new JLabel("");
	        lblNewLabel_14_15.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_14_15.setOpaque(true);
	        lblNewLabel_14_15.setBounds(271, 422, 12, 12);
	        tablero.add(lblNewLabel_14_15);
	        
	        JLabel lblNewLabel_14_16 = new JLabel("");
	        lblNewLabel_14_16.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_14_16.setOpaque(true);
	        lblNewLabel_14_16.setBounds(215, 422, 12, 12);
	        tablero.add(lblNewLabel_14_16);
	        
	        JLabel lblNewLabel_14_17 = new JLabel("");
	        lblNewLabel_14_17.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/naranja.jpg")));
	        lblNewLabel_14_17.setOpaque(true);
	        lblNewLabel_14_17.setBounds(326, 276, 12, 12);
	        tablero.add(lblNewLabel_14_17);
	        
	        JLabel lblNewLabel_5 = new JLabel("");
	        lblNewLabel_5.setBorder(new LineBorder(new Color(0, 255, 255), 2));
	        lblNewLabel_5.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/trasparencia2.png")));
	        lblNewLabel_5.setBounds(0, 0, 884, 591);
	        panel.add(lblNewLabel_5);
	        
	        JLabel lblNewLabel_4 = new JLabel("");
	        lblNewLabel_4.setIcon(new ImageIcon(Tablero.class.getResource("/imagenes/fondo.gif")));
	        lblNewLabel_4.setBounds(0, 0, 884, 619);
	        panel.add(lblNewLabel_4);
	        
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					 temporizador.stop();
				     
				}
				@Override
				public void windowActivated(WindowEvent e) {
						System.out.println(name);
						inicializarTotalLabels();
					  	File audioFile = new File("sonidoInicio.wav");
					  	try {
			            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			            sound = AudioSystem.getClip();
			            sound.open(audioStream);
			            sound.start();
			            
			           musicaStart();
			            
				}catch(Exception ex) {
					System.out.print("Error en el sonido inicial");
				}	
				}
				});
			
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
		        	musicaStop();
		            ((Timer) e.getSource()).stop();	           
		            colisionDetectada.set(false);
		            terminarJuego();
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

        if (nuevaPosX < 0) {
            
            nuevaPosX = 468;
        } else if (nuevaPosX + jugador.getDiametro() > 498) {
           
            nuevaPosX = 8;
        }

        if (esPosicionValida(nuevaPosX, nuevaPosY)) {
        //    System.out.println("Posición del jugador después de mover: (" + nuevaPosX + ", " + nuevaPosY + ")");
            jugador.mover(nuevaPosX - jugador.getX(), deltaY); // Adjust the movement based on the new X position
            tablero.repaint();
            verificarColisiones();
        }
    }

    

    private Set<JLabel> labelsConsumidos = new HashSet<>();
    
    private void inicializarTotalLabels() {
        Component[] componentes = tablero.getComponents();
        for (Component componente : componentes) {
            if (componente instanceof JLabel) {
                totalLabels++;
            }
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
                return;  // Salgo del bucle después de la primera colisión encontrada
            }
        }

        // Verifica colisiones con etiquetas
        Component[] componentes = tablero.getComponents();
        for (Component componente : componentes) {
            if (componente instanceof JLabel) {
                JLabel label = (JLabel) componente;
                if (!labelsConsumidos.contains(label)) {
                    Rectangle labelRect = label.getBounds();
                    if (jugadorRect.intersects(labelRect)) {
                        // Colisión con un JLabel, oculta la etiqueta
                        label.setVisible(false);
                        labelsConsumidos.add(label);
                        puntos = puntos + 10;
                        lb_puntos.setText(Integer.toString(puntos));

                        // Verifica si todas las etiquetas han sido consumidas
                        if (labelsConsumidos.size() == totalLabels) {
                            // Todas las etiquetas han sido consumidas, gana la partida
                            ganarPartida();
                        }
                    }
                }
            }
        }
    }
    
    private void ganarPartida() {
		
    	 JOptionPane.showMessageDialog(this, "Has ganado!!!");
    	 musicaStop();
    	 PantallaFinalWin finJuego = new PantallaFinalWin(getName(), getPuntos());
         finJuego.setVisible(true);
         dispose();
	
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
                
                break;
        }
    }


    private void mostrarDialogoColision() {
        if (!colisionDetectada.getAndSet(true)) {
        	pausaTemporal = true;
            // Muestra un joption para la colision
            try {
            	sonidoMuerte();
                semaforoColision.acquire(); // cojo el semáforo para evitar colisiones concurrentes (wtf)
                restarVida(); //restamos vida
                // Reinicia las posiciones del jugador y los fantasmas
                if(vidas >0) {
                reiniciarJuego();
                }else {
                	musicaStop();
                	terminarJuego();
                }
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
        musicaStop();
        temporizador.stop();
    	 dispose();



        //Me cargo los hilos y next
        hiloMovimientoPacman.interrupt();
        hiloFantasmaAzul.interrupt();
        hiloFantasmaNaranja.interrupt();
        hiloFantasmaRojo.interrupt();
        hiloFantasmaRosa.interrupt();
        
       
        PantallaFinal finJuego = new PantallaFinal(getName(), getPuntos());
        finJuego.setVisible(true);
    }
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
    private void sonidoMuerte() {
        try {
            // Ruta del archivo de audio (cambia esto a la ubicación de tu archivo de música)
            File audioFile = new File("muerte.wav");

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            dead = AudioSystem.getClip();
            dead.open(audioStream);
            dead.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void musicaStart() {
        try {
            // Ruta del archivo de audio (cambia esto a la ubicación de tu archivo de música)
            File audioFile = new File("tema.wav");

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            tema = AudioSystem.getClip();
            tema.open(audioStream);
            tema.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void musicaStop() {
        try {
            // Si el Clip ya está abierto, lo detenemos
            if (tema != null && tema.isOpen()) {
                tema.stop();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

    public int getPuntos() {
		return puntos;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	@Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}