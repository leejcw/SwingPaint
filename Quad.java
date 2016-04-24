package csg;

import javax.swing.*;
import java.awt.*;

class Quad extends Geometry {
    int centerX, centerY;
    int leftX, leftY;
    int rightX, rightY;

    Quad(Op op) {
	super(op);
	centerX = centerY = 10;
	leftX = 15; leftY = 20;
	rightX = 30; rightY = 10;
    }
							
    // rectangle inscribed by these points
    Quad(Op op, int x1, int y1, int x2, int y2) {
	super(op);
	centerX = x1;
	centerY = y1;
	leftX = x1;
	leftY = y2;
	rightX = x2;
	rightY = y1;
    }
    
    boolean isHit(int x, int y) {
	int otherX = rightX + leftX - centerX;
	int otherY = rightY + leftY - centerY;
	Triangle t1 = new Triangle(op, leftX, leftY, centerX, centerY, rightX, rightY);
	Triangle t2 = new Triangle(op, leftX, leftY, otherX, otherY, rightX, rightY);
	return t1.isHit(x, y) || t2.isHit(x, y);
    }

    public String toString() {
	return "Quad " + op.name();
    }

    void draw(Graphics g) {
	g.setColor(color);
	g.drawLine(centerX, centerY, leftX, leftY);
	g.drawLine(centerX, centerY, rightX, rightY);
	int otherX = rightX + leftX - centerX;
	int otherY = rightY + leftY - centerY;
	g.drawLine(leftX, leftY, otherX, otherY);
	g.drawLine(rightX, rightY, otherX, otherY);
    }

    void corner(Graphics g, int corner) {
	g.setColor(Color.WHITE);
	switch (corner) {
	case 1:
	    g.fillRect(centerX - 1, centerY - 1, 2, 2);
	    break;
	case 2:
	    g.fillRect(leftX - 1, leftY - 1, 2, 2);
	    break;
	case 3:
	    g.fillRect(rightX - 1, rightY - 1, 2, 2);
	    break;
	default:
	    break;
	}
    }

}