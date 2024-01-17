import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;

public class Ingame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ingame frame = new Ingame();
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
	public Ingame() {
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
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 215, 0));
		panel_1.setBounds(573, 11, 202, 498);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 11, 498, 498);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JPanel panel_5_1_2_1 = new JPanel();
		panel_5_1_2_1.setBounds(348, 105, 17, 114);
		panel_2.add(panel_5_1_2_1);
		
		JPanel panel_5_1_2 = new JPanel();
		panel_5_1_2.setBounds(134, 105, 17, 114);
		panel_2.add(panel_5_1_2);
		
		JPanel panel_5_1_1 = new JPanel();
		panel_5_1_1.setBounds(241, 105, 17, 65);
		panel_2.add(panel_5_1_1);
		
		JPanel panel_5_1 = new JPanel();
		panel_5_1.setBounds(241, 395, 17, 65);
		panel_2.add(panel_5_1);
		
		JPanel panel_3_1_1_2_2_2 = new JPanel();
		panel_3_1_1_2_2_2.setBounds(187, 395, 123, 16);
		panel_2.add(panel_3_1_1_2_2_2);
		
		JPanel panel_3_1_1_2_2_1_1 = new JPanel();
		panel_3_1_1_2_2_1_1.setBounds(294, 444, 160, 16);
		panel_2.add(panel_3_1_1_2_2_1_1);
		
		JPanel panel_3_1_1_2_2_1 = new JPanel();
		panel_3_1_1_2_2_1.setBounds(44, 444, 160, 16);
		panel_2.add(panel_3_1_1_2_2_1);
		
		JPanel panel_3_1_1_2_2 = new JPanel();
		panel_3_1_1_2_2.setBounds(187, 105, 123, 16);
		panel_2.add(panel_3_1_1_2_2);
		
		JPanel panel_3_1_1_2_1 = new JPanel();
		panel_3_1_1_2_1.setBounds(294, 347, 71, 16);
		panel_2.add(panel_3_1_1_2_1);
		
		JPanel panel_3_1_1_2 = new JPanel();
		panel_3_1_1_2.setBounds(133, 347, 71, 16);
		panel_2.add(panel_3_1_1_2);
		
		JPanel panel_3_1_1_1 = new JPanel();
		panel_3_1_1_1.setBounds(400, 105, 54, 16);
		panel_2.add(panel_3_1_1_1);
		
		JPanel panel_3_1_1 = new JPanel();
		panel_3_1_1.setBounds(44, 105, 54, 16);
		panel_2.add(panel_3_1_1);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(241, 0, 17, 73);
		panel_2.add(panel_5);
		
		JPanel panel_4_1_1_1 = new JPanel();
		panel_4_1_1_1.setBounds(133, 40, 71, 33);
		panel_2.add(panel_4_1_1_1);
		
		JPanel panel_4_1_1 = new JPanel();
		panel_4_1_1.setBounds(294, 40, 71, 33);
		panel_2.add(panel_4_1_1);
		
		JPanel panel_4_1 = new JPanel();
		panel_4_1.setBounds(400, 40, 54, 33);
		panel_2.add(panel_4_1);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(44, 40, 54, 33);
		panel_2.add(panel_4);
		
		JPanel panel_3_4_1 = new JPanel();
		panel_3_4_1.setBounds(0, 209, 10, 51);
		panel_2.add(panel_3_4_1);
		
		JPanel panel_3_4 = new JPanel();
		panel_3_4.setBounds(488, 209, 10, 51);
		panel_2.add(panel_3_4);
		
		JPanel panel_3_3 = new JPanel();
		panel_3_3.setBounds(400, 250, 10, 66);
		panel_2.add(panel_3_3);
		
		JPanel panel_3_2 = new JPanel();
		panel_3_2.setBounds(88, 250, 10, 66);
		panel_2.add(panel_3_2);
		
		JPanel panel_3_1 = new JPanel();
		panel_3_1.setBounds(88, 152, 10, 67);
		panel_2.add(panel_3_1);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(400, 152, 10, 67);
		panel_2.add(panel_3);
		
		JPanel parte_super_1_1_1_3 = new JPanel();
		parte_super_1_1_1_3.setBounds(0, 306, 98, 10);
		panel_2.add(parte_super_1_1_1_3);
		
		JPanel parte_super_1_1_1_2 = new JPanel();
		parte_super_1_1_1_2.setBounds(400, 306, 98, 10);
		panel_2.add(parte_super_1_1_1_2);
		
		JPanel parte_super_1_1_1 = new JPanel();
		parte_super_1_1_1.setBounds(0, 250, 98, 10);
		panel_2.add(parte_super_1_1_1);
		
		JPanel parte_super_1_1_1_1 = new JPanel();
		parte_super_1_1_1_1.setBounds(400, 250, 98, 10);
		panel_2.add(parte_super_1_1_1_1);
		
		JPanel parte_super_1_1 = new JPanel();
		parte_super_1_1.setBounds(0, 209, 98, 10);
		panel_2.add(parte_super_1_1);
		
		JPanel parte_super_1_2 = new JPanel();
		parte_super_1_2.setBounds(0, 152, 98, 10);
		panel_2.add(parte_super_1_2);
		
		JPanel parte_super_1 = new JPanel();
		parte_super_1.setBounds(400, 209, 98, 10);
		panel_2.add(parte_super_1);
		
		JPanel parte_super = new JPanel();
		parte_super.setBounds(400, 152, 98, 10);
		panel_2.add(parte_super);
		
		JPanel parte_inferior_der_1 = new JPanel();
		parte_inferior_der_1.setBounds(488, 306, 10, 192);
		panel_2.add(parte_inferior_der_1);
		
		JPanel parte_inferior_izq = new JPanel();
		parte_inferior_izq.setBounds(0, 306, 10, 192);
		panel_2.add(parte_inferior_izq);
		
		JPanel parte_inferior = new JPanel();
		parte_inferior.setBounds(0, 488, 498, 10);
		panel_2.add(parte_inferior);
		
		JPanel parte_superior_izquierda = new JPanel();
		parte_superior_izquierda.setBounds(0, 0, 10, 161);
		panel_2.add(parte_superior_izquierda);
		
		JPanel parte_superior_derecha = new JPanel();
		parte_superior_derecha.setBounds(488, 0, 10, 161);
		panel_2.add(parte_superior_derecha);
		
		JPanel parte_superior_exterior = new JPanel();
		parte_superior_exterior.setBounds(0, 0, 498, 10);
		panel_2.add(parte_superior_exterior);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Medac\\Desktop\\pacman.png"));
		lblNewLabel.setBounds(0, 0, 498, 498);
		panel_2.add(lblNewLabel);
		
		JPanel panel_3_1_1_3 = new JPanel();
		panel_3_1_1_3.setBounds(145, 154, 54, 16);
		panel_2.add(panel_3_1_1_3);
	}
}
