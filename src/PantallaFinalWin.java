import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PantallaFinalWin extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static String name;
	private static int puntos;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PantallaFinalWin dialog = new PantallaFinalWin(name, puntos);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public PantallaFinalWin(String name, int puntos) {
		
		setUndecorated(true);
		this.name = name;
		this.puntos = puntos;
		
		setBounds(100, 100, 478, 270);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		this.setLocationRelativeTo(contentPanel);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon(PantallaFinalWin.class.getResource("/imagenes/confetti.gif")));
		lblNewLabel_4.setBounds(310, 39, 158, 160);
		contentPanel.add(lblNewLabel_4);
		{
			JLabel lb_puntos1 = new JLabel("");
			lb_puntos1.setForeground(Color.MAGENTA);
			lb_puntos1.setHorizontalAlignment(SwingConstants.CENTER);
			lb_puntos1.setFont(new Font("I pixel u", Font.PLAIN, 40));
			lb_puntos1.setBounds(220, 105, 218, 52);
			lb_puntos1.setText(Integer.toString(puntos));
			contentPanel.add(lb_puntos1);
		}
		{
			JLabel lb_puntos2 = new JLabel("");
			lb_puntos2.setHorizontalAlignment(SwingConstants.CENTER);
			lb_puntos2.setForeground(Color.CYAN);
			lb_puntos2.setFont(new Font("I pixel u", Font.PLAIN, 40));
			lb_puntos2.setBounds(222, 103, 218, 52);
			lb_puntos2.setText(Integer.toString(puntos));
			contentPanel.add(lb_puntos2);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("SCORE");
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_3.setForeground(Color.MAGENTA);
			lblNewLabel_3.setFont(new Font("I pixel u", Font.PLAIN, 40));
			lblNewLabel_3.setBounds(20, 105, 190, 52);
			contentPanel.add(lblNewLabel_3);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("SCORE");
			lblNewLabel_3.setForeground(Color.CYAN);
			lblNewLabel_3.setFont(new Font("I pixel u", Font.PLAIN, 40));
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_3.setBounds(22, 103, 190, 52);
			contentPanel.add(lblNewLabel_3);
		}
		{
			JLabel lb_puntos3 = new JLabel("");
			lb_puntos3.setHorizontalAlignment(SwingConstants.CENTER);
			lb_puntos3.setForeground(Color.BLACK);
			lb_puntos3.setFont(new Font("I pixel u", Font.PLAIN, 40));
			lb_puntos3.setBounds(218, 100, 218, 52);
			lb_puntos3.setText(Integer.toString(puntos));
			contentPanel.add(lb_puntos3);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("SCORE");
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_3.setForeground(Color.BLACK);
			lblNewLabel_3.setFont(new Font("I pixel u", Font.PLAIN, 40));
			lblNewLabel_3.setBounds(18, 100, 190, 52);
			contentPanel.add(lblNewLabel_3);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Quieres guardar tu puntuación?");
			lblNewLabel_2.setForeground(Color.CYAN);
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_2.setFont(new Font("I pixel u", Font.PLAIN, 20));
			lblNewLabel_2.setBounds(36, 158, 413, 30);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Quieres guardar tu puntuación?");
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_2.setForeground(Color.MAGENTA);
			lblNewLabel_2.setFont(new Font("I pixel u", Font.PLAIN, 20));
			lblNewLabel_2.setBounds(37, 159, 413, 30);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Quieres guardar tu puntuación?");
			lblNewLabel_2.setBackground(Color.BLACK);
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_2.setForeground(Color.BLACK);
			lblNewLabel_2.setFont(new Font("I pixel u", Font.PLAIN, 20));
			lblNewLabel_2.setBounds(35, 157, 413, 30);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("");
			lblNewLabel_1.setIcon(new ImageIcon(PantallaFinalWin.class.getResource("/imagenes/win.png")));
			lblNewLabel_1.setBounds(35, 54, 413, 43);
			contentPanel.add(lblNewLabel_1);
		}
		
		
		
		{
			JButton okButton = new JButton("");
			okButton.setBorderPainted(false);
			okButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			okButton.setContentAreaFilled(false);
			okButton.setOpaque(false);
			okButton.setIcon(new ImageIcon(PantallaFinalWin.class.getResource("/imagenes/go.png")));
			okButton.setBounds(320, 210, 30, 30);
			contentPanel.add(okButton);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					Bdd bdd = new Bdd();
					bdd.insertarPuntos(name, puntos);
					dispose();
					PantallaPrincipal pp = new PantallaPrincipal();
					pp.setVisible(true);
					
				}
			});
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
			
		}
		{
			JButton cancelButton = new JButton("");
			cancelButton.setBorderPainted(false);
			cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			cancelButton.setContentAreaFilled(false);
			cancelButton.setOpaque(false);
			cancelButton.setIcon(new ImageIcon(PantallaFinalWin.class.getResource("/imagenes/cancel.png")));
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					PantallaPrincipal pp = new PantallaPrincipal();
					pp.setVisible(true);
				}
			});
			cancelButton.setBounds(150, 210, 30, 30);
			contentPanel.add(cancelButton);
			cancelButton.setActionCommand("Cancel");
		}
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(PantallaFinalWin.class.getResource("/imagenes/giphy.gif")));
		lblNewLabel.setBounds(0, 0, 478, 270);
		contentPanel.add(lblNewLabel);
		
		
	}
}
