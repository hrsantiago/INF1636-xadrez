package view;

import javax.swing.JFrame;

public class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3739008754324139579L;

	public Window() {
		setSize(480, 320);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(new BoardView());
	}
}
