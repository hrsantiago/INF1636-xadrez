package view;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import core.Game;

public class Window extends JFrame {

	private static final long serialVersionUID = -3739008754324139579L;

	public Window() {
		setupWindow();
		setupMenu();
		getContentPane().add(new BoardView());
	}
	
	private void setupWindow() {
		setSize(320, 320);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - getHeight()) / 2);
	    setLocation(x, y);
	}

	private void setupMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenuItem newAction = new JMenuItem("New");
		JMenuItem openAction = new JMenuItem("Open");
		JMenuItem saveAction = new JMenuItem("Save");
		JMenuItem exitAction = new JMenuItem("Exit");

		newAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) { onNewClicked(); }
		});
		openAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) { onOpenClicked(); }
		});
		saveAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) { onSaveClicked(); }
		});
		exitAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) { onExitClicked(); }
		});

		fileMenu.add(newAction);
		fileMenu.add(openAction);
		fileMenu.add(saveAction);
		fileMenu.add(exitAction);
		
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		
		JMenuItem undoAction = new JMenuItem("Undo");
		undoAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK));
		undoAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) { onUndoClicked(); }
		});
		
		editMenu.add(undoAction);
	}

	private void onNewClicked() {
		Game game = Game.getInstance();
		game.create();
	}

	private void onOpenClicked() {
		JFileChooser fc = new JFileChooser();
		
		FileFilter filter = new FileNameExtensionFilter("Chess file (*.chess)", "chess");
		fc.addChoosableFileFilter(filter);
		fc.setFileFilter(filter);
		
		int returnVal = fc.showOpenDialog(Window.this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				FileInputStream in = new FileInputStream(file);
				Game game = Game.getInstance();
				game.unserialize(in);
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void onSaveClicked() {
		JFileChooser fc = new JFileChooser();
		
		FileFilter filter = new FileNameExtensionFilter("Chess file (*.chess)", "chess");
		fc.addChoosableFileFilter(filter);
		fc.setFileFilter(filter);
		
		int returnVal = fc.showSaveDialog(Window.this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String fileName = file.getAbsolutePath();
			if(!fileName.endsWith(".chess"))
                file = new File(fileName + ".chess");
                
			try {
				FileOutputStream out = new FileOutputStream(file);
				Game game = Game.getInstance();
				game.serialize(out);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void onExitClicked() {
		System.exit(0);
	}
	
	private void onUndoClicked() {
		Game game = Game.getInstance();
		game.undo();
	}
}
