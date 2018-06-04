package edu.nchu.cs.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.nchu.cs.ai.algorithms.ParticleSwarmOptimization;
import edu.nchu.cs.ai.evaluator.AckleyFunctionEvaluator;
import edu.nchu.cs.ai.solution.OptimumSolution;
import edu.nchu.cs.ai.solution.Solution;
import edu.nchu.cs.ai.transitor.SwarmTransitor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * An application for testing ParticleSwarmOptimization algorithm.
 * 1. running and collecting the objective every iteration.
 * 2. averaging the value per iteration.
 * 3. draw the convergence chart.
 * @author Jo
 *
 */
public class AckleyFunctionOptimization extends Application{
	private static String winTitle;
	private static String chartTitle;
	private static String axisXName;
	private static String axisYName;
	private static double width;
	private static double height;
	private static Map<String,List<Double>> data;

	/**
	 * java -jar pso.jar swarm_size dimension position_limit velocity_limit iterations run_times
	 * sample:
	 * 	swarm size is 100
	 * 	dimension is 2
	 * 	position range is between -100 ~ 100
	 * 	velocity range is -1.5 ~ 1.5
	 * 	iteration 500 times
	 * 	run 30 times to calculate the average
	 * java -jar pso.jar 100 2 100 1.5 1000 30
	 */
	public static void main( String[] args ){
		int particleCount = Integer.valueOf(args[0]);
		int dimension = Integer.valueOf(args[1]);
		double positionLimit = Double.valueOf(args[2]);
		double velocityLimit = Double.valueOf(args[3]);
		int iteration = Integer.valueOf(args[4]);
		int runTimes = Integer.valueOf(args[5]);

		List<List<Solution>> totalDetail = new ArrayList<>();
		Map<String, List<Double>> chartData = new HashMap<>();
		List<Double> tmp;
		int cnt = 0;
		while (cnt < runTimes) {
			ParticleSwarmOptimization pso = new ParticleSwarmOptimization(iteration, particleCount, dimension, positionLimit, velocityLimit);
			pso.setTransitor(new SwarmTransitor());
			pso.setEvaluator(new AckleyFunctionEvaluator());
			OptimumSolution os = pso.run();
			totalDetail.add(os.getExecuteDetail());
			cnt++;
		}
		List<Double> avgData = new ArrayList<>();
		for (int i=0;i<totalDetail.get(0).size();i++) {
			tmp = new ArrayList<>();
			for (int j=0;j<totalDetail.size();j++) {
				tmp.add(totalDetail.get(j).get(i).getObjectiveValue());
			}
			avgData.add(tmp.stream().mapToDouble(val -> val).average().getAsDouble());
		}
		chartData.put("pso", avgData);
		winTitle = "Convergence Chart";
		width = 800;
		height = 600;
		chartTitle = particleCount + " particles";
		axisXName = "Iteration";
		axisYName = "Objective Value";
		data = chartData;
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(this.winTitle);
		//defining the axis
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel(this.axisXName);
		yAxis.setLabel(this.axisYName);

		//creating the chart
		final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

		lineChart.setTitle(this.chartTitle);
		Iterator<String> ite = this.data.keySet().iterator();
		while(ite.hasNext()) {
			String seriesName = ite.next();
			XYChart.Series<Number,Number> series = new XYChart.Series<>();
			series.setName(seriesName);
			List<Double> seriesData = this.data.get(seriesName);
			for (int i=0;i<seriesData.size();i++) {
				series.getData().add(new XYChart.Data<Number,Number>(i+1, seriesData.get(i)));
			}
			lineChart.getData().add(series);
		}
		//not show node
		for (XYChart.Series<Number,Number> series:lineChart.getData()) {
			for (XYChart.Data<Number,Number> data:series.getData()) {
				data.getNode().setVisible(false);
			}
		}
		Scene scene  = new Scene(lineChart,this.width,this.height);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
