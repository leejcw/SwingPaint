package csg;

class Circle extends Ellipse {
    
    Circle(Op op) {
	super(op, 10, 10, 10, 10);
    }

    Circle(Op op, int x, int y, double r) {
	super(op, x, y, r, r);
    }

    public String toString() {
	return "Circle " + op.name() + " rad:" + aRad;
    }

}