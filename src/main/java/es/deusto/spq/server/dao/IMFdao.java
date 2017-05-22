package es.deusto.spq.server.dao;

import java.util.List;

import es.deusto.spq.server.data.Cancion;
import es.deusto.spq.server.data.Usuario;

public interface IMFdao {

	/**
	 * 
	 * @param u
	 * @return
	 */
	public boolean storeUser(Usuario u);

	/**
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public boolean loginUser(String email, String password);

	/**
	 * 
	 * @return
	 */
	public String getUserName();

	/**
	 * 
	 * @param c
	 */
	public void storeSong(Cancion c);

	/**
	 * 
	 * @return
	 */
	public String getUserMail();

	/**
	 * 
	 * @param keyword
	 * @return
	 */
	public List<String> searchSong(String keyword);

	/**
	 * 
	 * @return
	 */
	public List<String> loadSongs();

	/**
	 * 
	 * @param user
	 * @return
	 */
	public boolean checkUser(Usuario user);
}
