package com.android.tetristakethree;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;

import java.util.Locale;
import java.util.Random;

import static com.android.tetristakethree.R.integer.scoreTxtID;

public class MainActivity extends Activity {
	GameSurfaceView gameSurfaceView;
	FrameLayout gameFrame;
	RelativeLayout quizLayout;
	Button left;
	Button right;
	Button down;
	TextView question, result, scoreTxt;
	EditText answer;
	Button submit, cancel;
	String speechText = "Hello";
	TextToSpeech tts;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		gameSurfaceView = new GameSurfaceView(this);

		gameFrame = new FrameLayout(this);
		quizLayout = new RelativeLayout(this);

		question = new TextView(this);
		question.setId(R.integer.questionID);
		question.setText("Type in the Braille\nfor the falling word");
		question.setTextSize(24);

		answer = new EditText(this);
		answer.setId(R.integer.answerID);
		answer.setSingleLine();

		submit = new Button(this);
		submit.setText("Submit");
		submit.setId(R.integer.submitID);

		cancel = new Button(this);
		cancel.setText("Next");
		cancel.setId(R.integer.cancelID);

		result = new TextView(this);
		result.setId(R.integer.resultID);
		result.getEditableText();

		scoreTxt = new TextView(this);
		scoreTxt.setId(R.integer.scoreTxtID);
		scoreTxt.setText("Score - ");
		scoreTxt.setTextSize(18);

		left = new Button(this);
		left.setText("Left");
		left.setId(111);

		right = new Button(this);
		right.setText("Right");
		right.setId(222);

		down = new Button(this);
		down.setText("Rotate");
		down.setId(333);

		tts=new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {

			@Override
			public void onInit(int status) {
				// TODO Auto-generated method stub
				if(status == TextToSpeech.SUCCESS){
					int result=tts.setLanguage(Locale.US);
					if(result==TextToSpeech.LANG_MISSING_DATA ||
							result==TextToSpeech.LANG_NOT_SUPPORTED){
						Log.e("error", "This Language is not supported");
					}
					else{
					//	String currQuestion = gameSurfaceView.CurrentQuestion();
						ConvertTextToSpeech(gameSurfaceView.CurrentQuestion());
					}
				}
				else
					Log.e("error", "Initilization Failed!");
			}
		});



		RelativeLayout.LayoutParams rl = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

		RelativeLayout.LayoutParams questionLayout = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams answerLayout = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams submitLayout = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams cancelLayout = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams resultLayout = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams scoreTxtLayout = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

		RelativeLayout.LayoutParams leftButton = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams rightButton = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams downButton = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

		quizLayout.setLayoutParams(rl);
		quizLayout.addView(question);
		quizLayout.addView(answer);
		quizLayout.addView(submit);
		quizLayout.addView(cancel);
		quizLayout.addView(result);
		quizLayout.addView(scoreTxt);

		questionLayout.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		questionLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		answerLayout.addRule(RelativeLayout.BELOW, R.integer.questionID);
		submitLayout.addRule(RelativeLayout.BELOW , R.integer.answerID );
		cancelLayout.addRule(RelativeLayout.BELOW , R.integer.answerID );
		cancelLayout.addRule(RelativeLayout.RIGHT_OF , R.integer.submitID );
		resultLayout.addRule(RelativeLayout.BELOW , R.integer.submitID);
		scoreTxtLayout.addRule(RelativeLayout.BELOW, R.integer.resultID);


		answer.setLayoutParams(answerLayout);
		submit.setLayoutParams(submitLayout);
		cancel.setLayoutParams(cancelLayout);
		result.setLayoutParams(resultLayout);
		scoreTxt.setLayoutParams(scoreTxtLayout);

		submit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
//				tts.speak(speechText, TextToSpeech.QUEUE_FLUSH, null);
				String currQuestion = gameSurfaceView.CurrentQuestion();
				if (answer.getText().toString().toUpperCase().equals(currQuestion))
				{
					result.setText("Correct");
					gameSurfaceView.game.rightAnswer();
					gameSurfaceView.nextQuestion();
					currQuestion = gameSurfaceView.CurrentQuestion();
					ConvertTextToSpeech(currQuestion);
					answer.setText("");
				}
				else
				{
					result.setText("Wrong");
				}
			}
		});

		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String currQuestion;
				gameSurfaceView.nextQuestion();
				currQuestion = gameSurfaceView.CurrentQuestion();
				ConvertTextToSpeech(currQuestion);
				answer.setText("");
			}
		});

//		GameButtons.addView(left);
//		GameButtons.addView(right);
//		GameButtons.addView(down);

//		leftButton.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//		leftButton.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);


//		rightButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//		rightButton.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

//		downButton.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
//		downButton.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

		left.setLayoutParams(leftButton);
		right.setLayoutParams(rightButton);
		down.setLayoutParams(downButton);

		gameFrame.addView(gameSurfaceView);
		gameFrame.addView(quizLayout);
		setContentView(gameFrame);

	}

	@Override
	protected void onResume() {
		super.onResume();
		gameSurfaceView.onResumeGameSurfaceView();
	}

	@Override
	public void onPause() {

		super.onPause();
		if(tts != null){

			tts.stop();
			tts.shutdown();
		}

		gameSurfaceView.onPauseGameSurfaceView();
	}

	private void ConvertTextToSpeech(String text) {
		// TODO Auto-generated method stub

		speechText = text;
		if(speechText==null||"".equals(speechText))
		{
			speechText = "Content not available";
			tts.speak(speechText, TextToSpeech.QUEUE_FLUSH, null);
		}else
			tts.speak(speechText, TextToSpeech.QUEUE_FLUSH, null);
	}


//	@Override
	/*
	public void onClick(View v) {
		if (v == left) {
		   gameSurfaceView.game.userPressedLeft();
		} else if (v == right) {
			gameSurfaceView.game.userPressedRight();
		} else if (v == down) {
			gameSurfaceView.game.userPressedRotate();
		}
	}*/
}
