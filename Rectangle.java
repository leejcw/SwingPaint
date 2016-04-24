package csg;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

class Rectangle extends Geometry {
    protected int x, y;
    protected int w, h;

    Rectangle(Op op) {
	super(op);
	x = y = 30;
	w = 40; h = 30;
    }

    Rectangle(Op op, int x1, int y1, int x2, int y2) {
	super(op);
	x = Math.min(x1, x2);
	y = Math.min(y1, y1);
	w = Math.abs(x1 - x2);
	h = Math.abs(y1 - y2);
    }

    boolean isHit(int x, int y) {
	return x > this.x && x < this.x + w && y > this.y && y < this.y + h;
    }
    
    public String toString() {
	return "Rectangle " + op.name() + " x:" + x + " y:" + y + " w:" + w + " h:" + h;
    }

    void draw(Graphics g) {
	g.setColor(color);
	g.drawRect(x, y, w, h);
    }

    void corner(Graphics g, int c) {
    }

}