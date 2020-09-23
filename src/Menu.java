/* Francisco Javier Ramos Velasco A01636425
 * Jose Ricardo Vanegas Castillo A01634956*/


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.applet.AudioClip;

public class Menu extends JPanel implements MouseListener, Runnable  {
	private Image menuImg, 
						  Pato1, 
						  Pato2, 
						  Perro;
	private Contenedor contenedor;
	private Thread hiloStop;
	private boolean altoSound;
	private AudioClip sound;
	private Game Game;
	private Dog perro;
	public Menu(Contenedor contenedor) {
		super();
		this.contenedor = contenedor;
		this.setPreferredSize(new Dimension(800, 600));
		this.menuImg = new ImageIcon("images/pantallaInicio.jpg").getImage();
		this.Pato1 = new ImageIcon("images/PatoAlasAbajo.png").getImage();
		this.Pato2 = new ImageIcon("images/PatoAlasArriba.png").getImage();
		this.Perro = new ImageIcon("images/Atrapado.png").getImage();
		this.hiloStop = new Thread(this);
		this.audio();
		this.addMouseListener(this);
		this.hiloStop.start();

	}

		private void audio() {
			sound = java.applet.Applet.newAudioClip(getClass().getResource("intro.mid"));
			sound.play();
		}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Imágenes
		g.drawImage(this.menuImg, 0, 0, this.getWidth(), this.getHeight(), this);
		g.drawImage(this.Pato1, (int)(this.getWidth()*.03), (int)(this.getHeight()*.6),
				(int)+(this.getWidth()*.2),(int)(this.getHeight()*.2),this);
		g.drawImage(this.Pato2, (int)(this.getWidth()*.03+(this.getWidth()*.2)), (int)(this.getHeight()*.6),
				(int)+(this.getWidth()*.2),(int)(this.getHeight()*.2),this);
		g.drawImage(this.Perro, (int)(this.getWidth()*.65),(int)(this.getHeight()*.6),
				(int)(this.getWidth()*.3),(int)(this.getHeight()*.3),this);
		
		//Strings
		g.setColor(Color.yellow);
		g.setFont(new Font("arial", Font.BOLD, 48));
		g.drawString("Start", (int) ((this.getWidth() / 2) - (this.getWidth() * .055)+15),
				((this.getHeight() / 2)) + (this.getHeight() / 6)+50);
		g.drawString("Quit", (int) ((this.getWidth() / 2) - (this.getWidth() * .047)+15),
				(int) ((this.getHeight() / 2) + (this.getHeight() / 6) + (this.getHeight() * .067))+50);
	}

	public void mouseClicked(MouseEvent evt) {
		if (evt.getX() > 384 && evt.getX() < 493 && evt.getY() > 414 && evt.getY() < 451) {
			System.out.println("Presionaste start");
			this.contenedor.cl.show(this.contenedor, "2");
			this.altoSound = true;
			
		} else if (evt.getX() > 384 && evt.getX() < 493 && evt.getY() > 458 && evt.getY() < 490) {
			System.out.println("Presionaste Quit");
			System.exit(1);
		}
	}
	
	
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent evt) {}
	public void mouseExited(MouseEvent e) {}

	@Override
	public void run() {
		// Esto es para detener el audio si se apresiona start o cualquier otro boton antes de su final
		while(true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}if(this.altoSound) {
				sound.stop();
			}
		}
	}
	
}
