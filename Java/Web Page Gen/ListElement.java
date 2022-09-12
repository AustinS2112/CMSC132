package model;

import java.util.ArrayList;

/**
 * Represents the &lt;ul&gt and the &lt;ol&gt; tags. An ArrayList is used to
 * keep track of the Element objects of the list.
 * 
 * @author UMCP
 *
 */
public class ListElement extends TagElement {
	private ArrayList<Element> items;
	private String attributes;
	private boolean ordered;

	public ListElement(boolean ordered, String attributes) {
		super(null, true, null, attributes);
		items = new ArrayList<Element>();
		this.attributes = attributes;
		this.ordered = ordered;
	} // Constructor

	public void addItem(Element item) {
		items.add(item);
	}// addItem

	private String indListElements(int indentation, int index) {
		String returnString = "";

		for (int i = 0; i < indentation; i++) {
			returnString = returnString + " ";
		} // indentation

		returnString = returnString + "<li>" + "\n";

		returnString = returnString + items.get(index).genHTML(indentation + 3) + "\n";

		for (int j = 0; j < indentation; j++) {
			returnString = returnString + " ";
		} // indentation

		returnString = returnString + "</li>" + "\n";

		return returnString;
	}// indListElements

	public String genHTML(int indentation) {
		String HTML = "";

		for (int i = 0; i < indentation; i++) {
			HTML = HTML + " ";
		} // indentation

		// start tag----------------------------------------

		if (ordered == false) {// checking ordered/unordered
			if (this.getIdStatus() == false) {// checking id status
				if (attributes == null) {// checking attributes
					HTML = HTML + "<ul>" + "\n";// no id and no attributes
				} else {
					HTML = HTML + "<ul " + attributes + ">" + "\n";// attributes w/ no id
				}
			} else {// id status is true
				if (attributes == null) {
					HTML = HTML + "<ul id=" + "\"" + "ul" + this.getId() + "\"" + ">" + "\n";
				} else {
					HTML = HTML + "<ul id=" + "\"" + "ul" + this.getId() + "\" " + attributes + ">" + "\n";
				}

			} // else
		} else {// ordered is true
			if (this.getIdStatus() == false) {
				if (attributes == null) {
					HTML = HTML + "<ol>" + "\n";// no id and no attributes
				} else {
					HTML = HTML + "<ol " + attributes + ">" + "\n";// no id w/attributes
				}
			} else {// id status is true
				if (attributes == null) {
					HTML = HTML + "<ol id=" + "\"" + "ol" + this.getId() + "\" " + ">" + "\n";
				} else {
					HTML = HTML + "<ol id=" + "\"" + "ol" + this.getId() + "\" " + attributes + ">" + "\n";
				}
			} // else

		} // order is true

		// listed section-----------------------------------------------

		for (int idx = 0; idx < items.size(); idx++) {
			HTML = HTML + indListElements(indentation + 3, idx);
		}

		// endingTag section---------------------------------------------

		for (int i = 0; i < indentation; i++) {
			HTML = HTML + " ";
		}

		if (ordered == false) {
			HTML = HTML + "</ul>";
		} else {
			HTML = HTML + "</ol>";
		}
		return HTML;
	}// genHTML
}// ListElement
