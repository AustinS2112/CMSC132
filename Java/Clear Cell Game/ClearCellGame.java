package model;

import java.util.Random;

/**
 * This class extends GameModel and implements the logic of the clear cell game.
 * We define an empty cell as BoardCell.EMPTY. An empty row is defined as one
 * where every cell corresponds to BoardCell.EMPTY.
 * 
 * @author Department of Computer Science, UMCP
 */

public class ClearCellGame extends Game {
	private Random random;
	private int strategy;
	private static int gameScore;
	private int moveCounter;

	/**
	 * Defines a board with empty cells. It relies on the super class constructor to
	 * define the board. The random parameter is used for the generation of random
	 * cells. The strategy parameter defines which clearing cell strategy to use
	 * (for this project it will be 1). For fun, you can add your own strategy by
	 * using a value different that one.
	 * 
	 * @param maxRows
	 * @param maxCols
	 * @param random
	 * @param strategy
	 */
	public ClearCellGame(int maxRows, int maxCols, Random random, int strategy) {
		super(maxRows, maxCols);
		this.random = random;
		this.strategy = strategy;

	}// constructor

	/**
	 * The game is over when the last board row (row with index board.length -1) is
	 * different from empty row.
	 */
	public boolean isGameOver() {
		if (!RowIsEmpty(board.length - 1)) {
			return true;
		} else {
			return false;
		}
	}// isGameOver

	public int getScore() {
		return gameScore;
	}// getScore

	private Boolean RowIsEmpty(int rowIndex) {
		int emptyCounter = 0;

		for (int col = 0; col < board[rowIndex].length; col++) {
			if (board[rowIndex][col] == BoardCell.EMPTY) {
				emptyCounter++;
			}
		}

		if (emptyCounter == board[rowIndex].length) {// whole row is empty
			return true;
		} else {
			return false;
		}
	}// auxiliary method to check if a row is empty

	/**
	 * This method will attempt to insert a row of random BoardCell objects if the
	 * last board row (row with index board.length -1) corresponds to the empty row;
	 * otherwise no operation will take place.
	 */
	public void nextAnimationStep() {

		if (RowIsEmpty(board.length - 1)) {
			if (RowIsEmpty(0)) {
				moveCounter++;
				for (int col = 0; col < board[0].length; col++) {
					board[0][col] = BoardCell.getNonEmptyRandomBoardCell(random);
				}
			} else {// first row isn't empty
				for (int count = 0; count < moveCounter; count++) {
					for (int col = 0; col < board[moveCounter].length; col++) {
						board[moveCounter - count][col] = board[moveCounter - count - 1][col];
					}
				} // copying rows down

				setRowWithColor(0, BoardCell.EMPTY);// setting first row empty

				for (int col2 = 0; col2 < board[0].length; col2++) {
					board[0][col2] = BoardCell.getNonEmptyRandomBoardCell(random);
				} // inserting next row into spot of first row

				moveCounter++;
			} // first row isn't empty
		} // last row isn't empty
	}// nextAnimationStep

	private int numOfTargetCellsHorizontalL(int rowIndex, int colIndex) {
		int targets = 0;
		int curRowInd = rowIndex;
		int curColInd = colIndex;

		while (curColInd >= 0 && board[curRowInd][curColInd] == board[rowIndex][colIndex]) {
			targets++;
			curColInd--;
		}

		return targets - 1;

	}// returns num of cells w/same color as target cell in left horizontal direction

	private int numOfTargetCellsHorizontalR(int rowIndex, int colIndex) {
		int targets = 0;
		int curColInd = colIndex;

		while (curColInd < board[rowIndex].length && board[rowIndex][curColInd] == board[rowIndex][colIndex]) {
			targets++;
			curColInd++;
		}

		return targets - 1;
	}// returns num of cells w/same color as target cell in right horizontal
		// direction

	private int numOfTargetCellsUp(int rowIndex, int colIndex) {
		int targets = 0;
		int curRow = rowIndex;

		while (curRow >= 0 && board[curRow][colIndex] == board[rowIndex][colIndex]) {
			targets++;
			curRow--;
		}

		return targets - 1;
	}// returns num of target cells in upward direction

	private int numOfTargetCellsDown(int rowIndex, int colIndex) {
		int targets = 0;
		int curRow = rowIndex;

		while (curRow < board.length && board[curRow][colIndex] == board[rowIndex][colIndex]) {
			targets++;
			curRow++;
		}

		return targets - 1;
	}// returns num of target cells in upward direction

	private int numTargetCellsDiagonalL(int rowIndex, int colIndex) {
		int row = rowIndex;
		int col = colIndex;
		int target = 0;

		while (row >= 0 && col >= 0 && board[row][col] == board[rowIndex][colIndex]) {
			target++;
			row--;
			col--;
		}

		return target - 1;
	}// returns number of target cells to the diagonal left and up of target cell

	private int numTargetCellsDiagonalR(int rowIndex, int colIndex) {
		int row = rowIndex;
		int col = colIndex;
		int target = 0;

		while (row >= 0 && col < board[row].length && board[row][col] == board[rowIndex][colIndex]) {
			target++;
			row--;
			col++;
		}

		return target - 1;
	}// returns number of target cells to the diagonal right and up of target cell

	private int numTargetCellsDiagonalLDown(int rowIndex, int colIndex) {
		int row = rowIndex;
		int col = colIndex;
		int target = 0;

		while (row < board.length && col >= 0 && board[row][col] == board[rowIndex][colIndex]) {
			target++;
			row++;
			col--;
		}

		return target - 1;
	}// returns number of target cells to diagonal left and down of target cell

	private int numTargetCellsDiagonalRDown(int rowIndex, int colIndex) {
		int row = rowIndex;
		int col = colIndex;
		int target = 0;

		while (row < board.length && col < board[row].length && board[row][col] == board[rowIndex][colIndex]) {
			target++;
			row++;
			col++;
		}

		return target - 1;
	}// returns number of target cells to diagonal right and down of target cell

	private void makeEmptyHorizontalL(int rowIndex, int colIndex) {
		int col = colIndex;
		int limit = numOfTargetCellsHorizontalL(rowIndex, col);

		for (int i = 1; i <= limit; i++) {
			board[rowIndex][colIndex - i] = BoardCell.EMPTY;
			gameScore++;
		}

	}// makes all target cells to horizontal left of target cell empty

	private void makeEmptyHorizontalR(int rowIndex, int colIndex) {
		int col = colIndex;
		int limit = numOfTargetCellsHorizontalR(rowIndex, col);

		for (int i = 1; i <= limit; i++) {
			board[rowIndex][colIndex + i] = BoardCell.EMPTY;
			gameScore++;
		}

	}// makes all target cells to horizontal left of target cell empty

	private void makeEmptyUp(int rowIndex, int colIndex) {
		int row = rowIndex;
		int limit = numOfTargetCellsUp(row, colIndex);

		for (int i = 1; i <= limit; i++) {
			board[row - i][colIndex] = BoardCell.EMPTY;
			gameScore++;
		}

	}// makes all target cells upward of target cell empty

	private void makeEmptyDown(int rowIndex, int colIndex) {
		int row = rowIndex;
		int limit = numOfTargetCellsDown(row, colIndex);

		for (int i = 1; i <= limit; i++) {
			board[row + i][colIndex] = BoardCell.EMPTY;
			gameScore++;
		}

	}// makes all target cells downward of target cell empty

	private void makeEmptyDiagonalL(int rowIndex, int colIndex) {
		int row = rowIndex;
		int col = colIndex;
		int limit = numTargetCellsDiagonalL(row, col);

		for (int i = 1; i <= limit; i++) {
			board[row - i][col - i] = BoardCell.EMPTY;
			gameScore++;
		}

	}// makes target cells to the diagonal upward left of target cell empty

	private void makeEmptyDiagonalR(int rowIndex, int colIndex) {
		int row = rowIndex;
		int col = colIndex;
		int limit = numTargetCellsDiagonalR(row, col);

		for (int i = 1; i <= limit; i++) {
			board[row - i][col + i] = BoardCell.EMPTY;
			gameScore++;
		}
	}// makes target cells to the diagonal upward right of target cell empty

	private void makeEmptyDiagonalLDown(int rowIndex, int colIndex) {
		int row = rowIndex;
		int col = colIndex;
		int limit = numTargetCellsDiagonalLDown(row, col);

		for (int i = 1; i <= limit; i++) {
			board[row + i][col - i] = BoardCell.EMPTY;
			gameScore++;
		}
	}// makes target cells to the diagonal downward left of target cell empty

	private void makeEmptyDiagonalRDown(int rowIndex, int colIndex) {
		int row = rowIndex;
		int col = colIndex;
		int limit = numTargetCellsDiagonalRDown(row, col);

		for (int i = 1; i <= limit; i++) {
			board[row + i][col + i] = BoardCell.EMPTY;
			gameScore++;
		}
	}// makes target cells to the diagonal downward right of target cell empty

	/**
	 * This method will turn to BoardCell.EMPTY the cell selected and any adjacent
	 * surrounding cells in the vertical, horizontal, and diagonal directions that
	 * have the same color. The clearing of adjacent cells will continue as long as
	 * cells have a color that corresponds to the selected cell. Notice that the
	 * clearing process does not clear every single cell that surrounds a cell
	 * selected (only those found in the vertical, horizontal or diagonal
	 * directions).
	 * 
	 * IMPORTANT: Clearing a cell adds one point to the game's score.<br />
	 * <br />
	 * 
	 * If after processing cells, any rows in the board are empty,those rows will
	 * collapse, moving non-empty rows upward. For example, if we have the following
	 * board (an * represents an empty cell):<br />
	 * <br />
	 * RRR<br />
	 * GGG<br />
	 * YYY<br />
	 * * * *<br/>
	 * <br />
	 * then processing each cell of the second row will generate the following
	 * board<br />
	 * <br />
	 * RRR<br />
	 * YYY<br />
	 * * * *<br/>
	 * * * *<br/>
	 * <br />
	 * IMPORTANT: If the game has ended no action will take place.
	 * 
	 * 
	 */
	public void processCell(int rowIndex, int colIndex) {

		if (!isGameOver()) {// processing only occurs if game isn't over
			if (board[rowIndex][colIndex] != BoardCell.EMPTY) {// if cell isn't already empty

				// Make all horizontal target cells empty
				makeEmptyHorizontalL(rowIndex, colIndex);
				makeEmptyHorizontalR(rowIndex, colIndex);

				// Make all vertical target cells empty
				makeEmptyUp(rowIndex, colIndex);
				makeEmptyDown(rowIndex, colIndex);

				// Make all diagonal cells Empty
				makeEmptyDiagonalL(rowIndex, colIndex);
				makeEmptyDiagonalR(rowIndex, colIndex);
				makeEmptyDiagonalLDown(rowIndex, colIndex);
				makeEmptyDiagonalRDown(rowIndex, colIndex);

				// Clear target cell
				board[rowIndex][colIndex] = BoardCell.EMPTY;
				gameScore++;

				// collapse empty row
				for (int row = 0; row < board.length; row++) {
					if (RowIsEmpty(row) && row != board.length - 1) {
						for (int i = 0; i < board[row].length; i++) {
							board[row][i] = board[row + 1][i];
						} // copy contents
						for (int j = 0; j < board[row].length; j++) {
							board[row + 1][j] = BoardCell.EMPTY;
						} // make row empty
					} // if row is empty/isn't last row
				}

			} // empty cell check
		} // game over check

	}// processCell
}// ClearCellGame