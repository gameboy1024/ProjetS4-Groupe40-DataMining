package projets4.case1.basic;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.rosuda.JRI.Rengine;

import projets4.case1.bolt.ArimaBolt;

/**
 * This class offer static access to arima calculation prediction
 * 
 * @author sbt
 * 
 */
public class Arima implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8446995474203141126L;
	private static Rengine re;
	private static final Logger LOG = Logger.getLogger(Arima.class);

	/**
	 * The engine should be initiated
	 */
	public Arima() {
		Arima.re = new Rengine(null, false, null);
		if (!re.waitForR()) {
			System.err.println("Cannot load R");
			return;
		}
		LOG.info("Arima engine loaded!");
	}

	/**
	 * Calculate the arima prediction based on given inputs
	 * 
	 * @param inputs
	 *            A table of long as inputs
	 * @param numPrediction
	 *            The number of wanted predictive numbers
	 * @return A table of double filled with predictive numbers
	 */
	public static double[] calculate(long[] inputs, int numPrediction) {
		// This function should be synchronized as there would be multiple running thread to call it.
		// This could cause problem as they all try to assign their input in to R
		synchronized (re) {
			LOG.info("ARIMA CALCULATION: Got input array ");
			for (long l : inputs) {
				System.out.print(l + ", ");
			}
			System.out.println("");
			if (inputs.length < 6) {
				// While the length is less than 6, arima in R will not work,
				// however, during the setup periode, it's possible to have less
				// entries
				LOG.warn("WARNING: Input length not enough to make predictions!");
				return null;
			}

			re.eval("library(forecast);");
			re.assign("data", convertLongsToInts(inputs));
			re.eval("arima<-auto.arima(data);");
			re.eval("fcast<-forecast(arima,h=" + numPrediction + ");");
			re.eval("r<-summary(fcast);");
			LOG.info("R operation finished");
			// This line shall be improved in terms of efficiency
			double[] prediction = re.eval("r[1];").asVector().at(0)
					.asDoubleArray();
			for (double d : prediction) {
				// prediction less than 0 are not allowed!
				if (d < 0) {
					d = 0;
				}
			}
			return prediction;
		}

	}

	/**
	 * Convert an array of long into a table of int
	 * 
	 * @param input
	 * @return
	 */
	private static int[] convertLongsToInts(long[] input) {
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
