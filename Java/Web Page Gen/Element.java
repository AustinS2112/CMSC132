package model;

/**
 * Represents an HTML element
 * @author UMCP
 *
 */
public interface Element {
	/**
	 * Returns a string that represents the HTML associated with the element.
	 * The string is indented based on the parameter value.
	 * @param indentation
	 * @return HTML associated with the element.
	 */
	public String genHTML(int indentation);
}
