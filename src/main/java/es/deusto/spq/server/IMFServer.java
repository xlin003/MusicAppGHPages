package es.deusto.spq.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import es.deusto.spq.server.data.Usuario;

public interface IMFServer extends Remote {

	String sayMessage(String login, String password, String message) throws RemoteException;

	public boolean registerUser(String nombre, String apellido, String email, String password) throws RemoteException;

	public boolean loginUser(String email, String password) throws RemoteException;
	
	public String getName() throws RemoteException;
	
	public String getUserMail() throws RemoteException;
	
	public List<String> loadSongs() throws RemoteException;
	
	public List<String> searchSong(String keyword);
	
	public void uploadSong(String name, String genero, String artista, String cancion, String useridentification);

	public List<String> loadFavoriteSongs() throws RemoteException;

	public void registerFavoriteSong(String nombre, String artista) throws RemoteException;
}
