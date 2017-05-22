package es.deusto.spq.client.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import es.deusto.spq.client.controller.MFcontroller;

/**
 * 
 * @class SigninGUI.java
 * The login window class 
 *
 */
public class SigninGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private MFcontroller mfcon;
	private JPanel contentPane;
	private JTextField jtfMail;
	private JPasswordField jtfPassword;
	private JButton btnAccept = new JButton("Aceptar");
	private JButton btnCancel = new JButton("Cancelar");
	// private boolean done = false;
	
	/**
	 * The Signin class constructor
	 * @param mfcon 
	 */
	protected SigninGUI(MFcontroller mfcon) {	
		this.mfcon = mfcon;
		this.initComponents();
		this.setVisible(true);
		this.centreWindow();
		this.setTitle("Signing in");
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

		JLabel lblMail = new JLabel("Email");
		lblMail.setBounds(45, 100, 100, 30);
		lblMail.setFont(font);
		contentPane.add(lblMail);

		JLabel lblContrasea = new JLabel("Contraseña");
		lblContrasea.setBounds(45, 180, 140, 30);
		lblContrasea.setFont(font);
		contentPane.add(lblContrasea);

		jtfMail = new JTextField();
		jtfMail.setBounds(220, 100, 160, 26);
		contentPane.add(jtfMail);
		jtfMail.setColumns(10);
		jtfMail.setFont(font);

		jtfPassword = new JPasswordField();
		jtfPassword.setBounds(220, 180, 160, 26);
		contentPane.add(jtfPassword);
		jtfPassword.setColumns(10);
		jtfPassword.setFont(font);

		btnAccept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				acceptActionPerformed(e);
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelActionPerformed(e);
			}
		});

		btnAccept.setBounds(70, 300, 130, 30);
		btnAccept.setFont(font);
		contentPane.add(btnAccept);
		btnCancel.setBounds(240, 300, 130, 30);
		btnCancel.setFont(font);
		contentPane.add(btnCancel);
	}

	protected boolean acceptActionPerformed(ActionEvent e) {
		boolean b = false;
		if (jtfMail.getText().equals("")) {
			System.out.println("The user mail could not be empty!");
		} else if (new String(jtfPassword.getPassword()).isEmpty()) {
			System.out.println("Enter the password!");
		} else {
			try {
				b = mfcon.loginUser(jtfMail.getText(), new String(jtfPassword.getPassword()));
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (b) {
				System.out.println("Login successfully!");
				mfcon.setLogin(true);
				this.dispose();
			} else {
				System.out.println("User account or password error!");
			}
		}
		return b;
	}

	private void cancelActionPerformed(ActionEvent e) {
		this.dispose();
	}

}
