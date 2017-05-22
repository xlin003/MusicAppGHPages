package es.deusto.spq.client.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import es.deusto.spq.client.controller.MFcontroller;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

public class SignupGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MFcontroller mfcon;
	private JPanel contentPane;
	private JTextField jtfName;
	private JTextField jtfApellido;
	private JTextField jtfMail;
	private JPasswordField jtfPassword;
	private JPasswordField jtfConfirmacion;

	/**
	 * Create the frame.
	 */
	protected SignupGUI(MFcontroller mfcon) {
		this.mfcon = mfcon;
		this.initComponents();
		this.setVisible(true);
		this.centreWindow();
		this.setTitle("Signing up");
	}

	private void centreWindow() {
		Dimension dim = getToolkit().getScreenSize();
		Rectangle abounds = getBounds();
		setLocation((dim.width - abounds.width) / 2, (dim.height - abounds.height) / 2);
	}

	private void initComponents() {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Font font = new Font("SansSerif", Font.PLAIN, 18);
		setBounds(100, 100, 421, 416);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblRegistro = new JLabel("REGISTRO");
		lblRegistro.setBounds(140, 26, 150, 30);
		lblRegistro.setFont(lblRegistro.getFont().deriveFont(25.0f));
		contentPane.add(lblRegistro);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(45, 80, 100, 30);
		lblNombre.setFont(font);
		contentPane.add(lblNombre);

		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(45, 130, 100, 30);
		lblApellido.setFont(font);
		contentPane.add(lblApellido);

		JLabel lblMail = new JLabel("Email");
		lblMail.setBounds(45, 180, 100, 30);
		lblMail.setFont(font);
		contentPane.add(lblMail);

		JLabel lblContrasea = new JLabel("Contraseña");
		lblContrasea.setBounds(45, 230, 140, 30);
		lblContrasea.setFont(font);
		contentPane.add(lblContrasea);

		JLabel lblConfirmacin = new JLabel("Confirmación");
		lblConfirmacin.setBounds(45, 280, 150, 30);
		lblConfirmacin.setFont(font);
		contentPane.add(lblConfirmacin);

		jtfName = new JTextField();
		jtfName.setBounds(220, 80, 160, 26);
		contentPane.add(jtfName);
		jtfName.setColumns(10);
		jtfName.setFont(font);

		jtfApellido = new JTextField();
		jtfApellido.setBounds(220, 130, 160, 26);
		contentPane.add(jtfApellido);
		jtfApellido.setColumns(10);
		jtfApellido.setFont(font);

		jtfMail = new JTextField();
		jtfMail.setBounds(220, 180, 160, 26);
		contentPane.add(jtfMail);
		jtfMail.setColumns(10);
		jtfMail.setFont(font);

		jtfPassword = new JPasswordField();
		jtfPassword.setBounds(220, 230, 160, 26);
		contentPane.add(jtfPassword);
		jtfPassword.setColumns(10);
		jtfPassword.setFont(font);

		jtfConfirmacion = new JPasswordField();
		jtfConfirmacion.setBounds(220, 280, 160, 26);
		contentPane.add(jtfConfirmacion);
		jtfConfirmacion.setColumns(10);
		jtfConfirmacion.setFont(font);

		JButton btnRegister = new JButton("Registrarse");
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				signupActionPerformed(e);
			}
		});

		JButton btnCancel = new JButton("Cancelar");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelActionPerformed(e);
			}
		});
		btnRegister.setBounds(70, 350, 140, 30);
		btnRegister.setFont(font);
		contentPane.add(btnRegister);
		btnCancel.setBounds(240, 350, 130, 30);
		btnCancel.setFont(font);
		contentPane.add(btnCancel);
	}

	protected boolean signupActionPerformed(ActionEvent e) {
		boolean b = false;
		if (jtfMail.getText().equals("")) {
			System.out.println("The user mail could not be empty!");
		} else if (new String(jtfPassword.getPassword()).isEmpty()) {
			System.out.println("Enter the password!");
		} else if (new String(jtfConfirmacion.getPassword()).isEmpty()) {
			System.out.println("Confirm the password!");
		} else if (!new String(jtfPassword.getPassword()).equals(new String(jtfConfirmacion.getPassword()))) {
			System.out.println("The introduced passwords doesn't match");
		} else {
			try {
				b = this.mfcon.registerUser(jtfName.getText(), jtfApellido.getText(), jtfMail.getText(),
						new String(jtfPassword.getPassword()));
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (b) {
				this.dispose();
				JOptionPane.showMessageDialog(this, "congratulations you have been registrated successfully");
			}
		}
		return b;
	}

	private void cancelActionPerformed(ActionEvent e) {
		this.dispose();
	}

}
