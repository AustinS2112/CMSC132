package model;

/**
 * This class represents the logic of a game where a board is updated on each
 * step of the game animation. The board can also be updated by selecting a
 * board cell.
 * 
 * @author Department of Computer Science, UMCP
 */

public abstract class Game {
	protected BoardCell[][] board;
	private int maxRows;
	private int maxCols;

	/**
	 * Defines a board with BoardCell.EMPTY cells.
	 * 
	 * @param maxRows
	 * @param maxCols
	 */
	public Game(int maxRows, int maxCols) {
		this.maxRows = maxRows;
		this.maxCols = maxCols;
		board = new BoardCell[maxRows][maxCols];

		for (int row = 0; row < maxRows; row++) {
			for (int col = 0; col < maxCols; col++) {
				board[row][col] = BoardCell.EMPTY;
			}
		} // initializing cells in board to EMPTY

	}// constructor

	public int getMaxRows() {
		return maxRows;
	}// getMaxRows

	public int getMaxCols() {
		return maxCols;
	}// getMaxCols

	public void setBoardCell(int rowIndex, int colIndex, BoardCell boardCell) {
		for (int row = 0; row < maxRows; row++) {
			for (int col = 0; col < maxCols; col++) {
				if (row == rowIndex && col == colIndex) {
					board[row][col] = boardCell;
				} // cell founds
			} // cols
		} // rows
	}// setBoardCell

	public BoardCell getBoardCell(int rowIndex, int colIndex) {
		for (int row = 0; row < maxRows; row++) {
			for (int col = 0; col < maxCols; col++) {
				if (row == rowIndex && col == colIndex) {
					return board[row][col];
				}
			}
		}
		return null;
	}// getBoardCell;

	/**
	 * Initializes row with the specified color.
	 * 
	 * @param rowIndex
	 * @param cell
	 */
	public void setRowWithColor(int rowIndex, BoardCell cell) {
		for (int row = 0; row < maxRows; row++) {
			if (row == rowIndex) {
				for (int col = 0; col < maxCols; col++) {
					board[row][col] = cell;
				} // filling row
			} // found row
		} // cycle through array
	}// setRowWithColor

	/**
	 * Initializes column with the specified color.
	 * 
	 * @param colIndex
	 * @param cell
	 */
	public void setColWithColor(int colIndex, BoardCell cell) {
		for (int row = 0; row < maxRows; row++) {
			for (int col = 0; col < maxCols; col++) {
				if (col == colIndex) {
					board[row][col] = cell;
				} // col found
			} // cycle thru cols
		} // cycle thru rows
	}// setColWithColor

	/**
	 * Initializes the board with the specified color.
	 * 
	 * @param cell
	 */
	public void setBoardWithColor(BoardCell cell) {
		for (int row = 0; row < maxRows; row++) {
			for (int col = 0; col < maxCols; col++) {
				board[row][col] = cell;
			} // cols
		} // rows
	}// setBoardWithColor
	
	public int test() {
		return 1;
	}

	public abstract boolean isGameOver();

	public abstract int getScore();

	/**
	 * Advances the animation one step.
	 */
	public abstract void nextAnimationStep();

	/**
	 * Adjust the board state according to the current board state and the selected
	 * cell.
	 * 
	 * @param rowIndex
	 * @param colIndex
	 */
	public abstract void processCell(int rowIndex, int colIndex);

}// Game