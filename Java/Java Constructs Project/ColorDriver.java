package enumeratedTypes;

public class ColorDriver {

	public static void main(String[] args) {
		/* Possible values */
		for (Color color : Color.values()) {
			System.out.println(color);
		}

		Color green = Color.Green;
		System.out.println("Name: " + green.name());
		System.out.println("Ordinal: " + green.ordinal());

		if (Color.Red.compareTo(Color.Blue) < 0) {
			System.out.println("Red precedes Blue");
		} else {
			System.out.println("Blue precedes Red");
		}

		/* We can use equals method to compare them */
	}
}