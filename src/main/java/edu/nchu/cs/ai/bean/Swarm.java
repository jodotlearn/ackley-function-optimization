package edu.nchu.cs.ai.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edn.nchu.cs.utils.RandomUtil;

public class Swarm {
	private int swarmSize;
	private int dimension;
	private double positionLimit;
	private double velocityLimit;
	private List<Particle> particles;
	private double bestScore;
	private double[] bestPosition;

	public Swarm(int swarmSize, int dimension, double positionLimit, double velocityLimit) {
		this.swarmSize = swarmSize;
		this.dimension = dimension;
		this.positionLimit = positionLimit;
		this.velocityLimit = velocityLimit;
		this.bestScore = Double.MAX_VALUE;
		this.particles = new ArrayList<>();
		this.initialize();
	}
	public double getPositionLimit() {
		return this.positionLimit;
	}
	public double getVelocityLimit() {
		return this.velocityLimit;
	}
	public List<Particle> getParticles(){
		return this.particles;
	}

	public void setBestScore(double score) {
		this.bestScore = score;
	}

	public double getBestScore() {
		return this.bestScore;
	}

	public void setBestPosition(double[] position) {
		this.bestPosition = Arrays.copyOf(position, position.length);
	}

	public double[] getBestPosition() {
		return this.bestPosition;
	}

	private void initialize() {
		for (int i=0;i<this.swarmSize;i++) {
			Particle p = new Particle();
			double[] position = new double[this.dimension];
			double[] velocity = new double[this.dimension];
			for (int j=0;j<this.dimension;j++) {
				position[j] = RandomUtil.rand(-this.positionLimit, this.positionLimit);
				velocity[j] = RandomUtil.rand(-this.velocityLimit, this.velocityLimit);
			}
			p.setPosition(position);
			p.setBestPosition(position);
			p.setVelocity(velocity);
			p.setSwarm(this);
			this.particles.add(p);
		}
	}

}
