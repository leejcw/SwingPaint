package csg;

class Square extends Rectangle {

    Square(Op op) {
	super(op);
	h = 20;
    }

    Square(Op op, int x1, int y1, int x2, int y2) {
	super(op, x1, y1, x2, y2);
	if (w < h) h = w;
	else if (h < w) w = h;
    }

    public String toString() {
	return "Square " + op.name() + " x:" + x + " y:" + y + " s:" + w;
    }
}