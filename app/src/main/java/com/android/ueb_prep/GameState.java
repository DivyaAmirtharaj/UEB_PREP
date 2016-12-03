package com.android.ueb_prep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class GameState {
	Word fallingWord;
	private List<Word> mWordList = new ArrayList<Word>();
	private GameSurfaceView mGameSurfaceView;
	private List<int[]> deleteMe;


	GameState(GameSurfaceView surface) {
		Word tWord = Word.l(surface);
		mWordList.add(tWord);

		fallingWord = tWord;
		this.mGameSurfaceView = surface;
	}

	private Word makeNewWord(int randomNumber, GameSurfaceView surface) {
			return Word.l(surface);
		}

	private int randomNumber() {
		Random rand = new Random();
		int newWord;
		newWord = rand.nextInt(5);
		return newWord;
	}

	List<Word> getWords() {
		compareCoordinates(getFalling(), getCoordinates());
		isThisRowFull(getCoordinates());

		if (!fallingWord.isFalling) {
			int wordNumber = randomNumber();
			Word addWord = makeNewWord(wordNumber, mGameSurfaceView);
			mWordList.add(addWord);
			fallingWord = mWordList.get(mWordList.size() - 1);
			fallingWord.isFalling = true;

		}
		return mWordList;
	}

	private List<int[]> getFalling() {
		List<int[]> getFalling = new ArrayList<int[]>();
		int[] aPair = {fallingWord.mCoordinate.x, fallingWord.mCoordinate.y+100};
		getFalling.add(aPair);

		return getFalling;
	}

	private List<int[]> getCoordinates() {
		List<int[]> coordinateList = new ArrayList<int[]>();

		for (int i = 0; i < mWordList.size(); i++) {
			if (mWordList.get(i) != fallingWord) {
				Word word = mWordList.get(i);
				List<Coordinate> coordinates = word.wordCoordinates();


				for (int j = 0; j < coordinates.size(); j++) {
					int[] pair = {coordinates.get(j).x, coordinates.get(j).y};
					coordinateList.add(pair);
				}
			}
		}
		return coordinateList;
	}

	private void compareCoordinates(List<int[]> getFalling, List<int[]> getCoordinates) {
		for (int[] wordCoordinates : getCoordinates) {
			String coordinatesString = Arrays.toString(wordCoordinates);
			for (int[] fallingCoords : getFalling) {
				String fallingString = Arrays.toString(fallingCoords);
				if (coordinatesString.equals(fallingString)) {
					fallingWord.isFalling = false;				}
			}
		}
		isThisRowFull(getCoordinates());
	}

	private void isThisRowFull(List<int[]> getCoordinates) {
		List<int[]> list = new ArrayList<int[]>();
		for (int i = 0; i < getCoordinates().size(); i++) {
			int[] coordinatePair = getCoordinates.get(i);
			list.add(coordinatePair);


		}
		deleteMe = new ArrayList<int[]>();

		if (list.size() == 7) {
			for (int i = 0; i < list.size(); i++) {
				int[] addToDeleteMe = list.get(i);
				deleteMe.add(addToDeleteMe);
			}
		}

	}

	List<Coordinate> deleteThisRow() {
		List<Coordinate> coordinateToDelete = new ArrayList<Coordinate>();
		for (int i = 0; i < deleteMe.size(); i++) {
			int x = deleteMe.get(i)[0];
			int y = deleteMe.get(i)[1];
			Coordinate coord = new Coordinate(x,y);
			coordinateToDelete.add(coord);
		}
		return coordinateToDelete;
	}


	void rightAnswer() {
		fallingWord.stop();
		fallingWord.restart();
	}


	void restartFall()
   {

	   fallingWord.stop();
	   fallingWord.restart();
   }


}
