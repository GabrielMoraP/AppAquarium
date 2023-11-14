package modelo;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import vista.PanelAcuario;

public class Tiburon extends JLabel implements Runnable {

	private static final long serialVersionUID = 1L;
	private PanelAcuario panelAcuario;
	private Sexo sexo;
	private boolean procrear;
	private boolean vivo;
	private boolean peleando;
	private int x, y;
	private int width, height;
	private int dx, dy;
	private long tiempoUltimoCambioDireccion;
	private static final int TIEMPO_CAMBIO_DIRECCION = 1000;
	private List<Pez> peces;
	private List<Tiburon> tiburones;

	public Tiburon(PanelAcuario panel, List<Pez> peces, List<Tiburon> tiburones, int x, int y) {
		setSize(150, 75);
		this.peleando = false;
		this.vivo = true;
		this.procrear = true;
		this.panelAcuario = panel;
		this.peces = peces;
		this.tiburones = tiburones;
		this.x = x;
		this.y = y;
		this.width = getWidth();
		this.height = getHeight();
		this.dx = new Random().nextInt(5) - 2;
		this.dy = new Random().nextInt(5) - 2;
		this.tiempoUltimoCambioDireccion = System.currentTimeMillis();
		Random random = new Random();
		boolean genero = random.nextBoolean();
		if (genero == true) {
			this.sexo = Sexo.M;
			ImageIcon imageIcon = new ImageIcon("src/img/tiburonmacho.png");
			Image image = imageIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
			setIcon(new ImageIcon(image));
		} else {
			this.sexo = Sexo.H;
			ImageIcon imageIcon = new ImageIcon("src/img/tiburonhembra.png");
			Image image = imageIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
			setIcon(new ImageIcon(image));
		}
	}

	public void run() {
		try {
			while (true) {
				
				x += dx;
				y += dy;

				if (x <= 0) {
					x = 0;
					cambiarDireccion();
				} else if (x >= panelAcuario.getWidth() - width) {
					x = panelAcuario.getWidth() - width;
					cambiarDireccion();
				}

				if (y <= 0) {
					y = 0;
					cambiarDireccion();
				} else if (y >= panelAcuario.getHeight() - height) {
					y = panelAcuario.getHeight() - height;
					cambiarDireccion();
				}

				for (Tiburon tiburon : tiburones) {
					if (tiburon != this) {
						if (getBounds().intersects(tiburon.getBounds())) {
							if (this.isVivo() && tiburon.isVivo()) {
								if (tiburon.getSexo() == getSexo()) {
									if (isPeleando() == false && tiburon.isPeleando() == false) {
										AudioReproductor audioReproductor = new AudioReproductor(
												"src/sound/mordida.mp3", false);
										Thread hiloReproductor = new Thread(audioReproductor);
										hiloReproductor.start();
										tiburon.setPeleando(true);
										setPeleando(true);
										Random rand = new Random();
										Thread.sleep(rand.nextInt(100));
										panelAcuario.eliminarTiburon(this, tiburon);
										break;
									}
								} else if (tiburon.getSexo() != getSexo()) {
									if (isProcrear() == true && tiburon.isProcrear() == true) {
										if (isPeleando() == false && tiburon.isPeleando() == false) {
											tiburon.setProcrear(false);
											setProcrear(false);
											Thread.sleep(1000);
											panelAcuario.nuevoTiburon();
											break;
										}
									}
								}
							}
						}
					}
				}

				for (Pez pez : peces) {
					if (this.isVivo()) {
						if (pez.isVivo()) {
							if (isProcrear() == true && isPeleando() == false) {
								if (getBounds().intersects(pez.getBounds())) {
									AudioReproductor audioReproductor = new AudioReproductor("src/sound/mordida.mp3",
											false);
									Thread hiloReproductor = new Thread(audioReproductor);
									hiloReproductor.start();
									panelAcuario.comerPez(pez);
									Thread.sleep(500);
									break;
								}
							}
						}
					}
				}

				setLocation(x, y);
				Thread.sleep(6);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 75, 37);
	}

	private void cambiarDireccion() {
		long tiempoActual = System.currentTimeMillis();
		if (tiempoActual - tiempoUltimoCambioDireccion >= TIEMPO_CAMBIO_DIRECCION) {
			setProcrear(true);
			Random rand = new Random();
			dx = rand.nextInt(5) - 2;
			dy = rand.nextInt(5) - 2;
			tiempoUltimoCambioDireccion = tiempoActual;
		}
		if (dx > 0) {
			if (getSexo() == Sexo.M) {
				ImageIcon imageIcon = new ImageIcon("src/img/tiburonmacho2.png");
				Image image = imageIcon.getImage().getScaledInstance(getWidth(), getHeight(),
						Image.SCALE_DEFAULT);
				setIcon(new ImageIcon(image));
			} else {
				ImageIcon imageIcon = new ImageIcon("src/img/tiburonhembra2.png");
				Image image = imageIcon.getImage().getScaledInstance(getWidth(), getHeight(),
						Image.SCALE_DEFAULT);
				setIcon(new ImageIcon(image));

			}
		}else {
			if (getSexo() == Sexo.M) {
				ImageIcon imageIcon = new ImageIcon("src/img/tiburonmacho.png");
				Image image = imageIcon.getImage().getScaledInstance(getWidth(), getHeight(),
						Image.SCALE_DEFAULT);
				setIcon(new ImageIcon(image));
			} else {
				ImageIcon imageIcon = new ImageIcon("src/img/tiburonhembra.png");
				Image image = imageIcon.getImage().getScaledInstance(getWidth(), getHeight(),
						Image.SCALE_DEFAULT);
				setIcon(new ImageIcon(image));

			}
		}
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public boolean isProcrear() {
		return procrear;
	}

	public void setProcrear(boolean procrear) {
		this.procrear = procrear;
	}

	public boolean isVivo() {
		return vivo;
	}

	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}

	public boolean isPeleando() {
		return peleando;
	}

	public void setPeleando(boolean peleando) {
		this.peleando = peleando;
	}

}