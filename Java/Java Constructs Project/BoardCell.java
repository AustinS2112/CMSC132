package enumeratedTypes;

import java.awt.Color;
import java.util.Random;

/**
 * This enumerate type represents a board cell. A board cell has a color (based
 * on the Color class) and a name (e.g., "R")
 */

public enum BoardCell {
	/* Defining possible types of cells (must appear first) */
	RED(Color.RED, "R"), GREEN(Color.GREEN, "G"), BLUE(Color.BLUE, "B"), YELLOW(Color.YELLOW, "Y"),
	EMPTY(Color.WHITE, ".");

	private final Color color;
	private final String name;
	private static int totalColors = BoardCell.values().length;

	/* Needs private constructor */
	private BoardCell(Color color, String name) {
		this.color = color;
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public static int getTotalColors() {
		return totalColors;
	}

	/**
	 * Generates a random BoardCell using the specified Random object.
	 * 
	 * @param random
	 * @return random BoardCell
	 */

	public static BoardCell getNonEmptyRandomBoardCell(Random random) {
		int target = random.nextInt(totalColors);
		BoardCell answer = null;

		for (BoardCell boardCell : BoardCell.values()) {
			if (target == boardCell.ordinal()) {
				if (boardCell == BoardCell.EMPTY) {
					/* We don't want to return Empty cell */
					answer = BoardCell.RED;
				}
				answer = boardCell;
			}
		}

		return answer;
	}

	public static BoardCell getNonEmptyRandomBoardCellTwo(Random random) {
		BoardCell data[] = { RED, GREEN, BLUE, YELLOW };

		return data[random.nextInt(4)];
	}

	public static void main(String[] args) {
		System.out.println("Total Colors: " + BoardCell.getTotalColors());
		System.out.print("Listing of all cells: ");
		for (BoardCell cell : BoardCell.values())
			System.out.print(" " + cell);
		System.out.println();

		/* Some comparisons */
		BoardCell a = BoardCell.RED;
		BoardCell b = BoardCell.RED;
		if (a.equals(b)) {
			System.out.println("a and b same cell when using equals");
		}

		if (a == b) {
			System.out.println("a and b same cell when using ==");
		}

		System.out.println("Random values #1");
		long seed = 2L;
		Random random = new Random(seed); /* What if we use new Random() ? */
		for (int i = 1; i <= 4; i++) {
			BoardCell randomCell = BoardCell.getNonEmptyRandomBoardCell(random);
			System.out.println(randomCell);
		}

		System.out.println("Random values #2");
		Random randomTwo = new Random(seed); /* What if we use new Random() ? */
		for (int i = 1; i <= 32; i++) {
			BoardCell randomCell = BoardCell.getNonEmptyRandomBoardCellTwo(randomTwo);
			System.out.println(randomCell);
		}
	}
}