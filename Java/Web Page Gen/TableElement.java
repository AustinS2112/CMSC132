package model;

/**
 * Represents the &lt;table&gt tag. A two dimensional array is used to keep
 * track of the Element objects of table.
 * 
 * @author UMCP
 *
 */
public class TableElement extends TagElement {
	private Element[][] items;
	private String attributes;
	private int rows;
	private int cols;

	public TableElement(int rows, int cols, String attributes) {
		super("table", true, null, attributes);
		this.rows = rows;
		this.cols = cols;
		items = new Element[rows][cols];
		this.attributes = attributes;
	}// Constructor

	public void addItem(int rowIndex, int colIndex, Element item) {
		for (int row = 0; row < items.length; row++) {
			for (int col = 0; col < items[row].length; col++) {
				if (row == rowIndex && col == colIndex) {
					items[row][col] = item;
				} // if
			} // col
		} // row
	}// addItem

	public double getTableUtilization() {
		double used = 0;

		for (int row = 0; row < items.length; row++) {
			for (int col = 0; col < items[row].length; col++) {
				if (items[row][col] != null) {// if cell is not empty
					used++;
				} // if
			} // col
		} // row

		double returnVal = 100 * (used / (rows * cols));// rows and cols are still ints

		return returnVal;
	}// getTableUtilization

	private String oneRow(int indentation, int rowNum) {
		String row = "";

		for (int i = 0; i < indentation; i++) {
			row = row + " ";
		} // indentation

		row = row + "<tr>";

		for (int colNum = 0; colNum < items[rowNum].length; colNum++) {
			if (items[rowNum][colNum] != null) {// cell not empty
				row = row + "<td>" + items[rowNum][colNum].genHTML(0) + "</td>";
			} else {// empty cell
				row = row + "<td></td>";
			} // else
		} // for

		row = row + "</tr>" + "\n";

		return row;
	}// Auxiliary method to print one row of table

	public String genHTML(int indentation) {
		String HTML = "";

		for (int i = 0; i < indentation; i++) {
			HTML = HTML + " ";
		} // indentation

		// start tag-------------------------------
		HTML = HTML + this.getStartTag() + "\n";

		// Table content---------------------------
		for (int j = 0; j < items.length; j++) {
			HTML = HTML + oneRow(indentation + 3, j);
		} // for

		// endTag----------------------------------
		for (int j = 0; j < indentation; j++) {
			HTML = HTML + " ";
		} // indentation

		HTML = HTML + "</table>";

		return HTML;
	}// genHTML

}// TableElement
