package csg;

import java.awt.*;

class Triangle extends Geometry {

    int x1, y1, x2, y2, x3, y3;

    Triangle(Op op) {
	super(op);
	x1 = 40;
	y1 = 20;
	x2 = 20;
	y2 = 40;
	x3 = 60;
	y3 = 40;
    }

    Triangle(Op op, int x1, int y1, int x2, int y2, int x3, int y3) {
	super(op);
	this.x1 = x1;
	this.y1 = y1;
	this.x2 = x2;
	this.y2 = y2;
	this.x3 = x3;
	this.y3 = y3;
    }

    boolean isHit(int x, int y) {
	boolean b0, b1, b2;
	b0 = sign(x, y, x1, y1, x2, y2) < 0;
	b1 = sign(x, y, x2, y2, x3, y3) < 0;
	b2 = sign(x, y, x3, y3, x1, y1) < 0;
	return (b0 == b1) && (b1 == b2);
    }

    public String toString() {
	return "Triangle " + op.name();
    }

    void draw(Graphics g) {
	g.setColor(color);
	g.drawLine(x1, y1, x2, y2);
	g.drawLine(x2, y2, x3, y3);
	g.drawLine(x3, y3, x1, y1);
    }

    private double sign(int px1, int py1, int px2, int py2, int px3, int py3) {
	return (px1 - px3) * (py2 - py3) - (px2 - px3) * (py1 - py3);
    }

    void corner(Graphics g, int corner) {
	g.setColor(Color.WHITE);
	switch (corner) {
	case 1:
	    g.fillRect(x1 - 1, y1 - 1, 2, 2);
	    break;
	case 2:
	    g.fillRect(x2 - 1, y2 - 1, 2, 2);
	    break;
	case 3:
	    g.fillRect(x3 - 1, y3 - 1, 2, 2);
	    break;
	default:
	    break;
	}
    }

}