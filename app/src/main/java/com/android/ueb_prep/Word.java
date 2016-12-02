package com.android.ueb_prep;

import java.util.ArrayList;
import java.util.List;

public class Word {
	Coordinate mCoordinate;
	public int down, right, w,h;
	GameSurfaceView display;
	Boolean isFalling = true, isBottom=false;

	private Word(Coordinate a, GameSurfaceView display) {
		this.mCoordinate = a;
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
		return w;
	}



	public static Word l(GameSurfaceView display) {
		return new Word(new Coordinate(540,0),
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



