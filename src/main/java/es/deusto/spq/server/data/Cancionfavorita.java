package es.deusto.spq.server.data;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)

public class Cancionfavorita {
	private String nombre;
	private String artista;
	
	public Cancionfavorita(String nombre, String artista) {
		this.nombre = nombre;
		this.artista = artista;
		
	
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getArtista() {
		return artista;
	}
	public void setArtista(String artista) {
		this.artista = artista;
	}

}
