package vista;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modelo.Pez;
import modelo.Tiburon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

public class PanelAcuario extends JPanel {

	private static final long serialVersionUID = 1L;
	private List<Pez> peces;
	private List<Tiburon> tiburones;
	private JLabel lblFondo;
	private JLabel lblPeces;
	private JLabel lblTiburones;
	private int cantidadUno;
	private int cantidadDos;

	public PanelAcuario(int cantidadUno, int cantidadDos) {

		this.cantidadUno = cantidadUno;
		this.cantidadDos = cantidadDos;

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeigth = (int) screenSize.getHeight();
		setBounds(0, 0, screenWidth, screenHeigth);
		setSize(screenWidth, screenHeigth);
		setLayout(null);

		peces = new ArrayList<>();
		tiburones = new ArrayList<>();

		lblPeces = new JLabel("");
		lblPeces.setHorizontalAlignment(SwingConstants.CENTER);
		lblTiburones = new JLabel("");
		lblFondo = new JLabel("");
		lblFondo.setHorizontalAlignment(SwingConstants.CENTER);

		startComponents();

	}

	private void startComponents() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeigth = (int) screenSize.getHeight();
		Random random = new Random();

		for (int i = 0; i < cantidadUno; i++) {
			int x = random.nextInt(screenWidth);
			int y = random.nextInt(screenHeigth);
			Pez pez = new Pez(this, peces, x, y);
			peces.add(pez);
			add(pez);
			Thread pezThread = new Thread(pez);
			pezThread.start();
		}

		for (int i = 0; i < cantidadDos; i++) {
			int x = random.nextInt(screenWidth);
			int y = random.nextInt(screenHeigth);
			Tiburon tiburon = new Tiburon(this, peces, tiburones, x, y);
			tiburones.add(tiburon);
			add(tiburon);
			Thread tiburonThread = new Thread(tiburon);
			tiburonThread.start();
		}

		lblFondo.setBounds(0, 0, getWidth(), getHeight());
		ImageIcon imageIcon = new ImageIcon("src/img/fondo.jpg");
		Image image = imageIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
		lblFondo.setIcon(new ImageIcon(image));
		add(lblFondo);
		
		lblPeces.setBounds((getWidth()/2)-100, 20, 200, 20);
		lblPeces.setFont(new Font("Arial", Font.BOLD, 28));
		lblPeces.setForeground(new Color(255, 255, 255));
		add(lblPeces);

		lblTiburones.setBounds((getWidth()/2)-100, 50, 200, 20);
		lblTiburones.setFont(new Font("Arial", Font.BOLD, 28));
		lblTiburones.setForeground(new Color(255, 255, 255));
		add(lblTiburones);

	}

	public void nuevoPez() {
		repaint();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeigth = (int) screenSize.getHeight();
		Random random = new Random();
		int x = random.nextInt(screenWidth);
		int y = random.nextInt(screenHeigth);
		Pez pez = new Pez(this, peces, x, y);
		peces.add(pez);
		add(pez);
		Thread pezThread = new Thread(pez);
		pezThread.start();
		repaint();
	}

	public void eliminarPez(Pez pez, Pez pez2) {
		repaint();
		Random rand = new Random();
		boolean muerte = rand.nextBoolean();
		if (muerte == true) {
			if (pez.isVivo() && pez.isPeleando()) {
				remove(pez);
				peces.remove(pez);
				pez.setVivo(false);
			}
			pez2.setPeleando(false);
		} else {
			if (pez2.isVivo() && pez2.isPeleando()) {
				remove(pez2);
				peces.remove(pez2);
				pez2.setVivo(false);
			}
			pez.setPeleando(false);
		}
		repaint();
	}

	public void comerPez(Pez pez) {
		remove(pez);
		peces.remove(pez);
		pez.setVivo(false);
		repaint();
	}

	public void nuevoTiburon() {
		repaint();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeigth = (int) screenSize.getHeight();
		Random random = new Random();
		int x = random.nextInt(screenWidth);
		int y = random.nextInt(screenHeigth);
		Tiburon tiburon = new Tiburon(this, peces, tiburones, x, y);
		tiburones.add(tiburon);
		add(tiburon);
		Thread tiburonThread = new Thread(tiburon);
		tiburonThread.start();
		repaint();
	}

	public void eliminarTiburon(Tiburon tiburon, Tiburon tiburon2) {
		repaint();
		Random rand = new Random();
		boolean muerte = rand.nextBoolean();
		if (muerte == true) {
			if (tiburon.isVivo() && tiburon.isPeleando()) {
				remove(tiburon);
				tiburones.remove(tiburon);
				tiburon.setVivo(false);
			}
			tiburon2.setPeleando(false);
		} else {
			if (tiburon2.isVivo() && tiburon2.isPeleando()) {
				remove(tiburon2);
				tiburones.remove(tiburon2);
				tiburon2.setVivo(false);
			}
			tiburon.setPeleando(false);
		}
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		removeAll();
		lblPeces.setText("PECES: " + peces.size());
		lblTiburones.setText("TIBURONES: " + tiburones.size());
		for (int i = 0; i < peces.size(); i++) {
			Pez pez = peces.get(i);
			add(pez);
		}
		for (int i = 0; i < tiburones.size(); i++) {
			Tiburon tiburon = tiburones.get(i);
			add(tiburon);
		}
		add(lblPeces);
		add(lblTiburones);
		add(lblFondo);
	}

}
