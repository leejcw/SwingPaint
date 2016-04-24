package csg;

import javax.swing.*;
import java.awt.*;

class Quad extends Geometry {
    int centerX, centerY;
    int leftX, leftY;
    int rightX, rightY;

    Quad(Op op) {
	super(op);
	centerX = centerY = 0;
	leftX = 0; leftY = 10;
	rightX = 20; rightY = 0;
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
	return false;
    }

    public String toString() {
	return "Quad " + op.name();
    }

    void draw(Graphics g) {
	System.out.println("Quad draw todo");
    }

}