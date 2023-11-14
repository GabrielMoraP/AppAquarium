package vista;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import modelo.AudioReproductor;

public class VistaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private PanelAcuario panel;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaPrincipal frame = new VistaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VistaPrincipal() {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeigth = (int) screenSize.getHeight();
		
		setTitle("Acuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(0, 0, screenWidth, screenHeigth);
		setSize(screenWidth, screenHeigth);
		
		ImageIcon iconoOriginal = new ImageIcon("src/img/pezmacho.png");
        Image imagenRedimensionada = iconoOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon iconoRedimensionado = new ImageIcon(imagenRedimensionada);
        int cantidadPeces = Integer.parseInt((String) JOptionPane.showInputDialog(null, "INGRESE LA CANTIDAD DE PECES", "PECES", JOptionPane.INFORMATION_MESSAGE, iconoRedimensionado, null, null));
        
		ImageIcon iconoOriginal2 = new ImageIcon("src/img/tiburonmacho.png");
        Image imagenRedimensionada2 = iconoOriginal2.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        ImageIcon iconoRedimensionado2 = new ImageIcon(imagenRedimensionada2);
		int cantidadTiburones = Integer.parseInt((String) JOptionPane.showInputDialog(null, "INGRESE LA CANTIDAD DE TIBURONES", "PECES", JOptionPane.INFORMATION_MESSAGE, iconoRedimensionado2, null, null));
		
		AudioReproductor audioReproductor = new AudioReproductor("src/sound/agua.mp3", true);
		Thread hiloReproductor = new Thread(audioReproductor);
		hiloReproductor.start();
		
		panel = new PanelAcuario(cantidadPeces, cantidadTiburones);
		getContentPane().add(panel);

	}

}
