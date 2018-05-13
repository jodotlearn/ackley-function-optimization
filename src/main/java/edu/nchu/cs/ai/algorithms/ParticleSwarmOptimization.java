package edu.nchu.cs.ai.algorithms;

import java.util.ArrayList;
import java.util.List;

import edu.nchu.cs.ai.bean.Particle;
import edu.nchu.cs.ai.bean.Swarm;
import edu.nchu.cs.ai.evaluator.Evaluator;
import edu.nchu.cs.ai.solution.OptimumSolution;
import edu.nchu.cs.ai.solution.PositionSolution;
import edu.nchu.cs.ai.solution.Solution;
import edu.nchu.cs.ai.transitor.Transitor;

/**
 * Particle Swarm Optimization
 * @author Jo
 *
 */
public class ParticleSwarmOptimization {
	private int particleCount;
	private int dimension;
	private double positionLimit;
	private double velocityLimit;
	private int iteration;
	private Transitor transitor;
	private Evaluator evaluator;

	public ParticleSwarmOptimization(int iteration, int particleCount, int dimension, double positionLimit, double velocityLimit) {
		this.iteration = iteration;
		this.particleCount = particleCount;
		this.dimension = dimension;
		this.positionLimit = positionLimit;
		this.velocityLimit = velocityLimit;
	}

	public void setTransitor(Transitor transitor) {
		this.transitor = transitor;
	}

	public void setEvaluator(Evaluator evaluator) {
		this.evaluator = evaluator;
	}

	public OptimumSolution run() {
		int cnt = 0;
		Swarm swarm = new Swarm(this.particleCount, this.dimension, this.positionLimit, this.velocityLimit);
		OptimumSolution os = new OptimumSolution();
		List<Solution> detail = new ArrayList<>();
		Solution bestSolution = null;
		while (cnt <= this.iteration) {
			this.transitor.setCurrent(swarm);
			//evaluation
			double score = 0;
			for (Particle p:swarm.getParticles()) {
				score = (double) this.evaluator.evaluate(p.getPosition());
				if (score < p.getBestScore()) {
					p.setBestScore(score);
					p.setBestPosition(p.getPosition());
				}
				//determination
				Solution solution = new PositionSolution(swarm.getBestPosition());
				solution.setObjectiveValue(swarm.getBestScore());
				if (score < swarm.getBestScore()) {
					swarm.setBestScore(score);
					swarm.setBestPosition(p.getBestPosition());
					bestSolution = solution;
					os.setSolution(bestSolution);
				}
			}
			detail.add(bestSolution);
			//transition
			this.transitor.transit();
			cnt++;
		}
		os.setExecuteDetail(detail);
		return os;
	}

}
