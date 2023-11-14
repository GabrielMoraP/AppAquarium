package modelo;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import vista.PanelAcuario;

public class Pez extends JLabel implements Runnable {

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

	public Pez(PanelAcuario panel, List<Pez> peces, int x, int y) {
		setSize(50, 50);
		this.peleando = false;
		this.vivo = true;
		this.procrear = true;
		this.panelAcuario = panel;
		this.peces = peces;
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
			ImageIcon imageIcon = new ImageIcon("src/img/pezmacho.png");
			Image image = imageIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
			setIcon(new ImageIcon(image));
		} else {
			this.sexo = Sexo.H;
			ImageIcon imageIcon = new ImageIcon("src/img/pezhembra.png");
			Image image = imageIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
			setIcon(new ImageIcon(image));
		}

	}

	public void run() {
		try {
			while (vivo) {
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

				for (Pez pez : peces) {
					if (pez != this) {
						if (getBounds().intersects(pez.getBounds())) {
							if (this.isVivo() && pez.isVivo()) {
								if (pez.getSexo() == getSexo()) {
									if (isPeleando() == false && pez.isPeleando() == false) {
										AudioReproductor audioReproductor = new AudioReproductor("src/sound/mordidapez.mp3", false);
										Thread hiloReproductor = new Thread(audioReproductor);
										hiloReproductor.start();
										pez.setPeleando(true);
										setPeleando(true);
										Random rand = new Random();
										Thread.sleep(rand.nextInt(100));
										panelAcuario.eliminarPez(this, pez);
										break;
									}
								} else if (pez.getSexo() != getSexo()) {
									if (isProcrear() == true && pez.isProcrear() == true) {
										if (isPeleando() == false && pez.isPeleando() == false) {
											pez.setProcrear(false);
											setProcrear(false);
											Thread.sleep(1000);
											panelAcuario.nuevoPez();
											break;
										}
									}
								}
							}
						}
					}
				}
				setLocation(x, y);
				Thread.sleep(3);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 25, 25);
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
				ImageIcon imageIcon = new ImageIcon("src/img/pezmacho2.png");
				Image image = imageIcon.getImage().getScaledInstance(getWidth(), getHeight(),
						Image.SCALE_DEFAULT);
				setIcon(new ImageIcon(image));
			} else {
				ImageIcon imageIcon = new ImageIcon("src/img/pezhembra2.png");
				Image image = imageIcon.getImage().getScaledInstance(getWidth(), getHeight(),
						Image.SCALE_DEFAULT);
				setIcon(new ImageIcon(image));

			}
		}else {
			if (getSexo() == Sexo.M) {
				ImageIcon imageIcon = new ImageIcon("src/img/pezmacho.png");
				Image image = imageIcon.getImage().getScaledInstance(getWidth(), getHeight(),
						Image.SCALE_DEFAULT);
				setIcon(new ImageIcon(image));
			} else {
				ImageIcon imageIcon = new ImageIcon("src/img/pezhembra.png");
				Image image = imageIcon.getImage().getScaledInstance(getWidth(), getHeight(),
						Image.SCALE_DEFAULT);
				setIcon(new ImageIcon(image));

			}
		}
	}

	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}

	public boolean isVivo() {
		return vivo;
	}

	public boolean isPeleando() {
		return peleando;
	}

	public void setPeleando(boolean peleando) {
		this.peleando = peleando;
	}

	public boolean isProcrear() {
		return procrear;
	}

	public void setProcrear(boolean procrear) {
		this.procrear = procrear;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

}
