import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;

public class Nombre extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private String name;
	private PantallaPrincipal pantallaPrincipal;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Nombre dialog = new Nombre();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Nombre() {
		setModal(true);
		setResizable(false);
		setUndecorated(true);

		setBounds(100, 100, 450, 171);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.BLACK);
		contentPanel.setBorder(new LineBorder(Color.CYAN, 4));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		this.setLocationRelativeTo(contentPanel);
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.setIcon(new ImageIcon(Nombre.class.getResource("/imagenes/cancel.png")));
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setBorder(null);
		btnNewButton_1.setBounds(49, 124, 32, 32);
		contentPanel.add(btnNewButton_1);
		
        JButton btnNewButton = new JButton("");
        btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	name = textField.getText();
                dispose();
                if (pantallaPrincipal != null) {
                    pantallaPrincipal.dispose();
                }
                Tablero juego = new Tablero(name);
                juego.setVisible(true);
            }
        });

		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorder(null);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setIcon(new ImageIcon(Nombre.class.getResource("/imagenes/go.png")));
		btnNewButton.setBounds(376, 124, 32, 32);
		contentPanel.add(btnNewButton);
		
		JLabel lbl_name = new JLabel("NAME:");
		lbl_name.setForeground(Color.CYAN);
		lbl_name.setFont(new Font("I pixel u", Font.PLAIN, 40));
		lbl_name.setBounds(49, 66, 144, 38);
		contentPanel.add(lbl_name);
		
		textField = new JTextField();
		textField.setForeground(Color.CYAN);
		textField.setOpaque(false);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("I pixel u", Font.PLAIN, 30));
		textField.setBounds(193, 65, 215, 48);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel(".......................");
		lblNewLabel.setForeground(Color.CYAN);
		lblNewLabel.setFont(new Font("I pixel u", Font.PLAIN, 40));
		lblNewLabel.setBounds(193, 88, 215, 25);
		contentPanel.add(lblNewLabel);
	}
	
    public void setPantallaPrincipal(PantallaPrincipal pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }
    private void cerrarPantallaPrincipal() {
        if (pantallaPrincipal != null) {
            pantallaPrincipal.dispose();
        }
    }

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}
    
    
}
