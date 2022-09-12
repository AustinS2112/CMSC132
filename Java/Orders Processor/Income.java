package processor;

public class Income {
	private double value;

	public Income(double startValue) {
		value = startValue;
	}

	public void add(double value) {
		this.value += value;
	}

	public double getValue() {
		return value;
	}

	public String toString() {
		return Double.toString(value);
	}
}
