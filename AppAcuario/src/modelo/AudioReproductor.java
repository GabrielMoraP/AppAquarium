package modelo;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class AudioReproductor implements Runnable {

	private String rutaAudio;
	private boolean repetir;

	public AudioReproductor(String rutaAudio, boolean repetir) {
		this.rutaAudio = rutaAudio;
		this.repetir = repetir;
	}

	@Override
	public void run() {
		do {
			try {
				FileInputStream archivo = new FileInputStream(rutaAudio);
				BufferedInputStream flujoEntrada = new BufferedInputStream(archivo);
				Player player = new Player(flujoEntrada);
				player.play();
			} catch (FileNotFoundException | JavaLayerException e) {
				System.out.println(e);
			}
		} while (repetir);
	}
}
