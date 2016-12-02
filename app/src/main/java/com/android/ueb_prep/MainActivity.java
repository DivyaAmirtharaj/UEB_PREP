package com.android.ueb_prep;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class MainActivity extends Activity {
	GameSurfaceView gameSurfaceView;
	FrameLayout gameFrame;
	RelativeLayout quizLayout;
	TextView questionTextView, resultTextView, scoreLabelTextView, scoreTextView;
	EditText answerEditText;
	Button submitButton, cancelButton, switchKeyboardButton;
	TextToSpeech tts;
	int mScore = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		DrawObjects();
		InitSpeech();

		submitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
//				tts.speak(speechText, TextToSpeech.QUEUE_FLUSH, null);
				String currQuestion = gameSurfaceView.CurrentQuestion();
				if (answerEditText.getText().toString().toUpperCase().equals(currQuestion))
				{
					resultTextView.setText("Correct");
					resultTextView.setTextColor(Color.GREEN);
					gameSurfaceView.mGameState.rightAnswer();
					gameSurfaceView.nextQuestion();
					currQuestion = gameSurfaceView.CurrentQuestion();
					ConvertTextToSpeech(currQuestion);
					answerEditText.setText("");
				}
				else
				{
					resultTextView.setText("Wrong");
					resultTextView.setTextColor(Color.RED);
				}
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String currQuestion;
				gameSurfaceView.nextQuestion();
				currQuestion = gameSurfaceView.CurrentQuestion();
				ConvertTextToSpeech(currQuestion);
				answerEditText.setText("");
			}
		});


		switchKeyboardButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ShowKeyboardPicker();
			}
		});

		answerEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					submitButton.performClick();
					return true;
				}
				return false;
			}
		});

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

	private void InitSpeech(){
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
						String currQuestion = gameSurfaceView.CurrentQuestion();
						ConvertTextToSpeech(gameSurfaceView.CurrentQuestion());
					}
				}
				else
					Log.e("error", "Initilization Failed!");
			}
		});

	}


	private void ConvertTextToSpeech(String text) {
		// TODO Auto-generated method stub

		String speechText = text;
		if(speechText==null||"".equals(speechText))
		{
			speechText = "Content not available";
			tts.speak(speechText, TextToSpeech.QUEUE_FLUSH, null);
		}else
			tts.speak(speechText, TextToSpeech.QUEUE_FLUSH, null);
		Log.i("Speech", speechText);
	}

	private void DrawObjects(){
		gameSurfaceView = new GameSurfaceView(this);

		gameFrame = new FrameLayout(this);
		quizLayout = new RelativeLayout(this);

		questionTextView = new TextView(this);
		questionTextView.setId(R.integer.questionID);
		questionTextView.setText("Type in the Braille\nfor the falling word");
		questionTextView.setTextSize(18);

		answerEditText = new EditText(this);
		answerEditText.setId(R.integer.answerID);
		answerEditText.setSingleLine();
		answerEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);

		submitButton = new Button(this);
		submitButton.setText("Submit");
		submitButton.setId(R.integer.submitID);

		cancelButton = new Button(this);
		cancelButton.setText("Next");
		cancelButton.setId(R.integer.cancelID);

		resultTextView = new TextView(this);
		resultTextView.setId(R.integer.resultID);
		resultTextView.setTextSize(20);
		resultTextView.getEditableText();

		scoreLabelTextView = new TextView(this);
		scoreLabelTextView.setId(R.integer.scoreTxtID);
		scoreLabelTextView.setText("Score - ");
		scoreLabelTextView.setTextSize(18);

		scoreTextView = new TextView(this);
		scoreTextView.setId(R.integer.scoreID);
		scoreTextView.setText("0");
		scoreTextView.setTextSize(18);

		switchKeyboardButton = new Button(this);
		switchKeyboardButton.setText("Swith Keyboard");
		switchKeyboardButton.setId(R.integer.switchKeyboardID);

		RelativeLayout.LayoutParams rl = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

		RelativeLayout.LayoutParams questionLayout = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams answerLayout = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams submitLayout = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams cancelLayout = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams switchKeyboardLayout = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams resultLayout = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams scoreTxtLayout = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams scoreLayout = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

		quizLayout.setLayoutParams(rl);
		quizLayout.addView(questionTextView);
		quizLayout.addView(answerEditText);
		quizLayout.addView(submitButton);
		quizLayout.addView(cancelButton);
		quizLayout.addView(switchKeyboardButton);
		quizLayout.addView(resultTextView);
		quizLayout.addView(scoreLabelTextView);
		quizLayout.addView(scoreTextView);

		questionLayout.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		questionLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		answerLayout.addRule(RelativeLayout.BELOW, R.integer.questionID);
		submitLayout.addRule(RelativeLayout.BELOW , R.integer.answerID );
		cancelLayout.addRule(RelativeLayout.BELOW , R.integer.answerID );
		cancelLayout.addRule(RelativeLayout.RIGHT_OF , R.integer.submitID );
		resultLayout.addRule(RelativeLayout.BELOW , R.integer.submitID);
		scoreTxtLayout.addRule(RelativeLayout.BELOW, R.integer.resultID);
		scoreLayout.addRule(RelativeLayout.BELOW, R.integer.resultID);
		scoreLayout.addRule(RelativeLayout.RIGHT_OF, R.integer.scoreTxtID);
		switchKeyboardLayout.addRule(RelativeLayout.BELOW , R.integer.scoreTxtID );


		answerEditText.setLayoutParams(answerLayout);
		submitButton.setLayoutParams(submitLayout);
		cancelButton.setLayoutParams(cancelLayout);
		resultTextView.setLayoutParams(resultLayout);
		scoreLabelTextView.setLayoutParams(scoreTxtLayout);
		scoreTextView.setLayoutParams(scoreLayout);
		switchKeyboardButton.setLayoutParams(switchKeyboardLayout);

	}

	private void ShowKeyboardPicker() {
		InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
		imeManager.showInputMethodPicker();
	}

}
