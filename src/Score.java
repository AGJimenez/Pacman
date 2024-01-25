import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Cursor;

public class Score extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private Bdd bdd = new Bdd();
    private JLabel nick1, nick2, nick3, nick4, nick5, nick6, nick7, punto1, punto2, punto3, punto4, punto5, punto6, punto7;
    private JLabel[] nicknameLabels = new JLabel[7];
    private JLabel[] puntoLabels = new JLabel[7];
    private int posicionActual = 0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Score dialog = new Score();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Score() {
        setUndecorated(true);
        setResizable(false);
        setTitle("Puntuacion");
        setBounds(100, 100, 450, 587);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(41, 41, 41));
        contentPanel.setBorder(new LineBorder(new Color(255, 0, 255), 3));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        setLocationRelativeTo(rootPane);
        
        
        
		JButton btn2 = new JButton("");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 if (hayDatosEnPosicion(posicionActual + 1)) {
	                    posicionActual++;
	                    bdd.sacarPuntos(Score.this, posicionActual);
	                }
			}
		});
		btn2.setOpaque(false);
		btn2.setContentAreaFilled(false);
		btn2.setBorder(null);
		btn2.setBorderPainted(false);
		btn2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn2.setIcon(new ImageIcon(Score.class.getResource("/imagenes/next.png")));
		btn2.setBounds(301, 490, 64, 64);
		contentPanel.add(btn2);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton.setIcon(new ImageIcon(Score.class.getResource("/imagenes/exit.png")));
		btnNewButton.setOpaque(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorder(null);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setBounds(168, 496, 91, 45);
		contentPanel.add(btnNewButton);
		

		JButton btn1 = new JButton("");
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  if (hayDatosEnPosicion(posicionActual - 1)) {
	                    posicionActual--;
	                    bdd.sacarPuntos(Score.this, posicionActual);
	                }
			}
		});
		btn1.setOpaque(false);
		btn1.setContentAreaFilled(false);
		btn1.setBorder(null);
		btn1.setBorderPainted(false);
		btn1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn1.setIcon(new ImageIcon(Score.class.getResource("/imagenes/back.png")));
		btn1.setBounds(73, 490, 64, 64);
		contentPanel.add(btn1);

		
		JLabel lblNewLabel = new JLabel("LEADERBOARD");
		lblNewLabel.setForeground(new Color(0, 255, 255));
		lblNewLabel.setFont(new Font("I pixel u", Font.PLAIN, 38));
		lblNewLabel.setBounds(60, 49, 319, 37);
		contentPanel.add(lblNewLabel);
		
		JLabel TITULOUSER = new JLabel("NICKNAME");
		TITULOUSER.setFont(new Font("I pixel u", Font.PLAIN, 25));
		TITULOUSER.setForeground(new Color(255, 0, 255));
		TITULOUSER.setBounds(60, 122, 162, 37);
		contentPanel.add(TITULOUSER);
		
		JLabel TITULOSCORE = new JLabel("SCORE");
		TITULOSCORE.setForeground(Color.MAGENTA);
		TITULOSCORE.setFont(new Font("I pixel u", Font.PLAIN, 25));
		TITULOSCORE.setBounds(274, 122, 106, 37);
		contentPanel.add(TITULOSCORE);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.MAGENTA, 2));
		panel.setBackground(new Color(41, 41, 41));
		panel.setBounds(48, 122, 175, 350);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		nick1 = new JLabel("NICKNAME");
        nick1.setForeground(Color.RED);
        nick1.setFont(new Font("I pixel u", Font.PLAIN, 25));
        nick1.setBounds(10, 53, 162, 37);
        panel.add(nick1);

        nick2 = new JLabel("NICKNAME");
        nick2.setForeground(Color.ORANGE);
        nick2.setFont(new Font("I pixel u", Font.PLAIN, 25));
        nick2.setBounds(10, 90, 162, 37);
        panel.add(nick2);

        nick3 = new JLabel("NICKNAME");
        nick3.setForeground(Color.GREEN);
        nick3.setFont(new Font("I pixel u", Font.PLAIN, 25));
        nick3.setBounds(10, 131, 162, 37);
        panel.add(nick3);

        nick4 = new JLabel("NICKNAME");
        nick4.setForeground(Color.CYAN);
        nick4.setFont(new Font("I pixel u", Font.PLAIN, 25));
        nick4.setBounds(10, 174, 162, 37);
        panel.add(nick4);

        nick5 = new JLabel("NICKNAME");
        nick5.setForeground(Color.WHITE);
        nick5.setFont(new Font("I pixel u", Font.PLAIN, 25));
        nick5.setBounds(10, 214, 162, 37);
        panel.add(nick5);

        nick6 = new JLabel("NICKNAME");
        nick6.setForeground(Color.YELLOW);
        nick6.setFont(new Font("I pixel u", Font.PLAIN, 25));
        nick6.setBounds(10, 253, 162, 37);
        panel.add(nick6);

        nick7 = new JLabel("NICKNAME");
        nick7.setForeground(Color.PINK);
        nick7.setFont(new Font("I pixel u", Font.PLAIN, 25));
        nick7.setBounds(10, 291, 162, 37);
        panel.add(nick7);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new LineBorder(Color.MAGENTA, 2));
        panel_1.setBackground(new Color(41, 41, 41));
        panel_1.setForeground(Color.DARK_GRAY);
        panel_1.setBounds(261, 122, 124, 350);
        contentPanel.add(panel_1);
        panel_1.setLayout(null);

        punto1 = new JLabel("0000");
        punto1.setHorizontalAlignment(SwingConstants.CENTER);
        punto1.setForeground(Color.RED);
        punto1.setFont(new Font("I pixel u", Font.PLAIN, 20));
        punto1.setBounds(10, 52, 104, 37);
        panel_1.add(punto1);

        punto2 = new JLabel("0000");
        punto2.setHorizontalAlignment(SwingConstants.CENTER);
        punto2.setForeground(Color.ORANGE);
        punto2.setFont(new Font("I pixel u", Font.PLAIN, 20));
        punto2.setBounds(10, 89, 104, 37);
        panel_1.add(punto2);

        punto3 = new JLabel("0000");
        punto3.setHorizontalAlignment(SwingConstants.CENTER);
        punto3.setForeground(Color.GREEN);
        punto3.setFont(new Font("I pixel u", Font.PLAIN, 20));
        punto3.setBounds(10, 130, 104, 37);
        panel_1.add(punto3);

        punto4 = new JLabel("0000");
        punto4.setHorizontalAlignment(SwingConstants.CENTER);
        punto4.setForeground(Color.CYAN);
        punto4.setFont(new Font("I pixel u", Font.PLAIN, 20));
        punto4.setBounds(10, 173, 104, 37);
        panel_1.add(punto4);

        punto5 = new JLabel("0000");
        punto5.setHorizontalAlignment(SwingConstants.CENTER);
        punto5.setForeground(Color.WHITE);
        punto5.setFont(new Font("I pixel u", Font.PLAIN, 20));
        punto5.setBounds(10, 213, 104, 37);
        panel_1.add(punto5);

        punto6 = new JLabel("0000");
        punto6.setHorizontalAlignment(SwingConstants.CENTER);
        punto6.setForeground(Color.YELLOW);
        punto6.setFont(new Font("I pixel u", Font.PLAIN, 20));
        punto6.setBounds(10, 252, 104, 37);
        panel_1.add(punto6);

        punto7 = new JLabel("0000");
        punto7.setHorizontalAlignment(SwingConstants.CENTER);
        punto7.setForeground(Color.PINK);
        punto7.setFont(new Font("I pixel u", Font.PLAIN, 20));
        punto7.setBounds(10, 290, 104, 37);
        panel_1.add(punto7);
        
        nicknameLabels[0] = nick1;
        nicknameLabels[1] = nick2;
        nicknameLabels[2] = nick3;
        nicknameLabels[3] = nick4;
        nicknameLabels[4] = nick5;
        nicknameLabels[5] = nick6;
        nicknameLabels[6] = nick7;

        puntoLabels[0] = punto1;
        puntoLabels[1] = punto2;
        puntoLabels[2] = punto3;
        puntoLabels[3] = punto4;
        puntoLabels[4] = punto5;
        puntoLabels[5] = punto6;
        puntoLabels[6] = punto7;
        
        bdd.sacarPuntos(this, posicionActual);

    }
	  // Método para actualizar la interfaz de usuario con los datos obtenidos
	public void actualizarInterfazUsuario(List<String> nicknames, List<Integer> puntos, int elementosObtenidos) {
	    for (int i = 0; i < elementosObtenidos; i++) {
	        nicknameLabels[i].setText(nicknames.get(i));
	        puntoLabels[i].setText(String.format("%04d", puntos.get(i)));
	    }

	    // Rellenar con "000000" solo los labels necesarios
	    for (int i = elementosObtenidos; i < 7; i++) {
	        nicknameLabels[i].setText("000000");
	        puntoLabels[i].setText("0000");
	    }
	}

    private boolean hayDatosEnPosicion(int posicion) {
        int totalRegistros = bdd.obtenerTotalRegistros();

        // Verifica si hay datos en la posición dada
        return posicion >= 0 && posicion < totalRegistros;
    }

	}