package csg;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

class Ellipse extends Geometry {
    double aRad;
    double bRad;
    int x, y;
    double rotation;

    Ellipse(Op op) {
	super(op);
	aRad = 20;
	bRad = 10;
	x = 10;
	y = 5;
	rotation = 0;
    }

    Ellipse(Op op, int x, int y, double aRad, double bRad) {
	super(op);
	this.aRad = aRad;
	this.bRad = bRad;
	this.x = x;
	this.y = y;
	rotation = 0;
    }

    boolean isHit(int x, int y) {
	return false;
    }

    public String toString() {
	return "Ellipse " + op.name() + " a:" + aRad + " b:" + bRad;
    }

    void draw(Graphics g) {
	g.setColor(color);
	Graphics2D g2 = (Graphics2D) g;
	g2.draw(new Ellipse2D.Double(x, y, 2 * aRad, 2 * bRad));
    }
}