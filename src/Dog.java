/* Francisco Javier Ramos Velasco A01636425
 * Jose Ricardo Vanegas Castillo A01634956*/


import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Font;

public class Dog extends JPanel implements Runnable{
	private Image 	dog;
	private Thread 	hilo;
	private boolean bandera,
					caminando,
					olfato,
					encontrado,
					animFin,
					saltoYAterrizo,
					sonidoAlto;

	private int incremento=0,
					  x=0,
					  xSalto=0,
					  ySalto=0;
					 
	
	private AudioClip audioCaminando,
					  audioLadrando;
	
	public Dog() {
		this.hilo =  new Thread(this);
		this.dog =  new ImageIcon("images/caminando.png").getImage();
		this.sonido();
		this.hilo.start();		
	}
	
	public void dibujaGetReady(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 48));
		if(this.getAnimFin()) {
			g.drawString("!Get Ready¡", 280, 300);
		}
		
	}
	
	public void dibujaPerro(Graphics g) {
		this.perroCaminando(g);
		
		if(this.getCaminando()) {
			this.perroOlfateando(g);
		}
		
		if(this.getOlfato()) {
			this.perroEncontrado(g);
		}
		
		if(this.getEncontrado()) {
			this.x=900;
			this.perroSaltandoYCayendo(g);
		}
	}
	
	private void perroCaminando(Graphics g) {
		Graphics2D g2d;
		g2d = (Graphics2D) g;
		int mx = (incremento%4)*110;
		g2d.drawImage(this.dog, x, 400, x+110, 500, mx, 10, mx+110, 110, this);
	}
	
	private void perroOlfateando(Graphics g) {
		Graphics2D g2d;
		g2d = (Graphics2D) g;
		int mx = (incremento%2)*110;
		this.dog = new ImageIcon("images/olfato.png").getImage();
		g2d.drawImage(this.dog, x, 400, x+110, 500,mx+110,10,mx+110,110, this);	
	}
	
	private void perroEncontrado(Graphics g) {
		Graphics2D g2d;
		g2d = (Graphics2D) g;
		this.dog = new ImageIcon("images/miradaArriba.png").getImage();
		g2d.drawImage(this.dog, x, 400, x+110, 500,0,10,110,110, this);
		
	}
	
	private void perroSaltandoYCayendo(Graphics g) {
		Graphics2D g2d;
		g2d = (Graphics2D) g; 
		int mx = (incremento%2)*80;
		Image img  =  new ImageIcon("images/saltoAterrizo.png").getImage();
		g2d.drawImage(img, 320+this.xSalto, this.ySalto+400, 400+this.xSalto, this.ySalto+492,mx,0,mx+80,92, this);
	
	}
	
	private void sonido() {
		this.audioCaminando = java.applet.Applet.newAudioClip(getClass().getResource("levelStart.mid"));
		this.audioLadrando = java.applet.Applet.newAudioClip(getClass().getResource("dogbrk.wav"));
		
	}
	
	//Getters
	
	public boolean getCaminando() {
		return this.caminando;
	}
	
	public boolean getOlfato() {
		return this.olfato;
	}
	
	public boolean getEncontrado() {
		return this.encontrado;
	}
	
	public boolean getSaltaYAterrizo() {
		return this.saltoYAterrizo;
	}
	public boolean getAnimFin() {
		return this.animFin;
	}
	public Dog getDog() {
		return this;
	}
	//Setters
	public void setCaminando() {
		this.caminando = !this.caminando;
	}
	
	public void setOlfato() {
		this.olfato = !this.olfato;
	}
	
	public void setEncontrado() {
		this.encontrado = !this.encontrado;
	}
	
	public void setSaltaYAterrizo() {
		this.saltoYAterrizo = !this.saltoYAterrizo;
	}
	public void setAnimFin() {
		this.animFin=true;
	}
	
	
	
	@Override
	public void run() {
		while(!this.getCaminando()&&!this.sonidoAlto) {
			try {
				Thread.sleep(4500);
				this.sonidoAlto=true;
			}catch(Exception e) {
				System.out.println("Error en el hilo sonido");
			}
			if(this.sonidoAlto) {
				this.audioCaminando.play();
			}
			
			while(!this.getCaminando()) {
				try {
					Thread.sleep(150);
				}catch(InterruptedException e) {
					System.out.println("Error en el hilo");
				}if (!(this.incremento>32)) {
					this.incremento++;
					this.x+=9;
				}else {
					this.incremento = 0;
					this.setCaminando();
				}this.repaint();
			}
				
			//Segundo Sprite (Olfato)
			while(!this.getOlfato()) {
				try {
					Thread.sleep(450);
				}catch(InterruptedException e) {
					System.out.println("Error en el hilo 2");
				}if(!(this.incremento>0)) {
					this.incremento++;
				}else {
					this.incremento = 0;
					this.setOlfato();
				}this.repaint();
			}
			
				
			//Tercer Sprite (Encontrado)
			while(!this.getEncontrado()) {
				try {
					Thread.sleep(500);
				}catch(InterruptedException e) {
					System.out.println("Error en el hilo 3");
				}this.setEncontrado();
				this.audioLadrando.play();
			}
				
			//Cuarto Sprite (Salta y Cae)
			//Salto
			while(!this.getSaltaYAterrizo()&&!this.bandera) {
				try {
					Thread.sleep(20);
				}catch(InterruptedException e) {
					System.out.println("Error en el hilo 4");
				}
				this.xSalto+=2;
				this.ySalto-=3;
				if(this.ySalto<-120) {
					this.incremento++;
					this.bandera = true;
				}
			}
				
			//Cayendo
			while(!this.getSaltaYAterrizo()&&this.bandera) {
				try {
					Thread.sleep(50);
				}catch(InterruptedException e) {
					System.out.println("Error en el hilo 4");
				}
				this.xSalto+=2;
				this.ySalto+=3;
				if(this.ySalto>-80) {
					this.setSaltaYAterrizo();
					this.xSalto = 900;
					this.ySalto  = 450; 
				}else {
					this.sonidoAlto = true;
				}
				
			}this.setAnimFin();
		}
	}
}