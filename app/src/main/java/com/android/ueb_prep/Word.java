package com.android.ueb_prep;

import java.util.ArrayList;
import java.util.List;

class Word {
    Coordinate mCoordinate;
    Boolean isFalling = true;
    private Boolean isBottom = false;
    private int down, right, w, h;
    private GameSurfaceView display;

	private Word(Coordinate a, GameSurfaceView display) {
		this.mCoordinate = a;
		this.down = -2;
		this.right = 0;
		this.display = display;
	}

    static Word l(GameSurfaceView display) {
        return new Word(new Coordinate(540, 0),
                display);
    }

	private int dH() {
		int dh = display.displayHeight()-300;
		h(dh);
		return dh;
	}

	private int h(int dH) {
        int h;
        h = dH / 22;
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

    List<Coordinate> wordCoordinates() {
        List<Coordinate> coords = new ArrayList<Coordinate>();
        coords.add(mCoordinate);
        return coords;
    }

    void fall() {
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

    void restart() {
        mCoordinate.y = 0;
		isFalling = true;
	}
}



