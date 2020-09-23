/* * Francisco Javier Ramos Velasco A01636425
 * Jose Ricardo Vanegas Castillo A01634956*/


import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Duck extends JPanel implements Runnable{
	private Image duck;
	private Image[] duckDirecciones;
	private int x,
					  y,
					  numero,
					  incremento,
					  speed = 4,
					  xPos,
					  yPos;
	
	private boolean numArreglo,
							  maxY,
							  minY,
							  iniX,
							  killed,
							  dead,
							  suelo,
							  otroPato;

	private Thread hilo;
	
	public Duck() {
		this.hilo =  new Thread(this);
		this.duckDirecciones();
		this.y  =(int) (Math.random()*300)+100;
		this.hilo.start();
	}
	
	//Dibuja al Pato
		public void dibujaPatoInicio(Graphics g) {
			if(!this.getDead()) {	
				this.dibujaPato(g);
			}
		}
		
		private void duckDirecciones() {
			this.duckDirecciones = new Image[4];
			this.duckDirecciones[0] =  new ImageIcon("images/rightUp.png").getImage();
			this.duckDirecciones[1] =  new ImageIcon("images/left.png").getImage();
			this.duckDirecciones[2] =  new ImageIcon("images/right.png").getImage();
			this.duckDirecciones[3] =  new ImageIcon("images/leftUp.png").getImage();
		}
		
		//Elegir Random inicio
		private void InicioAleatorio() {
			if(!this.numArreglo) {
				this.numero = (int) (Math.random()*4);
				System.out.println(this.numero);
				this.duck = new ImageIcon(this.duckDirecciones[this.numero]).getImage();
				if(!this.iniX) {
					this.x = (this.numero==0||this.numero==2)?0:750;
					this.setX();
				}
				this.setnumArreglo();
			}
		}
		
		
		//Mover
		private void mover() {
			this.maxMinY();
			//Right
			if(this.numero==0||this.numero==2) {
				this.cambioIAD();
			} //Left
			else if (this.numero==1||this.numero==3) {
				this.cambioDAI();
				}
		}
		
		private void izquierdaADerecha() {
			this.numero = 0;
			this.cambioIAD();
		}
		
		private void cambioIAD() {
			if(this.getMaxY()) {
				this.setImagen(0);
				this.numero=0;
				this.y-=10;
				//posicion "final del pato"
				this.setYPos(this.y);
			}else if(this.getMinY()) {
				this.setImagen(2);
				this.numero = 2;
				this.y+=10;
				//posicion final
				this.setYPos(this.y);
			}else {
				this.y+=10;
				//posicion final
				this.setYPos(this.y);
			}this.x+=5; 
			this.setXPos(this.x);
		}
		
		private void derechaAIzquierda() {
			this.numero=3;
			this.cambioDAI();
		}
		
		private void cambioDAI() {
			if(this.getMaxY()) {
				this.y-=10;
				this.setYPos(this.y);
				this.numero = 3;
				this.setImagen(3);
			}else if(this.getMinY()) {
				this.y+=10;
				this.setYPos(this.y);
				this.setImagen(1);
				this.numero =1;
			}else {
				this.y+=10;
				this.setYPos(this.y);
			}this.x-=5;
			this.setXPos(this.x);
		}
		
		
		//MaximosyMinimos
		private void maxMinY() {
			if(this.y<5) {
				this.setMinY();
			}
			
			if(this.y>400) {
				this.setMaxY();
			}
		}
		
		
		//Dibuja Pato paint
		private void dibujaPato(Graphics g) {
			this.InicioAleatorio();
			int x=0,y=0;
			Graphics2D g2d;
			g2d = (Graphics2D) g;
			if(this.numero==0 || this.numero==3) {
				x = 64;
				y = 66;
			}else {
				x = 68;
				y=64;
			}
			int mx = (this.incremento%4)*x;
			g2d.drawImage(this.duck, this.x,this.y,68+this.x,this.y+66,mx,0,mx+x,y,this);
			
		}
		
		//Getters y Setters
		
		public void  setImagen(int num) {
			this.duck = new ImageIcon(this.duckDirecciones[num]).getImage();
		}
		
		public boolean getOtroPato() {
			return this.otroPato;
		}
		
		public boolean getSuelo() {
			return this.suelo;
		}
		
		public boolean getKilled() {
			return this.killed;
		}
		
		public boolean getDead() {
			return this.dead;
		}
		
		public boolean getMinY() {
			return this.minY;
		}
		
		public boolean getMaxY() {
			return this.maxY;
		}
		
		public int getXPos() {
			return this.xPos;
		}
		public int getYPos() {
			return this.yPos;
		}
		public Duck getDuck() {
			return this;
		}
				
		public void velocidad(int kills) {
			this.speed = this.speed*kills;
		}
		
		public void setnumArreglo() {
			this.numArreglo =  !this.numArreglo;
		}
		
		public void setMinY() {
			this.minY = !this.minY;
			this.maxY = false;
		}
		
		public void setMaxY() {
			this.maxY = !this.maxY;
			this.minY = false;
		}
		
		public void setX() {
			this.iniX = !this.iniX;
		}
		public void setXPos(int x) {
			this.xPos=x;
		}
		public void setYPos(int y) {
			this.yPos=y;
		}
		
		public void setKilled() {
			this.killed=true;
		}
		
		public void setDead() {
			this.dead=!this.dead;
		}
		
		public void setSuelo() {
			this.suelo=!this.suelo;
		}
		
		public void setOtroPato() {
			this.otroPato = !this.otroPato;
		}
		
		
		
		@Override
		public void run() {
			while(!this.getDead()) {
				try {
					this.mover();
					this.incremento++;
					Thread.sleep(80-this.speed);
				}catch(InterruptedException e) {
					System.out.println("Error en el hilo 1");		
			}this.repaint();
			if(this.x>750) {
				this.derechaAIzquierda();
			}
			if(this.x<0) {
				this.izquierdaADerecha();
			}
		}
	}
}

