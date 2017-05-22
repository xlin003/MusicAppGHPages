/**
 * 
 */
package es.deusto.spq.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import es.deusto.spq.client.remote.MFManager;
import es.deusto.spq.server.dao.IMFdao;
import es.deusto.spq.server.dao.MFdao;
import es.deusto.spq.server.data.Cancion;
import es.deusto.spq.server.data.Usuario;

public class MFServer extends UnicastRemoteObject implements IMFServer {

	private static final long serialVersionUID = 1L;
	private IMFdao mfdao;
	MFManager mfm;

	/**
	 * The server side main method which establish the remote connexion with the client
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("How to invoke: java [policy] [codebase] Server.Server [host] [port] [server]");
			System.exit(0);
		}

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		String name = "//" + args[0] + ":" + args[1] + "/" + args[2];

		try {
			IMFServer objServer = new MFServer();
			Naming.rebind(name, objServer);
			System.out.println("Server '" + name + "' active and waiting...");
			InputStreamReader inputStreamReader = new InputStreamReader(System.in);
			BufferedReader stdin = new BufferedReader(inputStreamReader);
			String line = stdin.readLine();
		} catch (Exception e) {
			System.err.println("Remote exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @throws RemoteException
	 */
	public MFServer() throws RemoteException {
		super();
		mfdao = new MFdao();
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.deusto.spq.server.IMFServer#registerUser(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean registerUser(String nombre, String apellido, String email, String password) throws RemoteException {
		// TODO Auto-generated method stub
		Usuario u = new Usuario(nombre, apellido, email, password);
		return mfdao.storeUser(u);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.deusto.spq.server.IMFServer#loginUser(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean loginUser(String email, String password) throws RemoteException {
		// TODO Auto-generated method stub
		return mfdao.loginUser(email, password);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.deusto.spq.server.IMFServer#sayMessage(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String sayMessage(String login, String password, String message) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.deusto.spq.server.IMFServer#getName()
	 */
	@Override
	public String getName() throws RemoteException {
		// TODO Auto-generated method stub
		return mfdao.getUserName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.deusto.spq.server.IMFServer#uploadSong(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, es.deusto.spq.server.data.Usuario)
	 */
	@Override
	public void uploadSong(String name, String genero, String artista, String cancion, String useridentification)
			throws RemoteException {
		// TODO Auto-generated method stub
		Usuario u = new Usuario(null, null, useridentification, null);
		Cancion can = new Cancion(name, genero, artista, cancion, u);
		mfdao.storeSong(can);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.deusto.spq.server.IMFServer#getUserMail()
	 */
	@Override
	public String getUserMail() throws RemoteException {
		// TODO Auto-generated method stub
		return mfdao.getUserMail();
	}

	/* (non-Javadoc)
	 * @see es.deusto.spq.server.IMFServer#loadSongs()
	 */
	@Override
	public List<String> loadSongs() throws RemoteException {
		// TODO Auto-generated method stub
		return mfdao.loadSongs();
	}

	/* (non-Javadoc)
	 * @see es.deusto.spq.server.IMFServer#searchSong(java.lang.String)
	 */
	@Override
	public List<String> searchSong(String keyword) throws RemoteException {
		// TODO Auto-generated method stub
		return mfdao.searchSong(keyword);
	}

}
