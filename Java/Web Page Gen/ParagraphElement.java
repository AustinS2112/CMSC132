package model;

import java.util.ArrayList;

/**
 * 
 * Represents a paragraph (&lt;p&gt;) tag. It relies on an ArrayList in order to
 * keep track of the set of Element objects that are part of the paragraph.
 * 
 * @author UMCP
 *
 */
public class ParagraphElement extends TagElement {
	private ArrayList<Element> items;
	private String attributes;

	public ParagraphElement(String attributes) {
		super("p", true, null, attributes);
		this.attributes = attributes;
		items = new ArrayList<Element>();
	}

	public void addItem(Element item) {
		items.add(item);
	}// addItem

	public String genHTML(int indentation) {
		String HTML = "";

		// start tag-----------------------------
		for (int i = 0; i < indentation; i++) {
			HTML = HTML + " ";
		} // indentation

		HTML = HTML + this.getStartTag() + "\n";

		// contents------------------------------
		for (int j = 0; j < items.size(); j++) {
			HTML = HTML + items.get(j).genHTML(indentation + indentation) + "\n";
		}

		// endTag--------------------------------
		for (int i = 0; i < indentation; i++) {
			HTML = HTML + " ";
		} // indentation

		HTML = HTML + this.getEndTag();

		return HTML;
	}// genHTML

} // ParagraphElement
