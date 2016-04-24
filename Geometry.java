package csg;

import javax.swing.*;
import java.awt.*;

abstract class Geometry {

    public enum Op {
	AND, OR, NOT, XOR
	    }

    protected Color color;
    protected Op op;

    Geometry(Op op) {
	this.op = op;
	switch (op) {
	case AND:
	    color = Color.CYAN;
	    break;
	case OR:
	    color = Color.GREEN;
	    break;
	case NOT:
	    color = Color.RED;
	    break;
	case XOR:
	    color = Color.YELLOW;
	    break;
	default:
	    color = Color.LIGHT_GRAY;
	}
    }

    abstract boolean isHit(int x, int y);

    abstract void draw(Graphics g);
    
}