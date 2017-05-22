package es.deusto.spq.client.gui;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Player extends Thread {

	AdvancedPlayer adplay;

	/**
	 * The player class constructor
	 * 
	 * @param file
	 */
	public Player(String file) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			adplay = new AdvancedPlayer(fis);
		} catch (FileNotFoundException | JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {

		try {
			adplay.play();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the adplay
	 */
	public AdvancedPlayer getAdplay() {
		return adplay;
	}

	/**
	 * @param adplay
	 *            the adplay to set
	 */
	public void setAdplay(AdvancedPlayer adplay) {
		this.adplay = adplay;
	}

}