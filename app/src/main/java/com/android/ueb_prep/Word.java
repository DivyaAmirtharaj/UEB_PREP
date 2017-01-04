package com.android.ueb_prep;

import java.util.ArrayList;
import java.util.List;

class Word {
	Coordinate mCoordinate;
    Boolean isFalling = true;
    private GameSurfaceView display;

	private Word(Coordinate a, GameSurfaceView display) {
		this.mCoordinate = a;
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


    List<Coordinate> wordCoordinates() {
		List<Coordinate> coordinateList = new ArrayList<Coordinate>();
		coordinateList.add(mCoordinate);
		return coordinateList;
	}

    void fall() {
        if (mCoordinate.y < dH()) {
            this.mCoordinate.y += 1;
            isFalling = true;
		} else {
			isFalling = true;
			mCoordinate.y=0;
			display.reachedBottom();
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



