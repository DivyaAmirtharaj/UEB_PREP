package com.android.ueb_prep;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class GameSurfaceView extends SurfaceView implements Runnable {
	public GameState mGameState;
	SurfaceHolder mSurfaceHolder;
	volatile boolean running = false;
	Context mContext;
	Thread thread = null;
	int squareSize = 95;
	List<Coordinate> mCoordinateList;
	String[] questionList;
	String currQuestion;
	private Paint backgroundPaint;
	private android.os.Handler mHandler;

	public GameSurfaceView(Context context, android.os.Handler handler) {
		super(context);
		mContext = context;
		mSurfaceHolder = getHolder();
		mHandler = handler;
		
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
					mCoordinateList = word.wordCoordinates();
					int k;
					for (k = 0; k < mCoordinateList.size(); k++) {
						paint.setTextSize(60);
						paint.setColor(Color.BLACK);
						canvas.drawText(CurrentQuestion(), mCoordinateList.get(k).x, mCoordinateList.get(k).y, paint);
					}
				}

				mSurfaceHolder.unlockCanvasAndPost(canvas);

				try {
					sleep(5); //Give the speed here
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

	public void reachedBottom() {
		// TODO
		// When it reached bottom it need to change the score
		Message msg = mHandler.obtainMessage();
		Bundle bundle = new Bundle();
		String threadMessage = "Reached Bottom";
		bundle.putString("message", threadMessage);
		msg.setData(bundle);
		mHandler.sendMessage(msg);
		try {
			sleep(2000); //Pause bettween words
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//	nextQuestion();

	}
	public String CurrentQuestion()
	{
		return currQuestion;
	}
}
