/* Francisco Javier Ramos Velasco A01636425
 * Jose Ricardo Vanegas Castillo A01634956*/

import java.awt.CardLayout;

import javax.swing.JPanel;

public class Contenedor extends JPanel {
	protected CardLayout cl;

	public Contenedor() {
		super();

		Menu menu = new Menu(this);
		Game game = new Game();
		this.cl = new CardLayout();
		this.setLayout(this.cl);
		this.add(menu, "1");
		this.add(game, "2");

		this.cl.show(this, "1");

	}

}
