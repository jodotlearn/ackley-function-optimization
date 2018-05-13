package edu.nchu.cs.ai.solution;

import edn.nchu.cs.utils.StringUtil;

public class PositionSolution extends Solution<double[]>{

	public PositionSolution(double[] answer) {
		super(answer);
	}

	@Override
	public String toString() {
		return StringUtil.toString(this.getAns());
	}


}
