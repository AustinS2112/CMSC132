package model;

/**
 * 
 * Represents an HTML tag element (<&lt;p&gt;, &lt;ul&gt;, etc.). Each tag has
 * an id (ids start at 1). By default the start tag will have an id (e.g.,
 * <&lt;p id="a1"&gt;&lt;/p&gt;) when the HTML for the tag is generated. This
 * can be disabled by using enableId.
 * 
 * @author UMCP
 *
 */
public class TagElement implements Element {
	private String tagName;
	private boolean endTag;
	private Element content;
	private String attributes;
	private int Id;
	private static int idCounter;
	private static boolean idEnabled;

	public TagElement(String tagName, boolean endTag, Element content, String attributes) {
		this.tagName = tagName;
		this.endTag = endTag;
		this.content = content;
		this.attributes = attributes;

		if (idEnabled == true) {
			idCounter++;
			this.Id = idCounter;
		}
	}// Constructor

	public boolean getIdStatus() {
		return idEnabled;
	}// getIdStatus

	public int getId() {
		return Id;
	}// getId

	public String getStringId() {
		return tagName + Id;
	}// getStringId

	public String getStartTag() {
		if (attributes != null) {
			if (idEnabled == false) {
				return "<" + tagName + " " + attributes + ">";
			} else {
				return "<" + tagName + " id=" + "\"" + tagName + Id + "\"" + attributes + ">";
			}
		} else {// no attributes
			if (idEnabled == false) {
				return "<" + tagName + ">";
			} else {
				return "<" + tagName + " id=" + "\"" + tagName + Id + "\"" + ">";
			}
		} // no attributes
	}// getStartTag

	public String getEndTag() {
		if (endTag == false) {
			return "";
		} else {
			return "</" + tagName + ">";
		}
	}// getEndTag

	public void setAttributes(String attributes) {
		this.attributes = " " + this.attributes + attributes;
	}// setAttributes

	public static void resetIds() {
		idCounter = 0;
	}// resetIds

	public String getTagName() {
		return tagName;
	}// getTagName

	public static void enableId(boolean choice) {
		if (choice == false) {
			idEnabled = false;
		} else {
			idEnabled = true;
		}
	}// enableId

	public String genHTML(int indentation) {
		String HTML = "";

		for (int i = 0; i < indentation; i++) {
			HTML = HTML + " ";
		} // indentation

		if (tagName == "img") {
			return genHTML(indentation);
		}

		if (content != null) {
			HTML = HTML + getStartTag() + content.genHTML(0) + getEndTag();
		} else {
			HTML = HTML + getStartTag() + getEndTag();
		} // else

		return HTML;
	}// genHTML

}// TagElement