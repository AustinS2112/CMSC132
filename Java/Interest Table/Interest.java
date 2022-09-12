package Implementation;

import java.math.*;

public class Interest {

	public static double InterestComp(double principal, double rate, double years, String type) {
		if (type == "simple") {
			double answer1 = principal + (principal * (rate / 100) * years);
			return answer1;
		} else if (type == "compound") {
			double answer2 = principal * Math.pow((1 + rate / 100), years);
			return answer2;
		}
		return 0.0;
	}// InterestComp

}// Interest
