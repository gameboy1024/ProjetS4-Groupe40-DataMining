package projets4.case1.basic;

import java.io.Serializable;
import java.util.Arrays;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RList;
import org.rosuda.JRI.RVector;
import org.rosuda.JRI.Rengine;

public class Arima implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8446995474203141126L;
	private static Rengine re;

	public Arima() {
		Arima.re = new Rengine(null, false, null);
		if (!re.waitForR()) {
			System.err.println("Cannot load R");
			return;
		}
		
	}

	public static double[] calculate(long[] inputs, int numPrediction) {
		System.out.println("ARIMA CALCULATION: Got input array ");
		for (long l : inputs) {
			System.out.print(l + ", ");
		}
		System.out.println("");
		if (inputs.length < 6) {
			// While the length is less than 6, arima in R will not work,
			// however, during the setup periode, it's possible to have less entries
			System.out.println("WARNING: Input length not enough to make predictions!");
			return null;
		}
		re.eval("library(forecast);");
		re.assign("data", convertLongsToInts(inputs));
		re.eval("arima<-auto.arima(data);");
		re.eval("fcast<-forecast(arima,h=" + numPrediction + ");");
		re.eval("r<-summary(fcast);");
		double[] prediction = re.eval("r[1];").asVector().at(0).asDoubleArray();
		for (double d : prediction) {
			//prediction less than 0 are not allowed!
			if (d < 0) { 
				d = 0;
			}
		}
		return prediction;
	}

	public static int[] convertLongsToInts(long[] input) {
		if (input == null) {
			return null;
		}
		int[] output = new int[input.length];
		for (int i = 0; i < input.length; i++) {
			output[i] = (int) input[i];
		}
		return output;
	}
}
