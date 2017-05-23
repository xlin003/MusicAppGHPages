/**
 *
 */
package es.deusto.spq.client.controller;

import java.rmi.RemoteException;
import java.util.List;

import es.deusto.spq.client.gui.MFwindows;
import es.deusto.spq.client.remote.RMIServiceLocator;

public class MFcontroller {

	private RMIServiceLocator mfrsl;
	private boolean login = false;

	/**
	 * Constructor de la clase MFcontroller
	 *
	 * @param args
	 *            recibe los argumentos para la configuración de la conexión remota
	 * @throws RemoteException
	 */
	public MFcontroller(String[] args) throws RemoteException {
		// TODO Auto-generated constructor stub
		super();
		mfrsl = new RMIServiceLocator();
		mfrsl.setService(args[0], args[1], args[2]);
		new MFwindows(this);
	}

	/**
	 * Metodo que registra el usuario en la bds
	 *
	 * @param nombre
	 *            nombre del usuario
	 * @param apellido
	 *            apellido del usuario
	 * @param email
	 *            email del usuario (no puede ser null)
	 * @param password
	 *            password del usuario (no puede ser null)
	 * @return return a boolean true if is registered successfully
	 * @throws RemoteException
	 */
	public boolean registerUser(String nombre, String apellido, String email, String password) throws RemoteException {
		return mfrsl.getService().registerUser(nombre, apellido, email, password);
	}

	/**
	 * Metoso login que loguea el usuario
	 *
	 * @param email
	 *            el email del usuario
	 * @param password
	 *            el password del usuario
	 * @return return a boolean true if is logged in successfully
	 * @throws RemoteException
	 */
	public boolean loginUser(String email, String password) throws RemoteException {
		return mfrsl.getService().loginUser(email, password);
	}

	/**
	 * Main method
	 *
	 * @param args
	 * @throws RemoteException
	 */
	public static void main(String[] args) throws RemoteException {
		new MFcontroller(args);
	}

	/**
	 * Metodo para subir una cancion a la bds
	 *
	 * @param name
	 * @param genero
	 * @param artista
	 * @param cancion
	 * @param useridentification
	 */
	public void uploadSong(String name, String genero, String artista, String cancion, String useridentification) {
		try {
			mfrsl.getService().uploadSong(name, genero, artista, cancion, useridentification);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the login
	 */
	public boolean isLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(boolean login) {
		this.login = login;
	}

	/**
	 * The function that return the user name
	 *
	 * @return
	 * @throws RemoteException
	 */
	public String getUserName() throws RemoteException {
		return mfrsl.getService().getName();
	}

	/**
	 * The function that return the user identification
	 *
	 * @return
	 * @throws RemoteException
	 */
	public String getUserIdentification() throws RemoteException {
		return mfrsl.getService().getUserMail();
	}

	/**
	 * Load Song method
	 *
	 * @return Return a list of string of songs
	 * @throws RemoteException
	 */
	public List<String> loadSong() throws RemoteException {
		return mfrsl.getService().loadSongs();

	}

	/**
	 * Método de búsqueda de canción se puede buscar por el nombre de la canción, por el artista o por el género de la
	 * canción.
	 *
	 * @param keyword
	 *            La palabra clase para la búsqueda
	 * @return Retorna la canción buscado o una lista de canciones
	 * @throws RemoteException
	 */
	public List<String> searchSong(String keyword) throws RemoteException {
		return mfrsl.getService().searchSong(keyword);

	}

	public void registerFavoriteSong(String nombre, String artista) throws RemoteException {
		try {
			mfrsl.getService().registerFavoriteSong(nombre, artista);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<String> loadFavoriteSong() throws RemoteException {
		return mfrsl.getService().loadFavoriteSongs();

	}

}
