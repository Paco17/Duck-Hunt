/* Francisco Javier Ramos Velasco A01636425
 * Jose Ricardo Vanegas Castillo A01634956*/


import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

public class Game extends JPanel implements MouseListener{
	private Image fondo,
						   miss,
						   ducky;
	
	private  Dog perro;
	private  Duck duck;
	
	private int score,
					 killCount,
					 missShot;
	
	private AudioClip audioDisparo,
								audioFin;
	
	private int[] topScores;
	
	private boolean gameOver,
							  fin;
							  

	public Game() {
		super();
		this.setPreferredSize(new Dimension(800, 600));	
		this.setBackground(Color.decode("#33CCFF"));
		this.fondo =  new ImageIcon("images/Escenario.png").getImage();
		this.miss =  new ImageIcon("images/cruz.png").getImage();
		this.ducky = new ImageIcon("images/redDuck.png").getImage();
		this.audios();
		this.perro =  new Dog();
		this.perro= this.perro.getDog();
		
		//Duck
		this.duck =  new Duck();
		this.duck = this.duck.getDuck();
		
		this.missShot=0;
		this.score=1000;
		this.addMouseListener(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.fondo, 0, 0, this.getWidth(), this.getHeight(), this);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.BOLD,24));
		g.drawImage(this.ducky, 5, 5, 25, 25, this);
		g.drawString("    x   "+   this.getKillCount(), 20, 20);
		this.pintaAlPerro(g);
		if(this.perro.getAnimFin()) {
			this.duck.dibujaPatoInicio(g);
			this.pintaCruz(g);
			if(this.duck.getKilled()&&!this.gameOver) {
				Duck newDuck = new Duck();
				this.duck =  newDuck;
				this.duck.velocidad(this.killCount);
				newDuck.dibujaPatoInicio(g);	
			}
			
			//Dice el score
			if(this.gameOver) {
				if(!this.fin) {
					this.audioFin.play();
					this.fin=true;
				}
				g.setColor(Color.white);
				g.setFont(new Font("Arial", Font.BOLD, 48));
				g.drawString("GAME OVER", 275, 275);
				g.drawString("Score:  "+this.getScore(), 275, 325);	
				
			}
		}
		this.repaint();
	}
	
	private void audios() {
		this.audioDisparo = java.applet.Applet.newAudioClip(getClass().getResource("shot.wav"));
		this.audioFin = java.applet.Applet.newAudioClip(getClass().getResource("levelComplete.mid"));
	}
	
	private void pintaAlPerro(Graphics g) {
		this.perro.dibujaPerro(g);
	}
	
	private void pintaCruz(Graphics g) {
		int x=600;
		for(int i=0;i<this.missShot;i++) {
			g.drawImage(this.miss, x=x+55, 0, 50, 50, this);
		}
	}
	
	
	public int getScore() {
		return this.score*this.getKillCount();
	}
	
	public int getKillCount() {
		return killCount;
	}
	
	public void setKillCount() {
		this.killCount++;
	}
	
	@Override
	public void mouseClicked(MouseEvent evt) {	
	}

	@Override
	public void mousePressed(MouseEvent evt) {
		if(evt.getX()>=this.duck.getXPos() && evt.getX()<=(this.duck.getXPos()+50) && evt.getY()>=this.duck.getYPos() &&  evt.getY()<=(this.duck.getYPos()+50)&&!this.gameOver) {
			System.out.println("Le diste al pato");
			this.setKillCount();
			this.duck.setKilled();
			System.out.println(this.getKillCount());
			this.duck.setDead();
			this.audioDisparo.play();
		}else {
			System.out.println("Fallaste");
			this.missShot++;
			this.audioDisparo.play();
			if(this.missShot==3) {
				this.gameOver=true;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		// TODO Auto-generated method stu	
	}

	@Override
	public void mouseEntered(MouseEvent evt) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent evt) {
		// TODO Auto-generated method stub
		
	}
}
