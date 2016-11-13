package com.android.tetristakethree;

import java.util.ArrayList;
import java.util.List;

public class Shape {
	Coordinate mCoordinate;
	public Piece id;
	public int down, right, rotate, w,h;
	GameSurfaceView display;
	Boolean isFalling = true, isBottom=false;

	private Shape(Coordinate a, Piece id, GameSurfaceView display) {
		this.mCoordinate = a;
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
		return new Shape(new Coordinate(540,0),
				Piece.L,
				display);
	}


	public List<Coordinate> shapeCoordinates() {
		List<Coordinate> coords = new ArrayList<Coordinate>();
		coords.add(mCoordinate);
		return coords;
	}

	public void fall() {
		if (mCoordinate.y < dH()) {
			this.mCoordinate.y += 1;
			isFalling = true;
			down++;
		} else {
			isFalling = true;
			mCoordinate.y=0;
			display.nextQuestion();
			isBottom = true;

		}
	}

	public boolean Bottom(){
		return isBottom;
	}

	public boolean Falling() {
		boolean fall = isFalling;
		return isFalling;
	}

	public void right() {
		if (mCoordinate.x < dW() && mCoordinate.y < dH()) {
			this.mCoordinate.x += 100;
			right ++;
		}
	}

	public void stop() {
		mCoordinate.y = dH();
	}

	public void restart() {
		mCoordinate.y = 0;
		isFalling = true;
	}
}



