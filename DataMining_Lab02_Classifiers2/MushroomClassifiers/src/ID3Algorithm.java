import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.jar.Attributes;

import data.Class_Label;


public class ID3Algorithm {
	
	
	public static void generateDecisionTree(){
		Node node = new Node("root1");
	}	
	
	private double info(ArrayList<Mushroom> mushrooms, Object classifier){
		myClass buckets = bucketing(mushrooms, classifier);
		double result = 0;
		for (ArrayList<Mushroom> bucket: buckets.getBuckets().values()){
			double temp = (bucket.size()/buckets.getCounter());
			result -= temp*log(temp,2);
		}
		return result;
	}
	
	
	private double info_A(ArrayList<Mushroom> mushrooms, Object classifierAttribute){	
		myClass buckets = bucketing(mushrooms, classifierAttribute);
		double result = 0;
		for(Object classifier: buckets.getBuckets().keySet()){
			result += (buckets.getBuckets().get(classifier).size()/buckets.getCounter())*info(buckets.getBuckets().get(classifier),classifier);
		}
		return result;
	}

	public double gain(ArrayList<Mushroom> mushrooms, Object classifierAttribute){
		return info(mushrooms,classifierAttribute)-info_A(mushrooms,classifierAttribute);	
	}
	
	private double log(double a, double b){	
		return Math.log(a)/Math.log(b);
	}
	
	private myClass bucketing(ArrayList<Mushroom> mushrooms, Object classifier){
		HashMap<Object,ArrayList<Mushroom>> buckets = new HashMap<Object,ArrayList<Mushroom>>();	
		int counter = 0;
		for(Mushroom mushroom : mushrooms){
			Object attributeValue = mushroom.getAttributeValue(classifier);
			if (buckets.containsKey(attributeValue)){
				ArrayList<Mushroom> list = buckets.get(attributeValue);
				list.add(mushroom);
				buckets.put(attributeValue,list);
			}
			else
			{
				ArrayList<Mushroom> list = new ArrayList<Mushroom>();
				list.add(mushroom);
				buckets.put(attributeValue,list);
			}
			counter++;
		}
		return new myClass(counter, buckets);
	}
	
}


class myClass{
	private int counter;
	private HashMap<Object,ArrayList<Mushroom>> buckets;
	
	public myClass(int counter, HashMap<Object, ArrayList<Mushroom>> buckets) {
		this.counter = counter;
		this.buckets = buckets;
	}

	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public HashMap<Object, ArrayList<Mushroom>> getBuckets() {
		return buckets;
	}
	
	public void setBuckets(HashMap<Object, ArrayList<Mushroom>> buckets) {
		this.buckets = buckets;
	}
}


class Node {
	
	private String name;

	private ArrayList<Node> children;
	
	public Node(String name){
		this.name = name;
		this.children = new ArrayList<Node>();
	}
	
	public Node(String name, ArrayList<Node> children){
		this.name = name;
		this.children = children;
	}
	
	public void addChild(Node child){
		this.children.add(child);
	}
	
	public void addChild(ArrayList<Node> children){
		this.children.addAll(children);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Node> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}
}