import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PantallaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Clip clip;
	private boolean reproduciendo = true;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PantallaPrincipal frame = new PantallaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PantallaPrincipal() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				stopMusic();
			}
		});
		setUndecorated(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(PantallaPrincipal.class.getResource("/imagenes/pacman32.png")));
		setTitle("Pacman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 948, 599);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(this);
		JButton btnNewButton_1_1 = new JButton("");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JButton btn_linkedin = new JButton("");
		btn_linkedin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirEnlaceWeb("https://www.linkedin.com/in/alejandrogamezdev/");
				
			}
		});
		btn_linkedin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_linkedin.setIcon(new ImageIcon(PantallaPrincipal.class.getResource("/imagenes/linkedinchiquito.png")));
		btn_linkedin.setOpaque(false);
		btn_linkedin.setContentAreaFilled(false);
		btn_linkedin.setBorderPainted(false);
		btn_linkedin.setBorder(null);
		btn_linkedin.setBounds(813, 538, 32, 32);
		contentPane.add(btn_linkedin);
		
		JButton btn_github = new JButton("");
		btn_github.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirEnlaceWeb("https://github.com/AGJimenez");
				
			}
		});
		btn_github.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_github.setIcon(new ImageIcon(PantallaPrincipal.class.getResource("/imagenes/gitu.png")));
		btn_github.setOpaque(false);
		btn_github.setContentAreaFilled(false);
		btn_github.setBorderPainted(false);
		btn_github.setBorder(null);
		btn_github.setBounds(883, 538, 32, 32);
		contentPane.add(btn_github);
		
		JButton btn_portfolio = new JButton("");
		btn_portfolio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirEnlaceWeb("https://portfolio-alejandrogamez.web.app/");
			}
		});
		btn_portfolio.setIcon(new ImageIcon(PantallaPrincipal.class.getResource("/imagenes/logomoltpetit.png")));
		btn_portfolio.setOpaque(false);
		btn_portfolio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_portfolio.setContentAreaFilled(false);
		btn_portfolio.setBorder(null);
		btn_portfolio.setBorderPainted(false);
		btn_portfolio.setBounds(746, 538, 32, 32);
		contentPane.add(btn_portfolio);
		btnNewButton_1_1.setPressedIcon(new ImageIcon(PantallaPrincipal.class.getResource("/imagenes/exitw.png")));
		btnNewButton_1_1.setRolloverIcon(new ImageIcon(PantallaPrincipal.class.getResource("/imagenes/exitc.png")));
		btnNewButton_1_1.setIcon(new ImageIcon(PantallaPrincipal.class.getResource("/imagenes/exitw.png")));
		btnNewButton_1_1.setOpaque(false);
		btnNewButton_1_1.setFont(new Font("I pixel u", Font.PLAIN, 25));
		btnNewButton_1_1.setContentAreaFilled(false);
		btnNewButton_1_1.setBorderPainted(false);
		btnNewButton_1_1.setBounds(197, 351, 114, 41);
		contentPane.add(btnNewButton_1_1);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setSelectedIcon(new ImageIcon(PantallaPrincipal.class.getResource("/imagenes/leaderboardw.png")));
		btnNewButton_1.setRolloverIcon(new ImageIcon(PantallaPrincipal.class.getResource("/imagenes/leaderboardc.png")));
		btnNewButton_1.setIcon(new ImageIcon(PantallaPrincipal.class.getResource("/imagenes/leaderboardw.png")));
		btnNewButton_1.setOpaque(false);
		btnNewButton_1.setFont(new Font("I pixel u", Font.PLAIN, 25));
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setBounds(189, 274, 309, 41);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
                Nombre ventanaNombre = new Nombre();
                ventanaNombre.setPantallaPrincipal(PantallaPrincipal.this);
                ventanaNombre.setVisible(true);
			}
		});
		btnNewButton.setRolloverIcon(new ImageIcon(PantallaPrincipal.class.getResource("/imagenes/1playercyan.png")));
		btnNewButton.setPressedIcon(new ImageIcon(PantallaPrincipal.class.getResource("/imagenes/1playerwhite.png")));
		btnNewButton.setOpaque(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setIcon(new ImageIcon(PantallaPrincipal.class.getResource("/imagenes/1playerwhite.png")));
		btnNewButton.setFont(new Font("I pixel u", Font.PLAIN, 25));
		btnNewButton.setBounds(159, 192, 309, 41);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(PantallaPrincipal.class.getResource("/imagenes/panelBanner.png")));
		lblNewLabel.setBounds(714, 533, 235, 40);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Conecta conmigo!");
		lblNewLabel_1.setFont(new Font("I pixel u", Font.PLAIN, 20));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(714, 495, 201, 32);
		contentPane.add(lblNewLabel_1);
		playMusic();
		
		JButton btn_music = new JButton("");
		
		btn_music.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_music.setIcon(new ImageIcon(PantallaPrincipal.class.getResource("/imagenes/botonMusicOn.png")));
		btn_music.setOpaque(false);
		btn_music.setContentAreaFilled(false);
		btn_music.setBorderPainted(false);
		btn_music.setBorder(null);
		btn_music.setBounds(38, 538, 32, 32);
		contentPane.add(btn_music);
		
		btn_music.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(reproduciendo == false) {
					playMusic();
					reproduciendo = true;
					btn_music.setIcon(new ImageIcon(PantallaPrincipal.class.getResource("/imagenes/botonMusicOn.png")));
				}else {
					stopMusic();
					reproduciendo = false;
					btn_music.setIcon(new ImageIcon(PantallaPrincipal.class.getResource("/imagenes/botonMusicOff.png")));
				}

			}
		});
		
		JLabel lb_fondo = new JLabel("");
		lb_fondo.setIcon(new ImageIcon(PantallaPrincipal.class.getResource("/imagenes/arcades.gif")));
		lb_fondo.setBounds(0, 0, 957, 601);
		contentPane.add(lb_fondo);
	}
	
	private void abrirEnlaceWeb(String enlace) {
        try {
            Desktop.getDesktop().browse(new URI(enlace));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	
    private void playMusic() {
        try {
            // Ruta del archivo de audio (cambia esto a la ubicación de tu archivo de música)
            File audioFile = new File("hiloFondoSonido.wav");

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
