/**
 * 
 */
package es.deusto.spq.client.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import es.deusto.spq.client.controller.MFcontroller;

public class UploadGUI {

	private JFrame frame;
	private JPanel panel;
	private JLabel lnombre;
	private JLabel lgenero;
	private JLabel lartista;
	private JLabel lsong;
	private JTextField tfnombre;
	private JTextField tfgenero;
	private JTextField tfartista;
	private JTextField tfcancion;
	private JButton chooser;
	private JButton aceptar;
	private JButton cancelar;
	private JFileChooser fileChooser;
	private String choosertitle = "Upload";
	private MFcontroller mfcon;
	private Path destpath;

	/**
	 * The constructor of class UploadGUI
	 */
	public UploadGUI(MFcontroller mfcon) {
		this.mfcon = mfcon;
		initComponents();
		centreWindow();
		frame.setVisible(true);
	}

	private void initComponents() {
		Font font = new Font("SansSerif", Font.BOLD, 22);
		Font f = new Font("SansSerif", Font.PLAIN, 20);
		Font f1 = new Font("SansSerif", Font.BOLD, 16);
		frame = new JFrame("Upload panel");
		frame.setSize(500, 550);
		panel = new JPanel();
		frame.add(panel);

		panel.setLayout(null);

		lnombre = new JLabel("Nombre");
		lnombre.setBounds(50, 60, 100, 30);
		lnombre.setFont(font);
		panel.add(lnombre);

		lgenero = new JLabel("Genero");
		lgenero.setBounds(50, 140, 100, 30);
		lgenero.setFont(font);
		panel.add(lgenero);

		lartista = new JLabel("Artista");
		lartista.setBounds(50, 220, 100, 30);
		lartista.setFont(font);
		panel.add(lartista);

		lsong = new JLabel("Cancion");
		lsong.setBounds(50, 300, 100, 30);
		lsong.setFont(font);
		panel.add(lsong);

		tfnombre = new JTextField();
		tfnombre.setBounds(200, 60, 200, 30);
		tfnombre.setFont(f);
		panel.add(tfnombre);
		tfnombre.setVisible(true);

		tfgenero = new JTextField();
		tfgenero.setBounds(200, 140, 200, 30);
		tfgenero.setFont(f);
		panel.add(tfgenero);
		tfgenero.setVisible(true);

		tfartista = new JTextField();
		tfartista.setBounds(200, 220, 200, 30);
		tfartista.setFont(f);
		panel.add(tfartista);
		tfartista.setVisible(true);

		tfcancion = new JTextField();
		tfcancion.setBounds(200, 300, 200, 30);
		tfcancion.setFont(f);
		panel.add(tfcancion);
		tfcancion.setVisible(true);

		chooser = new JButton("...");
		chooser.setBounds(420, 300, 30, 30);
		panel.add(chooser);

		aceptar = new JButton("Aceptar");
		aceptar.setBounds(300, 400, 110, 30);
		aceptar.setFont(f1);
		panel.add(aceptar);

		cancelar = new JButton("Cancelar");
		cancelar.setBounds(100, 400, 110, 30);
		cancelar.setFont(f1);
		panel.add(cancelar);

		chooser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				chooserActionPerformed(e);
			}
		});

		cancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
			}
		});

		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (tfnombre.getText().isEmpty() || tfcancion.getText().isEmpty()) {
					System.out.println("Enter the name of song and the song to upload");
				} else {
					try {
						mfcon.uploadSong(tfnombre.getText(), tfgenero.getText(), tfartista.getText(),
								destpath.toString(), mfcon.getUserIdentification());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					frame.dispose();
				}
				
			}
		});
	}
	
	/**
	 * 
	 * @param e
	 */
	private void chooserActionPerformed(ActionEvent e) {
		String dir = System.getProperty("user.dir");
		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(choosertitle);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Audio Files", "mp3", "wav");
		fileChooser.setFileFilter(filter);

		if (fileChooser.showOpenDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
			System.out.println("getCurrentDirectory(): " + fileChooser.getCurrentDirectory());
			System.out.println("getSelectedFile() : " + fileChooser.getSelectedFile());
			String dest = dir + "/src/main/server/es/deusto/spq/server/audio/";
			destpath = Paths.get(dest + "/" + fileChooser.getSelectedFile().getName());
			
			try {
				Files.copy(fileChooser.getSelectedFile().toPath(), destpath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			tfcancion.setText(fileChooser.getSelectedFile().getName());
			System.out.println(fileChooser.getSelectedFile().toPath());
			System.out.println(destpath);
		} else {
			System.out.println("No Selection");
		}
	}

	private void centreWindow() {
		Dimension dim = frame.getToolkit().getScreenSize();
		Rectangle abounds = frame.getBounds();
		frame.setLocation((dim.width - abounds.width) / 2, (dim.height - abounds.height) / 2);
	}

}
