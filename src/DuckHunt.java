/* Francisco Javier Ramos Velasco A01636425
 * Jose Ricardo Vanegas Castillo A01634956*/


import javax.swing.JFrame;

import javax.swing.SwingUtilities;

public class DuckHunt extends JFrame {

	public DuckHunt() {
		super("DuckHunt");
		Contenedor contenedor = new Contenedor();
		this.add(contenedor);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DuckHunt dh = new DuckHunt();
			}
		});

	}
}
