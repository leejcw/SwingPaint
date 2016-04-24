package csg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SwingPaint extends JFrame implements KeyListener, ActionListener {
    JTextArea displayArea;
    JTextField typingArea;
    static MainPanel mainPanel;
    static final String newline = System.getProperty("line.separator");

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
         
        //JButton button = new JButton("Clear");
        //button.addActionListener(this);
         
        typingArea = new JTextField(20);
        typingArea.addKeyListener(this);
         
        //Uncomment this if you wish to turn off focus
        //traversal.  The focus subsystem consumes
        //focus traversal keys, such as Tab and Shift Tab.
        //If you uncomment the following line of code, this
        //disables focus traversal and the Tab events will
        //become available to the key event listener.
        typingArea.setFocusTraversalKeysEnabled(false);
         
        //displayArea = new JTextArea();
        //displayArea.setEditable(false);
        //JScrollPane scrollPane = new JScrollPane(displayArea);
        //scrollPane.setPreferredSize(new Dimension(375, 125));
         
        getContentPane().add(typingArea, BorderLayout.PAGE_START);
        //vgetContentPane().add(scrollPane, BorderLayout.CENTER);
        // getContentPane().add(button, BorderLayout.PAGE_END);
    }
    
    public void keyTyped(KeyEvent e) {
	//        displayInfo(e, "KEY TYPED: ");
	if (!mainPanel.addShape(e.getKeyChar()))
	    mainPanel.setOp(e.getKeyChar());
        typingArea.setText("");
    }
     
    /** Handle the key pressed event from the text field. */
    public void keyPressed(KeyEvent e) {
	int keyCode = e.getKeyCode();
	switch (keyCode) { 
	case 8: // DEL
	    mainPanel.pop();
	    break;
	case 9: // TAB
	    mainPanel.next();
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
	// displayInfo(e, "KEY PRESSED: ");
    }
     
    /** Handle the key released event from the text field. */
    public void keyReleased(KeyEvent e) {
	//        displayInfo(e, "KEY RELEASED: ");
    }
     
    /** Handle the button click. */
    public void actionPerformed(ActionEvent e) {
        //Clear the text components.
	//        displayArea.setText("");
        typingArea.setText("");
         
        //Return the focus to the typing area.
        typingArea.requestFocusInWindow();
    }
     
    /*
     * We have to jump through some hoops to avoid
     * trying to print non-printing characters
     * such as Shift.  (Not only do they not print,
     * but if you put them in a String, the characters
     * afterward won't show up in the text area.)
     */
    private void displayInfo(KeyEvent e, String keyStatus){
         
        //You should only rely on the key char if the event
        //is a key typed event.
        int id = e.getID();
        String keyString;
        if (id == KeyEvent.KEY_TYPED) {
            char c = e.getKeyChar();
            keyString = "key character = '" + c + "'";
        } else {
            int keyCode = e.getKeyCode();
            keyString = "key code = " + keyCode
                    + " ("
		+ KeyEvent.getKeyText(keyCode)
		+ ")";
        }
         
        int modifiersEx = e.getModifiersEx();
        String modString = "extended modifiers = " + modifiersEx;
        String tmpString = KeyEvent.getModifiersExText(modifiersEx);
        if (tmpString.length() > 0) {
            modString += " (" + tmpString + ")";
        } else {
            modString += " (no extended modifiers)";
        }
         
        String actionString = "action key? ";
        if (e.isActionKey()) {
            actionString += "YES";
        } else {
            actionString += "NO";
        }
         
        String locationString = "key location: ";
        int location = e.getKeyLocation();
        if (location == KeyEvent.KEY_LOCATION_STANDARD) {
            locationString += "standard";
        } else if (location == KeyEvent.KEY_LOCATION_LEFT) {
            locationString += "left";
        } else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
            locationString += "right";
        } else if (location == KeyEvent.KEY_LOCATION_NUMPAD) {
            locationString += "numpad";
        } else { // (location == KeyEvent.KEY_LOCATION_UNKNOWN)
            locationString += "unknown";
        }
         
        /* displayArea.append(keyStatus + newline
                + "    " + keyString + newline
                + "    " + modString + newline
                + "    " + actionString + newline
			   + "    " + locationString + newline);
			   displayArea.setCaretPosition(displayArea.getDocument().getLength()); */
	System.out.println(keyStatus + newline
                + "    " + keyString + newline
                + "    " + modString + newline
                + "    " + actionString + newline
			   + "    " + locationString + newline);
    }
}