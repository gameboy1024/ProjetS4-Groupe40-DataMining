package projets4.basic;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;

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
	public  double[] calculate(long[] inputs, int numPrediction, int distinguisherInt) {
		// This function should be synchronized as there would be multiple
		// running thread to call it.
		// This could cause problem as they all try to assign their input in to
		// R
		LOG.info("Got input array ");
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
		//Integer tempInteger = new Integer(distinguisherInt);
	//	String distinguisher = tempInteger.toString();
		String distinguisher = "1";
		re.eval("library(forecast);");
		re.assign("data" + distinguisher, convertLongsToInts(inputs));
		//System.out.println(re.eval("data" + distinguisher));
		re.eval("arima" + distinguisher + "<-auto.arima(data" + distinguisher + ");");
		re.eval("fcast" + distinguisher + "<-forecast(arima" + distinguisher + ",h=" + numPrediction + ");");
		re.eval("r" + distinguisher + "<-summary(fcast" + distinguisher + ");");
		LOG.info("R operation finished");
		// This line shall be improved in terms of efficiency
		REXP result = re.eval("r" + distinguisher + "[1];");
		System.out.println(result);
		double[] prediction = result.asVector().at(0).asDoubleArray();
		for (double d : prediction) {
			// prediction less than 0 are not allowed!
			if (d < 0) {
				d = 0;
			}
		}
		return prediction;

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
