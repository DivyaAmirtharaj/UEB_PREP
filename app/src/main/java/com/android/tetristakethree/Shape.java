package com.android.tetristakethree;

import java.util.ArrayList;
import java.util.List;

public class Shape {
	Coordinate a;
	public Piece id;
	public int down, right, rotate, w,h;
	GameSurfaceView display;
	Boolean isFalling = true;

	private Shape(Coordinate a, Piece id, GameSurfaceView display) {
		this.a = a;
		this.id = id;
		this.down = -2;
		this.right = 0;
		this.display = display;
	}

	private int dH() {
		int dh = display.displayHeight()-300;
		h(dh);
		return dh;
	}

	private int h(int dH) {
		int h = dH / 22;
		return h;
	}

	private int dW() {
		int dw = (display.displayWidth()/2)-100;
		w(dw);
		return dw;
	}

	private int w(int dW) {
		// int columns = dW / h;
		// int w = dW / columns;
		return w;
	}



	public static Shape l(GameSurfaceView display) {
		return new Shape(new Coordinate(400,0),
				Piece.L,
				display);
	}


	public List<Coordinate> shapeCoordinates() {
		List<Coordinate> coords = new ArrayList<Coordinate>();
		coords.add(a);
		return coords;
	}

	public void fall() {
		if (a.y < dH()) {
			this.a.y += 1;
			isFalling = true;
			down++;
		} else {
			isFalling = false;

		}
	}

	public boolean Falling() {
		boolean fall = isFalling;
		return isFalling;
	}

	public void right() {
		if (a.x < dW() && a.y < dH()) {
			this.a.x += 100;
			right ++;
		}
	}

	public void stop() {
		a.y = dH();
	}

	public void restart() { a.y = 0; }
}



