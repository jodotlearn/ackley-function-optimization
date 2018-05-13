package edu.nchu.cs.ai.transitor;

import java.util.List;

import edu.nchu.cs.ai.bean.Particle;
import edu.nchu.cs.ai.bean.Swarm;

public class SwarmTransitor implements Transitor<Swarm>{
	private Swarm swarm;

	@Override
	public Swarm transit() {
		List<Particle> particles = this.swarm.getParticles();
		for(Particle particle:particles) {
			particle.updateVelocity();
			particle.move();
		}
		return this.swarm;
	}


	@Override
	public void setCurrent(Swarm current) {
		this.swarm = current;
	}


	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

}
