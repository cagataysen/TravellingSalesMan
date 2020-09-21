package algoTest;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class TSP_NN {

	private int coordinates[][];
	private Dictionary points = new Hashtable();

	ArrayList<Integer> visitedCities;
	ArrayList<Integer> bestTourSoFar;
	private int[][] tourMatrix;
	int tourCost;
	int costOfBestTourSoFar;
	int tourSize = 48;

	public TSP_NN(int[][] coordinates) {
		this.coordinates = coordinates;

		visitedCities = new ArrayList<Integer>();
		bestTourSoFar = new ArrayList<Integer>();

		tourCost = 0;
		costOfBestTourSoFar = 1000000000;

		tourMatrix = calculateDistanceMatrix(coordinates);
//		printMatrix(tourMatrix);

	};

	public ArrayList<Integer> nearestNeighbor(int city) {
		determineShortestTour(city);

		System.out.println(costOfBestTourSoFar);
		return bestTourSoFar;
	}

	public ArrayList<Integer> repNearestNeighbor() {

		for (int city = 0; city < tourSize; city++) {
			determineShortestTour(city);
		}
		System.out.println(costOfBestTourSoFar);
		return bestTourSoFar;
	}

	private void determineShortestTour(int node) {
		int initialNode = node;

		ArrayList<Integer> tmpSolution = new ArrayList<Integer>();

		visitedCities.clear();

		tourCost = 0;

		tmpSolution.add(node);
		visitedCities.add(node);

		while (visitedCities.size() < tourSize) {
			node = getNearestNode(node);
			visitedCities.add(node);
			tmpSolution.add(node);
		}

		tourCost += tourMatrix[node][initialNode];

		if (tourCost < costOfBestTourSoFar) {
			costOfBestTourSoFar = tourCost;
			bestTourSoFar = (ArrayList<Integer>) tmpSolution.clone();
		}
	}

	private int getNearestNode(int currentNode) {
		double edge = -1.0;
		int nearestNode = -1;

		for (int i = (tourSize - 1); i > -1; i--) {
			if (isMarkedVisited(i)) {
				continue;
			}

			if (-1 == tourMatrix[currentNode][i]) {
				continue;
			}

			if ((-1.0 == edge) && (-1 != tourMatrix[currentNode][i])) {
				edge = tourMatrix[currentNode][i];
			}

			if ((tourMatrix[currentNode][i] <= edge)) {
				edge = tourMatrix[currentNode][i];
				nearestNode = i;
			}
		}
		tourCost += tourMatrix[currentNode][nearestNode];
		return nearestNode;
	}

	private boolean isMarkedVisited(int node) {
		boolean found = false;

		for (int i = 0; i < visitedCities.size(); i++) {
			if (node == visitedCities.get(i)) {
				found = true;
				break;
			}
		}
		return found;
	}

	private int[][] calculateDistanceMatrix(int[][] coordinates) {
		int[][] distArr = new int[tourSize][tourSize];
		// CREATING THE DISTANCE MATRIX
		for (int i = 0; i < coordinates.length; i++) {
			for (int j = 0; j < distArr.length; j++) {
				int xdist = (coordinates[i][0] - coordinates[j][0]) * (coordinates[i][0] - coordinates[j][0]);

				int ydist = (coordinates[i][1] - coordinates[j][1]) * (coordinates[i][1] - coordinates[j][1]);

				int totalDist = (int) Math.sqrt(xdist + ydist);
				if (totalDist == 0) {
					distArr[i][j] = -1;
				} else {
					distArr[i][j] = (int) Math.sqrt(xdist + ydist);
				}

			}
		}
		return distArr;

	}

	private void printMatrix(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println("");
		}
	}

}
