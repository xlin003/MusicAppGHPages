package es.deusto.spq.client.gui;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.swing.*;
import es.deusto.spq.client.controller.MFcontroller;

/**
 * The Free tune application windows class
 */
public class MFwindows extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private MFcontroller mfcon;
	private JPanel topPanel;
	// private JPanel bottomPanel;
	private JButton signup;
	private JButton signin;
	private JButton logout;
	private JButton upload;
	private JButton go;
	private JLabel title;
	private JLabel playlabel;
	private JLabel pauselabel;
	private JLabel addlabel;
	private JLabel deletelabel;
	private JLabel editlabel;
	private JLabel ulabel;
	private JCTextField searchBox;
	private Thread t;
	private JScrollPane scrollpane;
	private String DELIMITER = "#";
	private String path;
	// private boolean play = false;
	// private boolean pause = false;

	// private List<String> listSong = new ArrayList<String>();
	private Map<Integer, String> map = new HashMap<>();

	Player player;

	final DefaultListModel<Object> tune = new DefaultListModel<Object>();
	final DefaultListModel<Object> searchedtune = new DefaultListModel<Object>();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JList tunelist = new JList(tune);

	/**
	 * Constructor de la ventana free tune
	 * 
	 * @param mfcon
	 *            El controlador de MFcontroller
	 * @throws RemoteException
	 */
	public MFwindows(MFcontroller mfcon) throws RemoteException {
		this.mfcon = mfcon;
		initComponents();
		this.setSize(1100, 700);
		this.setVisible(true);
		this.centreWindow();
		this.setTitle("Free tune");
	}

	/**
	 * Metodo para situar la ventana en el centro
	 */
	public void centreWindow() {
		Dimension dim = getToolkit().getScreenSize();
		Rectangle abounds = getBounds();
		setLocation((dim.width - abounds.width) / 2, (dim.height - abounds.height) / 2);
	}

	public void loadSong() {
		tune.clear();
		int i = 0;
		try {
			for (String s : mfcon.loadSong()) {
				StringTokenizer tokenizer = new StringTokenizer(s, DELIMITER);
				String name = tokenizer.nextToken();
				String artist = tokenizer.nextToken();
				String genero = tokenizer.nextToken();
				path = tokenizer.nextToken();
				map.put(i, path);
				tune.addElement("  #" + i + "  " + name + "   " + artist + "  " + genero);
				i++;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que inicializa los componentes de la ventana
	 * 
	 * @throws RemoteException
	 */
	private void initComponents() throws RemoteException {

		Font font = new Font("SansSerif", Font.PLAIN, 18);
		Font font1 = new Font("Dialog", Font.BOLD, 14);
		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		topPanel = new JPanel();
		// bottomPanel = new JPanel();
		// bottomPanel.setBackground(new Color(240, 248, 255));
		ImageIcon playicon = new ImageIcon(getClass().getResource("/es/deusto/spq/client/images/p1.png"));
		ImageIcon favoriteicon = new ImageIcon(getClass().getResource("/es/deusto/spq/client/images/d.png"));
		ImageIcon pauseicon = new ImageIcon(getClass().getResource("/es/deusto/spq/client/images/s1.png"));
		ImageIcon addicon = new ImageIcon(getClass().getResource("/es/deusto/spq/client/images/a.png"));
		ImageIcon editicon = new ImageIcon(getClass().getResource("/es/deusto/spq/client/images/e.png"));

		playlabel = new JLabel(playicon);
		playlabel.setBounds(150, 85, 60, 60);
		playlabel.setCursor(cursor);
		topPanel.add(playlabel);

		pauselabel = new JLabel(pauseicon);
		pauselabel.setBounds(150, 85, 60, 60);
		pauselabel.setCursor(cursor);
		topPanel.add(pauselabel);

		addlabel = new JLabel(addicon);
		addlabel.setBounds(80, 84, 60, 60);
		addlabel.setCursor(cursor);
		topPanel.add(addlabel);

		deletelabel = new JLabel(favoriteicon);
		deletelabel.setBounds(15, 84, 60, 60);
		deletelabel.setCursor(cursor);
		topPanel.add(deletelabel);

		editlabel = new JLabel(editicon);
		editlabel.setBounds(220, 84, 60, 60);
		editlabel.setCursor(cursor);
		topPanel.add(editlabel);

		pauselabel.setVisible(false);

		loadSong();
		tunelist.setFont(new Font("Dialog", Font.ITALIC, 20));
		tunelist.setFixedCellHeight(30);

		playlabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (tunelist.isSelectionEmpty()) {
					System.out.println("Select the audio to play");
				} else {
					pauselabel.setVisible(true);
					playlabel.setVisible(false);
					String[] s = tunelist.getSelectedValue().toString().split("");
					for (Map.Entry<Integer, String> entry : map.entrySet()) {
						if (entry.getKey().equals(Integer.parseInt(s[3]))) {
							player = new Player(entry.getValue());
							player.start();
							break;
						}
					}
				}
			}
		});

		pauselabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				playlabel.setVisible(true);
				pauselabel.setVisible(false);
				player.stop();
			}
		});

		scrollpane = new JScrollPane(tunelist, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
		tunelist.setBackground(new Color(240, 248, 255));
		signup = new JButton("Sgin up");
		signup.setFont(font1);
		signup.setBounds(950, 30, 93, 30);

		signin = new JButton("Sgin in");
		signin.setFont(font1);
		signin.setBounds(950, 96, 93, 30);

		go = new JButton("Go");
		go.setFont(font1);
		go.setBounds(723, 100, 60, 30);

		searchBox = new JCTextField();
		searchBox.setBounds(324, 100, 400, 30);
		searchBox.setPlaceholder("Search your favorite song");
		searchBox.setFont(font);

		title = new JLabel("Freetune");
		title.setFont(new Font("sans-serif", Font.BOLD, 35));
		title.setCursor(cursor);
		title.setBounds(430, 30, 190, 25);

		topPanel.setPreferredSize(new Dimension(100, 150));
		scrollpane.setPreferredSize(new Dimension(100, 700));
		// topPanel.setBackground(new Color(135, 206, 250));
		topPanel.setBackground(new Color(176, 226, 255));
		topPanel.add(signup);
		topPanel.add(signin);
		topPanel.add(title);
		topPanel.add(searchBox);
		topPanel.add(go);

		topPanel.setLayout(null);
		title.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				loadSong();
				getContentPane().validate();
				getContentPane().repaint();
			}
		});
		signup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new SignupGUI(mfcon);
			}
		});

		signin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				signinActionPerformed(e);
			}

		});

		go.addActionListener(new ActionListener() {
			List<String> list = new ArrayList<>();

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				map.clear();
				tune.clear();
				try {
					list = mfcon.searchSong(searchBox.getText());
					int j = 0;
					for (String s : list) {
						StringTokenizer tokenizer = new StringTokenizer(s, DELIMITER);
						String name = tokenizer.nextToken();
						String artist = tokenizer.nextToken();
						String genero = tokenizer.nextToken();
						path = tokenizer.nextToken();
						map.put(j, path);
						tune.addElement("  #" + j + "  " + name + "   " + artist + "  " + genero);
						j++;
					}
					getContentPane().validate();
					getContentPane().repaint();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		getContentPane().add(topPanel, BorderLayout.PAGE_START);
		getContentPane().add(scrollpane, BorderLayout.CENTER);

	}

	/**
	 * Sign in action performed method
	 * 
	 * @param e
	 *            The action event
	 */
	public void signinActionPerformed(ActionEvent e) {
		new SigninGUI(mfcon);
		t = new Thread(this);
		t.start();
	}

	public void run() {
		Font font1 = new Font("Dialog", Font.BOLD, 14);
		while (!mfcon.isLogin()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		signin.setVisible(false);
		signup.setVisible(false);
		try {
			ulabel = new JLabel("Hello " + mfcon.getUserName());
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ulabel.setFont(new Font("sans-serif", Font.BOLD, 21));
		ulabel.setBounds(960, 30, 250, 25);
		topPanel.add(ulabel);

		upload = new JButton("Upload");
		upload.setFont(font1);
		upload.setBounds(818, 100, 110, 30);
		topPanel.add(upload);

		logout = new JButton("Log Out");
		logout.setFont(font1);
		logout.setBounds(960, 100, 110, 30);
		topPanel.add(logout);

		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mfcon.setLogin(false);
				logout.setVisible(false);
				ulabel.setVisible(false);
				upload.setVisible(false);
				signin.setVisible(true);
				signup.setVisible(true);
				System.out.println("Bye, see you again!");
			}
		});

		deletelabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (mfcon.isLogin())
					System.out.println("Deleting...");
			}
		});

		addlabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (mfcon.isLogin())
					System.out.println("Add to favorite");
			}
		});

		upload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				uploadActionPerformed(e);
			}
		});

		getContentPane().validate();
		getContentPane().repaint();
	}

	public void uploadActionPerformed(ActionEvent e) {
		new UploadGUI(this.mfcon);
	}

}
