package com.android.ueb_prep;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements Runnable {
	SurfaceHolder mSurfaceHolder;
	volatile boolean running = false;
	Thread thread = null;
	private Paint backgroundPaint;
	public GameState mGameState;
	int w, h;
	int y = 0;
	int x = 0;
	int squareSize = 95;
	String compareDeleteCoordString;
	List<Coordinate> coords;
	String[] questionList;
	String currQuestion;

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
		int screenHeight = d.heightPixels / 2 + 50;
		return screenHeight;
	}

	public final int displayWidth() {
		DisplayMetrics d = this.getResources().getDisplayMetrics();
		int screenWidth = d.widthPixels;
		return screenWidth;
	}
	
	private int h(int dH) {
		int h = dH / 22;
		return h;
	}
	
	private int w(int dW) {
		int w = dW / 13;
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
				
				List<Shape> gameShapes = mGameState.getShapes();
				
				for (int i = 0; i < 1; i++ ) {
					Shape shape = gameShapes.get(i);
					
					Paint paint = new Paint();
//					paint.setColor(getColor(shape.id));
					
					List<Coordinate> coordinatesToDelete = mGameState.deleteThisRow();
					coords = shape.shapeCoordinates();
						
					for (int k = 0; k < coordinatesToDelete.size(); k++) {
						int[] compareCoordToDelete = {coordinatesToDelete.get(k).x, coordinatesToDelete.get(k).y};
						compareDeleteCoordString = Arrays.toString(compareCoordToDelete);
						
						for (int j = 0; j < coords.size(); j++) {
	
							int[] compareCoord = {coords.get(j).x, coords.get(j).y};
							String compareCoordString = Arrays.toString(compareCoord);
							
							if(compareCoordString.equals(compareDeleteCoordString)) {
								coords.remove(j);
							}
						}
					}
					int k;
					for (k = 0; k < coords.size(); k++) {
						Rect rect = new Rect(coords.get(k).x, coords.get(k).y, coords.get(k).x + squareSize, coords.get(k).y + squareSize);
						paint.setTextSize(60);
						paint.setColor(Color.BLACK);
						canvas.drawText(CurrentQuestion(), coords.get(k).x, coords.get(k).y,paint);
					}
				}

				mSurfaceHolder.unlockCanvasAndPost(canvas);

				try {
					Thread.sleep(5); //Give the speed here
					mGameState.fallingShape.fall();
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
