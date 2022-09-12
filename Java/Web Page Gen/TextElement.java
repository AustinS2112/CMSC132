package model;

/**
 * 
 * Represents text that can appear in an HTML document
 * 
 * @author UMCP
 *
 */
public class TextElement implements Element {
	private String text;

	public TextElement(String text) {
		this.text = text;
	}// Constructor

	public String genHTML(int indentation) {
		String HTML = "";

		for (int i = 0; i < indentation; i++) {
			HTML = HTML + " ";
		} // for

		HTML = HTML + text;

		return HTML;
	}// genHTML

}// TextElement
