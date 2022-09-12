package model;

/**
 * Represents a heading. We can have levels 1 thru 6 (e.g., &lt;h1&gt;,
 * &lt;h2&gt;, etc.)
 * 
 * @author UMCP
 *
 */
public class HeadingElement extends TagElement {
	private Element content;
	private int level;
	private String attributes;

	public HeadingElement(Element content, int level, String attributes) {
		super("h" + level, true, content, attributes);
		this.content = content;
		this.attributes = attributes;

		if (level >= 1 && level <= 6) {
			this.level = level;
		} // if
	}// Constructor

}// HeadingElement
