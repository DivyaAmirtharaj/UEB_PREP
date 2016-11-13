package com.android.tetristakethree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameState {
	List<Shape> shapes = new ArrayList<Shape>();
	Shape fallingShape;
	int ground;
	GameSurfaceView mGameSurfaceView;
	List<int[]> deleteMe;


	public GameState(GameSurfaceView surface) {
		Shape tShape = Shape.l(surface);
		shapes.add(tShape);

		fallingShape = tShape;
		this.mGameSurfaceView = surface;
	}

	public Shape makeNewShape(int randomNumber, GameSurfaceView surface) {
			return Shape.l(surface);
		}

	public int randomNumber() {
		Random rand = new Random();
		int newShape = rand.nextInt(5);
		return newShape;
	}

	public List<Shape> getShapes() {
		compareCoordinates(getFalling(), getCoordinates());
		isThisRowFull(getCoordinates());

		if (!fallingShape.isFalling) {
			int shapeNumber = randomNumber();
			Shape addShape = makeNewShape(shapeNumber, mGameSurfaceView);
			shapes.add(addShape);
			fallingShape = shapes.get(shapes.size() - 1);
			fallingShape.isFalling = true;

		}
		return shapes;
	}

	public List<int[]> getFalling() {
		List<int[]> getFalling = new ArrayList<int[]>();
		int[] aPair = {fallingShape.mCoordinate.x, fallingShape.mCoordinate.y+100};
		getFalling.add(aPair);

		return getFalling;
	}

	public List<int[]> getCoordinates() {
		List<int[]> coordinateList = new ArrayList<int[]>();

		for (int i = 0; i < shapes.size(); i++ ) {
			if (shapes.get(i) != fallingShape) {
				Shape shape = shapes.get(i);
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
					fallingShape.isFalling = false;				}
			}
		}
		isThisRowFull(getCoordinates());
	}

	public void isThisRowFull(List<int[]> getCoordinates) {
		List<int[]> nine = new ArrayList<int[]>();
		for (int i = 0; i < getCoordinates().size(); i++) {
			int[] coordPair = getCoordinates.get(i);
			nine.add(coordPair);


		}
		deleteMe = new ArrayList<int[]>();

		if (nine.size() == 7) {
			for (int i = 0; i < nine.size(); i++) {
				int[] addToDeleteMe = nine.get(i);
//				Log.i("nine 1 " + nine.get(i)[0], "nine 2 "  + nine.get(i)[1]);
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
		fallingShape.stop();
		fallingShape.restart();
	}


   public void restartFall()
   {

	   fallingShape.stop();
	   fallingShape.restart();
   }


}
