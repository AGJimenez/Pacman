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
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Cursor;

public class Score extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	Bdd bdd = new Bdd();
	private JLabel nick1, nick2, nick3, nick4, nick5, nick6, nick7, punto1, punto2, punto3, punto4, punto5, punto6, punto7;
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
		
		//System.out.println("Lista: "+ bdd.sacarPuntos().toString());
		
		 List<Registro> listaRegistros = bdd.sacarPuntos();

		
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
		
		
	/*	 if (!listaRegistros.isEmpty()) {
	            nick1.setText(listaRegistros.get(0).getNickname());
	            nick2.setText(listaRegistros.get(1).getNickname());
	            nick3.setText(listaRegistros.get(2).getNickname());
	            nick4.setText(listaRegistros.get(3).getNickname());
	            nick5.setText(listaRegistros.get(4).getNickname());
	            nick6.setText(listaRegistros.get(5).getNickname());
	            nick7.setText(listaRegistros.get(6).getNickname());

	            punto1.setText(String.valueOf(listaRegistros.get(0).getPuntos()));
	            punto2.setText(String.valueOf(listaRegistros.get(1).getPuntos()));
	            punto3.setText(String.valueOf(listaRegistros.get(2).getPuntos()));
	            punto4.setText(String.valueOf(listaRegistros.get(3).getPuntos()));
	            punto5.setText(String.valueOf(listaRegistros.get(4).getPuntos()));
	            punto6.setText(String.valueOf(listaRegistros.get(5).getPuntos()));
	            punto7.setText(String.valueOf(listaRegistros.get(6).getPuntos()));
	        }*/
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel_1.setIcon(new ImageIcon(Score.class.getResource("/imagenes/back.png")));
		lblNewLabel_1.setBounds(73, 490, 64, 64);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel_1_1.setIcon(new ImageIcon(Score.class.getResource("/imagenes/next.png")));
		lblNewLabel_1_1.setBounds(301, 490, 64, 64);
		contentPanel.add(lblNewLabel_1_1);
		
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
	}
	
	
	
}
