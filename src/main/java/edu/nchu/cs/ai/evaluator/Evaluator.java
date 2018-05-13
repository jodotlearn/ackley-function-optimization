package edu.nchu.cs.ai.evaluator;

public interface Evaluator<T,V> {
	public V evaluate(T solution);
}
