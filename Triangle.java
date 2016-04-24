package csg;

import java.awt.*;

class Triangle extends Geometry {

    int x1, y1, x2, y2, x3, y3;

    Triangle(Op op) {
	super(op);
    }

    boolean isHit(int x, int y) {
	return false;
    }

    public String toString() {
	return "Triangle " + op.name();
    }

    void draw(Graphics g) {
	System.out.println("Triangle draw todo");
    }
}