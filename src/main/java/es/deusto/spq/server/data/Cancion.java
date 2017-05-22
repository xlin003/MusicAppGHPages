/**
 * 
 */
package es.deusto.spq.server.data;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class Cancion {

	private String nombre;
	private String genero;
	private String artista;
	private String path;
	private Usuario user;

	/**
	 * Constructor de la clase Cancion
	 * 
	 * @param nombre
	 * @param genero
	 * @param artista
	 * @param path
	 * @param user
	 */
	public Cancion(String nombre, String genero, String artista, String path, Usuario user) {
		super();
		this.nombre = nombre;
		this.genero = genero;
		this.artista = artista;
		this.path = path;
		this.user = user;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the genero
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * @param genero
	 *            the genero to set
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

	/**
	 * @return the artista
	 */
	public String getArtista() {
		return artista;
	}

	/**
	 * @param artista
	 *            the artista to set
	 */
	public void setArtista(String artista) {
		this.artista = artista;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the user
	 */
	public Usuario getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(Usuario user) {
		this.user = user;
	}

}
