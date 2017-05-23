package es.deusto.spq.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import es.deusto.spq.server.data.Cancion;
import es.deusto.spq.server.data.Cancionfavorita;
import es.deusto.spq.server.data.Usuario;

public class MFdao implements IMFdao {

	private PersistenceManagerFactory pmf;
	private String username;
	private String usermail;

	/**
	 * Constructor de mfdao crea un persistence manager factory
	 */
	public MFdao() {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}

	/**
	 * Metodo para guardar un objeto en la bases de datos
	 * 
	 * @param object
	 *            objeto que le pasamos para guardar en la bds
	 */
	private boolean storeObject(Object object) {
		boolean b = false;
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			System.out.println("   * Storing an object: " + object);
			pm.makePersistent(object);
			tx.commit();
			b = true;
		} catch (Exception ex) {
			System.out.println("   $ Error storing an object: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		return b;
	}

	public boolean checkUser(Usuario user) {
		boolean b = false;
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			System.out.println("   * Checking if " + user + " exists in the database");
			Extent<Usuario> ex = pm.getExtent(Usuario.class, true);
			for (Usuario u : ex) {
				if (u.getEmail().equals(user.getEmail())) {
					b = true;
				}
			}
			tx.commit();
		} catch (Exception ex) {
			System.out.println("   $ Error during the checking of user: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		return b;
	}

	public boolean loginUser(String email, String password) {
		boolean b = false;
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			Extent<Usuario> ex = pm.getExtent(Usuario.class, true);
			for (Usuario u : ex) {
				if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
					b = true;
					this.username = u.getNombre();
					this.usermail = u.getEmail();
				}
			}
			tx.commit();
		} catch (Exception ex) {
			System.out.println("   $ Error login for user: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return b;
	}

	@Override
	public boolean storeUser(Usuario u) {
		boolean b = false;
		if (!this.checkUser(u)) {
			b = this.storeObject(u);
		} else {
			System.out.println("The user mail " + u.getEmail() + " already exists");
		}
		return b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.deusto.spq.server.dao.IMFdao#getUserName()
	 */
	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return this.username;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.deusto.spq.server.dao.IMFdao#storeSong(es.deusto.spq.server.data.Cancion)
	 */
	@Override
	public void storeSong(Cancion c) {
		// TODO Auto-generated method stub
		 this.storeObject(c);

		// PersistenceManager pm = pmf.getPersistenceManager();
		// Transaction tx = pm.currentTransaction();
		// try {
		// tx.begin();
		// pm.newQuery("INSERT INTO CANCION(ARTISTA, GENERO, NOMBRE, PATH)VALUES('" + c.getNombre() + "', '" +
		// c.getGenero()
		// + "', '" + c.getArtista() + "', '" + c.getPath() + "')");
		// System.out.println("okkk");
		// } catch (Exception ex) {
		// System.out.println(" $ Error storing an object: " + ex.getMessage());
		// } finally {
		// if (tx != null && tx.isActive()) {
		// tx.rollback();
		// }
		//
		// pm.close();
		// }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.deusto.spq.server.dao.IMFdao#getUserMail()
	 */
	@Override
	public String getUserMail() {
		// TODO Auto-generated method stub
		return this.usermail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.deusto.spq.server.dao.IMFdao#loadSongs()
	 */
	@Override
	public List<String> loadSongs() {
		// TODO Auto-generated method stub
		List<String> listSong = new ArrayList<String>();
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			Extent<Cancion> extent = pm.getExtent(Cancion.class, true);
			for (Cancion can : extent) {
				String song = can.getNombre() + "#" + can.getArtista() + "#" + can.getGenero() + "#" + can.getPath();
				listSong.add(song);
			}
			tx.commit();
		} catch (Exception ex) {
			System.out.println(" $ Error retrieving songs: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		return listSong;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.deusto.spq.server.dao.IMFdao#searchSong()
	 */
	@Override
	public List<String> searchSong(String keyword) {
		// TODO Auto-generated method stub
		List<String> listSong = new ArrayList<String>();
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Extent<Cancion> extent = pm.getExtent(Cancion.class, true);
			for (Cancion can : extent) {
				if (can.getNombre().equals(keyword) || can.getGenero().equals(keyword)
						|| can.getArtista().equals(keyword)) {
					String song = can.getNombre() + "#" + can.getArtista() + "#" + can.getGenero() + "#"
							+ can.getPath();
					listSong.add(song);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(" $ Error retrieving songs: " + e.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return listSong;
	}

	public void storeFavoriteSong(Cancionfavorita cf) {
		// TODO Auto-generated method stub
		 this.storeObject(cf);
	}

	@Override
	public List<String> loadFavoriteSongs() {
		// TODO Auto-generated method stub
		List<String> listSong = new ArrayList<String>();
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			Extent<Cancionfavorita> extent = pm.getExtent(Cancionfavorita.class, true);
			for (Cancionfavorita cf : extent) {
				String song = cf.getArtista() + "#" + cf.getNombre();
				listSong.add(song);
			}
			tx.commit();
		} catch (Exception ex) {
			System.out.println(" $ Error retrieving songs: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		return listSong;
	}

}
