package com.android.ueb_prep;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameSurfaceView extends SurfaceView implements Runnable {
	public GameState mGameState;
	SurfaceHolder mSurfaceHolder;
	volatile boolean running = false;
	Thread thread = null;
	int w, h;
	int y = 0;
	int x = 0;
	int squareSize = 95;
	String compareDeleteCoordinateString;
	List<Coordinate> mCoordinateList;
	String[] questionList;
	String currQuestion;
	private Paint backgroundPaint;

	public GameSurfaceView(Context context) {
		super(context);
		mSurfaceHolder = getHolder();
		
		backgroundPaint = new Paint();
		backgroundPaint.setColor(Color.WHITE);
		
		displayHeight();
		displayWidth();
		
		mGameState = new GameState(this);

		String questions = getResources().getString(R.string.questions);
		questionList = questions.toUpperCase().split(",");
		nextQuestion();
	}
	
	public void onResumeGameSurfaceView() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void onPauseGameSurfaceView() {
		boolean retry = true;
		running = false;
		while(retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public final int displayHeight() {
		DisplayMetrics d = this.getResources().getDisplayMetrics();
		int screenHeight;
		screenHeight = d.heightPixels / 2 + 50;
		return screenHeight;
	}

	public final int displayWidth() {
		DisplayMetrics d = this.getResources().getDisplayMetrics();
		int screenWidth;
		screenWidth = d.widthPixels;
		return screenWidth;
	}
	
	private int h(int dH) {
		int h;
		h = dH / 22;
		return h;
	}
	
	private int w(int dW) {
		int w;
		w = dW / 13;
		return w;
	}
	
	@Override
	public void run() {
		while (running) {
			if (mSurfaceHolder.getSurface().isValid()) {
				Canvas canvas = mSurfaceHolder.lockCanvas();
				
				int w = canvas.getWidth();
				int h = canvas.getHeight();
				canvas.drawRect(0,0,w,h, backgroundPaint);

				List<Word> gameWords = mGameState.getWords();
				
				for (int i = 0; i < 1; i++ ) {
					Word word = gameWords.get(i);
					
					Paint paint = new Paint();

					List<Coordinate> coordinatesToDelete = mGameState.deleteThisRow();
					mCoordinateList = word.wordCoordinates();
						
					for (int k = 0; k < coordinatesToDelete.size(); k++) {
						int[] compareCoordinateToDelete = {coordinatesToDelete.get(k).x, coordinatesToDelete.get(k).y};
						compareDeleteCoordinateString = Arrays.toString(compareCoordinateToDelete);

						for (int j = 0; j < mCoordinateList.size(); j++) {

							int[] compareCoordinate = {mCoordinateList.get(j).x, mCoordinateList.get(j).y};
							String compareCoordinateString = Arrays.toString(compareCoordinate);

							if (compareCoordinateString.equals(compareDeleteCoordinateString)) {
								mCoordinateList.remove(j);
							}
						}
					}
					int k;
					for (k = 0; k < mCoordinateList.size(); k++) {
						Rect rect = new Rect(mCoordinateList.get(k).x, mCoordinateList.get(k).y, mCoordinateList.get(k).x + squareSize, mCoordinateList.get(k).y + squareSize);
						paint.setTextSize(60);
						paint.setColor(Color.BLACK);
						canvas.drawText(CurrentQuestion(), mCoordinateList.get(k).x, mCoordinateList.get(k).y, paint);
					}
				}

				mSurfaceHolder.unlockCanvasAndPost(canvas);

				try {
					Thread.sleep(5); //Give the speed here
					mGameState.fallingWord.fall();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void nextQuestion() {
		Random rand = new Random();
		int questionNumber = rand.nextInt(questionList.length);
		currQuestion = questionList[questionNumber];
		mGameState.restartFall();
	}
	public String CurrentQuestion()
	{
		return currQuestion;
	}
}
