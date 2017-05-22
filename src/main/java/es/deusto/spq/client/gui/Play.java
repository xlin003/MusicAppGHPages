/**
 * 
 */
package es.deusto.spq.client.gui;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Play extends Thread {

	private String file;
	public static Clip audio;

	public Play(String file) {
		super();
		this.file = file;
		audio = null;
	}

	public void run() {

		try {
			audio = AudioSystem.getClip();
			File a = new File(file);
			audio.open(AudioSystem.getAudioInputStream(a));
			audio.start();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
