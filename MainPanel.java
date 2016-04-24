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
    int corner; // applicable only to Triangles and Quads

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
	corner = 1;
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
	} else if (sel instanceof Ellipse) {
	    Ellipse e = (Ellipse) sel;
	    e.x = x;
	    e.y = y;
	} else if (sel instanceof Triangle) {
	    Triangle t = (Triangle) sel;
	    double cx = (t.x1 + t.x2 + t.x3) / 3.0;
	    double cy = (t.y1 + t.y2 + t.y3) / 3.0;
	    t.x1 = x + (int) (t.x1 - cx);
	    t.y1 = y + (int) (t.y1 - cy);
	    t.x2 = x + (int) (t.x2 - cx);
	    t.y2 = y + (int) (t.y2 - cy);
	    t.x3 = x + (int) (t.x3 - cx);
	    t.y3 = y + (int) (t.y3 - cy);
	} else if (sel instanceof Quad) {
	    Quad q = (Quad) sel;
	    int cx = (q.leftX + q.rightX) / 2;
	    int cy = (q.leftY + q.rightY) / 2;
	    q.centerX = x + (q.centerX - cx);
	    q.centerY = y + (q.centerY - cy);
	    q.leftX = x + (q.leftX - cx);
	    q.leftY = y + (q.leftY - cy);
	    q.rightX = x + (q.rightX - cx);
	    q.rightY = y + (q.rightY - cy);
	    
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
	for (int x = 0; x < 400; x++) {
            for (int y = 0; y < 400; y++) {
		if (isInside(x, y)) {
		    g.setColor(Color.GRAY);
		    g.fillRect(x, y, 1, 1);
		} else {
		    g.setColor(Color.LIGHT_GRAY);
		    g.fillRect(x, y, 1, 1);
		}
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
	if (sel != null) sel.corner(g, corner);
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

    void corner() {
	if (sel instanceof Triangle || sel instanceof Quad) {
	    corner++;
	    if (corner > 3) corner = 1;
	}
	repaint();
    }

    void up() {
	if (sel instanceof Rectangle) {
	    Rectangle r = (Rectangle) sel;
	    r.y -= 5;
	    r.h += 10;
	    if (sel instanceof Square) {
		r.x -= 5;
		r.w = r.h;
	    }
	} else if (sel instanceof Ellipse) {
	    Ellipse e = (Ellipse) sel;
	    e.bRad += 5;
	    if (sel instanceof Circle) {
		e.aRad = e.bRad;
	    }
	} else if (sel instanceof Triangle) {
	    Triangle t = (Triangle) sel;
	    switch (corner) {
	    case 1:
		t.y1 -= 5;
		break;
	    case 2:
		t.y2 -= 5;
		break;
	    case 3:
		t.y3 -= 5;
		break;
	    default:
		break;
	    }
	} else if (sel instanceof Quad) {
	    Quad q = (Quad) sel;
	    switch (corner) {
	    case 1:
		q.centerY -= 5;
		break;
	    case 2:
		q.leftY -= 5;
		break;
	    case 3:
		q.rightY -= 5;
		break;
	    default:
		break;
	    }
	}
	repaint();
    }

    void down() {
	if (sel instanceof Rectangle) {
	    Rectangle r = (Rectangle) sel;
	    r.y += 5;
	    r.h -= 10;
	    if (r.h <= 0) r.h = 1;
	    if (sel instanceof Square) {
		r.x += 5;
		r.w = r.h;
	    }
	} else if (sel instanceof Ellipse) {
	    Ellipse e = (Ellipse) sel;
	    e.bRad -= 5;
	    if (e.bRad <= 0) e.bRad = 1;
	    if (sel instanceof Circle) {
		e.aRad = e.bRad;
	    }
	} else if (sel instanceof Triangle) {
	    Triangle t = (Triangle) sel;
	    switch (corner) {
	    case 1:
		t.y1 += 5;
		break;
	    case 2:
		t.y2 += 5;
		break;
	    case 3:
		t.y3 += 5;
		break;
	    default:
		break;
	    }
	} else if (sel instanceof Quad) {
	    Quad q = (Quad) sel;
	    switch (corner) {
	    case 1:
		q.centerY += 5;
		break;
	    case 2:
		q.leftY += 5;
		break;
	    case 3:
		q.rightY += 5;
		break;
	    default:
		break;
	    }
	}
	repaint();
    }

    void left() {
	if (sel instanceof Rectangle) {
	    Rectangle r = (Rectangle) sel;
	    r.x += 5;
	    r.w -= 10;
	    if (r.w <= 0) r.w = 1; 
	    if (sel instanceof Square) {
		r.y += 5;
		r.h = r.w;
	    }
	} else if (sel instanceof Ellipse) {
	    Ellipse e = (Ellipse) sel;
	    e.aRad -= 5;
	    if (e.aRad <= 0) e.aRad = 1;
	    if (sel instanceof Circle) {
		e.bRad = e.aRad;
	    }
	} else if (sel instanceof Triangle) {
	    Triangle t = (Triangle) sel;
	    switch (corner) {
	    case 1:
		t.x1 -= 5;
		break;
	    case 2:
		t.x2 -= 5;
		break;
	    case 3:
		t.x3 -= 5;
		break;
	    default:
		break;
	    }
	} else if (sel instanceof Triangle) {
	    Triangle t = (Triangle) sel;
	    switch (corner) {
	    case 1:
		t.x1 -= 5;
		break;
	    case 2:
		t.x2 -= 5;
		break;
	    case 3:
		t.x3 -= 5;
		break;
	    default:
		break;
	    }
	} else if (sel instanceof Quad) {
	    Quad q = (Quad) sel;
	    switch (corner) {
	    case 1:
		q.centerX -= 5;
		break;
	    case 2:
		q.leftX -= 5;
		break;
	    case 3:
		q.rightX -= 5;
		break;
	    default:
		break;
	    }
	}
	repaint();
    }

    void right() {
	if (sel instanceof Rectangle) {
	    Rectangle r = (Rectangle) sel;
	    r.x -= 5;
	    r.w += 10;
	    if (sel instanceof Square) {
		r.y -= 5;
		r.h = r.w;
	    }
	} else if (sel instanceof Ellipse) {
	    Ellipse e = (Ellipse) sel;
	    e.aRad += 5;
	    if (sel instanceof Circle) {
		e.bRad = e.aRad;
	    }
	} else if (sel instanceof Triangle) {
	    Triangle t = (Triangle) sel;
	    switch (corner) {
	    case 1:
		t.x1 += 5;
		break;
	    case 2:
		t.x2 += 5;
		break;
	    case 3:
		t.x3 += 5;
		break;
	    default:
		break;
	    }
	} else if (sel instanceof Quad) {
	    Quad q = (Quad) sel;
	    switch (corner) {
	    case 1:
		q.centerX += 5;
		break;
	    case 2:
		q.leftX += 5;
		break;
	    case 3:
		q.rightX += 5;
		break;
	    default:
		break;
	    }
	}
	repaint();
    }
}