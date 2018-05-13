package edn.nchu.cs.utils;

import java.util.Random;

public class RandomUtil {
	public static double rand(double min, double max) {
		Random rnd = new Random();
		double range = max - min;
		return (min + rnd.nextDouble() * range);
	}
}
