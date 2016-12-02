package com.android.ueb_prep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameState {
	List<Word> shapes = new ArrayList<Word>();
	Word fallingWord;
	GameSurfaceView mGameSurfaceView;
	List<int[]> deleteMe;


	public GameState(GameSurfaceView surface) {
		Word tWord = Word.l(surface);
		shapes.add(tWord);

		fallingWord = tWord;
		this.mGameSurfaceView = surface;
	}

	public Word makeNewShape(int randomNumber, GameSurfaceView surface) {
			return Word.l(surface);
		}

	public int randomNumber() {
		Random rand = new Random();
		int newShape = rand.nextInt(5);
		return newShape;
	}

	public List<Word> getWords() {
		compareCoordinates(getFalling(), getCoordinates());
		isThisRowFull(getCoordinates());

		if (!fallingWord.isFalling) {
			int shapeNumber = randomNumber();
			Word addShape = makeNewShape(shapeNumber, mGameSurfaceView);
			shapes.add(addShape);
			fallingWord = shapes.get(shapes.size() - 1);
			fallingWord.isFalling = true;

		}
		return shapes;
	}

	public List<int[]> getFalling() {
		List<int[]> getFalling = new ArrayList<int[]>();
		int[] aPair = {fallingWord.mCoordinate.x, fallingWord.mCoordinate.y+100};
		getFalling.add(aPair);

		return getFalling;
	}

	public List<int[]> getCoordinates() {
		List<int[]> coordinateList = new ArrayList<int[]>();

		for (int i = 0; i < shapes.size(); i++ ) {
			if (shapes.get(i) != fallingWord) {
				Word shape = shapes.get(i);
				List<Coordinate> coords = shape.shapeCoordinates();



				for (int j = 0; j < coords.size(); j++) {
					int[] pair = {coords.get(j).x, coords.get(j).y};
					coordinateList.add(pair);
				}
			}
		}
		return coordinateList;
	}

	public void compareCoordinates(List<int[]> getFalling, List<int[]> getCoordinates) {
		for (int[] shapeCoords : getCoordinates ) {
			String coordString = Arrays.toString(shapeCoords);
			for (int[] fallingCoords : getFalling) {
				String fallingString = Arrays.toString(fallingCoords);
				if (coordString.equals(fallingString)) {
					fallingWord.isFalling = false;				}
			}
		}
		isThisRowFull(getCoordinates());
	}

	public void isThisRowFull(List<int[]> getCoordinates) {
		List<int[]> list = new ArrayList<int[]>();
		for (int i = 0; i < getCoordinates().size(); i++) {
			int[] coordPair = getCoordinates.get(i);
			list.add(coordPair);


		}
		deleteMe = new ArrayList<int[]>();

		if (list.size() == 7) {
			for (int i = 0; i < list.size(); i++) {
				int[] addToDeleteMe = list.get(i);
				deleteMe.add(addToDeleteMe);
			}
		}

	}

	public List<Coordinate> deleteThisRow() {
		List<Coordinate> coordsToDelete = new ArrayList<Coordinate>();
		for (int i = 0; i < deleteMe.size(); i++) {
			int x = deleteMe.get(i)[0];
			int y = deleteMe.get(i)[1];
			Coordinate coord = new Coordinate(x,y);
			coordsToDelete.add(coord);
		}
		return coordsToDelete;
	}


	public void rightAnswer() {
		fallingWord.stop();
		fallingWord.restart();
	}


   public void restartFall()
   {

	   fallingWord.stop();
	   fallingWord.restart();
   }


}
