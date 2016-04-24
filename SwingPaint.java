package csg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SwingPaint extends JFrame implements KeyListener, ActionListener {
    JTextArea displayArea;
    JTextField typingArea;
    static MainPanel mainPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
		public void run() {
		    createAndShowGUI(); 
		}
	    });
    }

    public SwingPaint(String name) {
	super(name);
    }

    private static void createAndShowGUI() {
        SwingPaint f = new SwingPaint("CSG Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setResizable(false);
	mainPanel = new MainPanel();
        f.add(mainPanel);
	f.addComponentsToPane();
        f.pack();
        f.setVisible(true);
    } 

    private void addComponentsToPane() {
         
        typingArea = new JTextField(20);
        typingArea.addKeyListener(this);
        typingArea.setFocusTraversalKeysEnabled(false);
        getContentPane().add(typingArea, BorderLayout.PAGE_START);
    }
    
    public void keyTyped(KeyEvent e) {
	if (!mainPanel.addShape(e.getKeyChar()))
	    mainPanel.setOp(e.getKeyChar());
        typingArea.setText("");
    }
     
    public void keyPressed(KeyEvent e) {
	int keyCode = e.getKeyCode();
	switch (keyCode) { 
	case 8: // DEL
	    mainPanel.pop();
	    break;
	case 9: // TAB
	    mainPanel.next();
	    break;
	case 32: // space
	    mainPanel.corner();
	    break;
        case KeyEvent.VK_UP:
            mainPanel.up();
            break;
        case KeyEvent.VK_DOWN:
            mainPanel.down(); 
            break;
        case KeyEvent.VK_LEFT:
            mainPanel.left();
            break;
        case KeyEvent.VK_RIGHT:
            mainPanel.right();
            break;
	}
    }
    
    public void keyReleased(KeyEvent e) {
    }
     
    public void actionPerformed(ActionEvent e) {
        typingArea.setText("");
        typingArea.requestFocusInWindow();
    }
}