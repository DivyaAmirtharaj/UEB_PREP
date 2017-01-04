package com.android.ueb_prep;

import java.util.ArrayList;
import java.util.List;

class GameState {
	Word fallingWord;
	private List<Word> mWordList = new ArrayList<Word>();
	private GameSurfaceView mGameSurfaceView;

	GameState(GameSurfaceView surface) {
		Word tWord = Word.l(surface);
		mWordList.add(tWord);

		fallingWord = tWord;
		this.mGameSurfaceView = surface;
	}

	private Word makeNewWord(GameSurfaceView surface) {
			return Word.l(surface);
		}

	List<Word> getWords() {
		if (!fallingWord.isFalling) {
			Word addWord = makeNewWord(mGameSurfaceView);
			mWordList.add(addWord);
			fallingWord = mWordList.get(mWordList.size() - 1);
			fallingWord.isFalling = true;

		}
		return mWordList;
	}

	void rightAnswer() {
		restartFall();
	}

	void restartFall()
   {
	   fallingWord.stop();
	   fallingWord.restart();
   }


}
