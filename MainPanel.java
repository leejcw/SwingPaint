package csg;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MainPanel extends JPanel {

    // represent shapes in the order of z value; first shape is lowest
    LinkedList<Geometry> geos;
    int idx;

    private Geometry.Op op;
    private Geometry sel;

    public MainPanel() {

        setBorder(BorderFactory.createLineBorder(Color.black));
	setOpaque(true);

        addMouseListener(new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
		    moveShape(e.getX(),e.getY());
		}
	    });

        addMouseMotionListener(new MouseAdapter() {
		public void mouseDragged(MouseEvent e) {
		    moveShape(e.getX(),e.getY());
		}
	    });
        
	geos = new LinkedList<Geometry>();
	idx = -1;
	op = Geometry.Op.AND;
    }

    void setOp(char c) {
	switch (c) {
	case 'a':
	    this.op = Geometry.Op.AND;
	    break;
	case 'o':
	    this.op = Geometry.Op.OR;
	    break;
	case 'n':
	    this.op = Geometry.Op.NOT;
	    break;
	case 'x':
	    this.op = Geometry.Op.XOR;
	    break;
	default:
	    break;
	}
    }

    boolean addShape(char c) {
	switch (c) {
	case 'r' : 
	    geos.add(new Rectangle(op));
	    break;
	case 's':
	    geos.add(new Square(op));
	    break;
	case 'q':
	    geos.add(new Quad(op));
	    break;
	case 'e':
	    geos.add(new Ellipse(op));
	    break;
	case 'c':
	    geos.add(new Circle(op));
	    break;
	case 't':
	    geos.add(new Triangle(op));
	    break;
	default:
	    return false;
	}

	for (Geometry geo : geos) {
	    System.out.println(geo);
	}
	
	repaint();
	sel = geos.getLast();
	idx = geos.size() - 1;
	return true;
    }
    
    void pop() {
	if (geos.size() > 0) {
	    geos.remove(idx);
	}
	repaint();
	if (idx == geos.size()) {
	    idx = geos.size() - 1;
	}
	if (idx == -1) {
	    sel = null;
	    return;
	}
	sel = geos.get(idx);
    }
    
    private void moveShape(int x, int y) {
	if (sel instanceof Rectangle) {
	    Rectangle r = (Rectangle) sel;
	    r.x = x - r.w / 2;
	    r.y = y - r.h / 2;
	}
	repaint();
    }
    

    public Dimension getPreferredSize() {
        return new Dimension(400,400);
    }

    private boolean isInside(int x, int y) {
	boolean inside = true;
	for (Geometry g : geos) {
	    boolean hit = g.isHit(x, y);
	    switch (g.op) {
	    case AND:
		inside = inside && hit;
		break;
	    case OR:
		inside = inside || hit;
		break;
	    case NOT:
		inside = inside && !hit;
		break;
	    case XOR:
		inside = inside ^ hit;
		break;
	    }
	}
	return inside;
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
	//        g.setColor(Color.RED);
	//        g.fillRect(squareX,squareY,squareW,squareH);
	// g.setColor(Color.BLACK);
        // g.drawRect(squareX,squareY,squareW,squareH);
	for (int x = 0; x < 400; x++) {
            for (int y = 0; y < 400; y++) {
		if (isInside(x, y)) {
		    g.setColor(Color.GRAY);
		    g.fillRect(x, y, 1, 1);
		} else {
		    g.setColor(Color.LIGHT_GRAY);
		    g.fillRect(x, y, 1, 1);
		}
		    //		    canvas.setRGB(x, y, 0x00AA3300);
		    //		canvas.setRGB(x, y, 0x00FF0000);
            }
        }
	for (Geometry geo : geos) {
	    Graphics2D g2 = (Graphics2D) g;
	    if (geo == sel) {
		g2.setStroke(new BasicStroke(3));
	    } else {
		g2.setStroke(new BasicStroke(1));
	    }
	    geo.draw(g);
	}
	// Collections.reverse(geos);
    }

    void next() {
	idx++;
	if (idx == geos.size()) idx = 0;
	if (geos.size() == 0) {
	    idx = -1;
	    return;
	}
	sel = geos.get(idx);
	repaint();
    }

    void up() {
	if (sel instanceof Rectangle) {
	    Rectangle r = (Rectangle) sel;
	    r.y -= 5;
	    r.h += 10;
	}
	repaint();
    }

    void down() {
	if (sel instanceof Rectangle) {
	    Rectangle r = (Rectangle) sel;
	    r.y += 5;
	    r.h -= 10;
	    if (r.h <= 0) r.h = 1;
	}
	repaint();
    }

    void left() {
	if (sel instanceof Rectangle) {
	    Rectangle r = (Rectangle) sel;
	    r.x += 5;
	    r.w -= 10;
	    if (r.w <= 0) r.w = 1; 
	}
	repaint();
    }

    void right() {
	if (sel instanceof Rectangle) {
	    Rectangle r = (Rectangle) sel;
	    r.x -= 5;
	    r.w += 10;
	}
	repaint();
    }
}