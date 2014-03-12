import java.util.*;

import data.Class_Label;

public class kNNAlgorithm {
	
	//k-nearest neighbour function
	public static void nearestNeighbour(ArrayList<Mushroom> testSet, ArrayList<Mushroom> trainingSet, int k) throws Exception{
		int warnings = 0;
		for (Mushroom testMushroom : testSet){
			System.out.println("---");
			trainingSet.remove(testMushroom); // removing mushroom to test from the trainingset
			int row = 0;
			ArrayList<Neighbour> distances = new ArrayList<Neighbour>();
			//System.out.println("TrainingSet: "+trainingSet.size());
			
			// Calculate the distance from the mushroom to test to each other mushroom in the traiingset
			for (Mushroom trainingMushroom : trainingSet){
				row++;
				int distance = distance(testMushroom, trainingMushroom);
				distances.add(new Neighbour(trainingMushroom,distance,row));
			}
			
			// Sort the mushrooms to their distance to the mushroom to test
			Collections.sort(distances);
			//System.out.println("Distances: "+distances);
			
			// Select the k-nearest neighbours and count how many edible/poisonous mushrooms there are
			int[] classOccurances = rateKNearestNeighbours(distances, k);
			
			// Print prediction and real value
			warnings += printResult(classOccurances,testMushroom);

			// Adding mushroom to test back to the training set, so it can be used for the next mushroom to test
			trainingSet.add(testMushroom);
		}
		System.out.println("MISINTERPRETATION: "+warnings);
	}
	
	// Calculates the distance between two mushrooms
	// A high value indicates a high distance (inequality)
	private static int distance (Mushroom a, Mushroom b){
		ArrayList<Object> attributes = Mushroom.getAttributeList();
		int sumDiff = 0;
		for (Object attribute : attributes){
			if (a.getAttributeValue(attribute) != b.getAttributeValue(attribute))
				sumDiff++;
		}
		return sumDiff;
	}
	
	// Counts occurances of classes within the k nearest neighbours
	// Return array of counts for each class
	private static int[] rateKNearestNeighbours(ArrayList<Neighbour> distances, int k) throws Exception{
		int[] result = {0, 0};
		for (int i = 0; i < k; i++){
			Neighbour neighbour = distances.get(i);
			if (neighbour.getMushroom().m_Class.equals(Class_Label.edible))
				result[0]++;
			else if (neighbour.getMushroom().m_Class.equals(Class_Label.poisonous))
				result[1]++;
			else throw new Exception("Error");
		}
		return result;
	}
	
	// Print prediction and real value for a mushroom
	// Returns 1 if the prediction and the real value are a mismatch
	private static int printResult(int[] classOccurances, Mushroom mushroom){
		System.out.println("E: "+classOccurances[0] + "; P: " + classOccurances[1]);
		Class_Label prediction = Class_Label.poisonous;
		if (classOccurances[0] > classOccurances[1])
			prediction = Class_Label.edible;			
		System.out.println("Prediction: "+prediction);
		System.out.println("Real value: "+mushroom.m_Class);			
		if (mushroom.m_Class != prediction){
			System.out.println("---------------------> WARNING");
			return 1;
		}
		return 0;
	}
	
}

//Helper-Class for k-NearestNeighbour Function
class Neighbour implements Comparator<Neighbour>, Comparable<Neighbour>{
	private Integer distance;
	private Mushroom mushroom;
	private int row;
	
	public Neighbour(Mushroom mushroom, int distance, int row){
		this.mushroom = mushroom;
		this.distance = distance;
		this.row = row;
	}
	
	@Override
	public int compare(Neighbour arg0, Neighbour arg1) {
		if (arg0.distance > arg1.distance){
			return 1;
		}
		else if (arg0.distance < arg1.distance){
			return -1;
		}
		else return 0;
	}

	@Override
	public int compareTo(Neighbour arg0) {
		return (this.distance).compareTo(arg0.distance);
	}
	
	public Mushroom getMushroom(){
		return this.mushroom;
	}
	
	public int getDistance(){
		return this.distance;
	}
	
	public int getRow(){
		return this.row;
	}
	
	@Override
	public String toString(){
		return distance.toString();
	}
}
