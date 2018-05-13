package edu.nchu.cs.ai.evaluator;

public class AckleyFunctionEvaluator implements Evaluator<double[],Double>{

	@Override
	public Double evaluate(double[] solution) {
		double sum1 = 0.0;
		double sum2 = 0.0;

		for (int i = 0 ; i < solution.length; i++) {
			sum1 += (solution[i] * solution[i]);
			sum2 += (Math.cos(2*Math.PI*solution[i]));
		}
		return (-20.0 * Math.exp(-0.2 * Math.sqrt(sum1 / ((double )solution.length))) - Math.exp(sum2 / ((double )solution.length)) + 20.0 + Math.E);
	}

}
