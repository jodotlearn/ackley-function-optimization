package edu.nchu.cs.ai.bean;

import java.util.Arrays;
import java.util.Random;

import edn.nchu.cs.utils.RandomUtil;

public class Particle {
	private Swarm swarm;
	private double[] position;
	private double[] velocity;
	private double[] bestPosition;
	private double score;
	private double bestScore;

	public Particle() {
		this.bestScore = Double.MAX_VALUE;
	}

	public void setSwarm(Swarm swarm) {
		this.swarm = swarm;
	}

	public Swarm getSwarm() {
		return this.swarm;
	}

	public void setPosition(double[] position) {
		this.position = Arrays.copyOf(position, position.length);
	}

	public double[] getPosition() {
		return this.position;
	}

	public void setVelocity(double[] velocity) {
		this.velocity = Arrays.copyOf(velocity, velocity.length);
	}

	public double[] getVelocity() {
		return this.velocity;
	}

	public void setBestPosition(double[] position) {
		this.bestPosition = Arrays.copyOf(position, position.length);
	}

	public double[] getBestPosition() {
		return this.bestPosition;
	}

	public void updateVelocity() {
		double w = 1.0;
		double c1 = 0.5;
		double c2 = 0.5;
		double[] gb = this.getSwarm().getBestPosition();
		Random rnd = new Random();
		double velocityLimit = this.swarm.getVelocityLimit();
		for (int i=0;i<this.velocity.length;i++) {
			double velocity = w * this.velocity[i]
					+ c1 * rnd.nextDouble() * (this.bestPosition[i] - this.position[i])
					+ c2 * rnd.nextDouble() * (gb[i] - this.position[i]);
			if (velocity > velocityLimit || velocity < -velocityLimit) {
				velocity = RandomUtil.rand(-velocityLimit, velocityLimit);
			}
			this.velocity[i] = velocity;
		}
	}

	public void move() {
		double[] newP = new double[this.position.length];
		double positionLimit = this.swarm.getPositionLimit();
		for (int i=0;i<this.position.length;i++) {
			this.position[i] += this.velocity[i];
			if (this.position[i] > positionLimit || this.position[i] < -positionLimit) {
				this.position[i] = RandomUtil.rand(-positionLimit, positionLimit);
			}
		}
	}

	public void setScore(double score) {
		this.score = score;
	}
	public double getScore() {
		return this.score;
	}
	public void setBestScore(double score) {
		this.bestScore = score;
	}

	public double getBestScore() {
		return this.bestScore;
	}

}
