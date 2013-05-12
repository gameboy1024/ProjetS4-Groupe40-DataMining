package projets4.case1.basic;

import java.io.Serializable;
import org.rosuda.JRI.REXP;
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

	public static int[] calculate(long[] inputs, int numPrediction) {
		System.out.println("ARIMA CALCULATION: Got input array ");
		for (long l : inputs) {
			System.out.print(l + ", ");
		}
		System.out.println("");
		if (inputs.length < 6) {
			System.out.println("WARNING: Data length not enough to make predictions!");
			return null;
		}
		re.eval("library(forecast);");
		re.assign("data", convertLongsToInts(inputs));
		// use auto.arima function to forecast
		re.eval("arima<-auto.arima(data);");
		REXP fs = re.eval("fcast<-forecast(arima,h=" + numPrediction + ");");
		re.eval("summary(fcast);");
		System.out.println(fs);
		// I want to get result of forecast and returned it at an array
		double[] forecast = fs.asDoubleArray();
		System.out.println("PREDICTION: "+forecast);
		return null;
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
