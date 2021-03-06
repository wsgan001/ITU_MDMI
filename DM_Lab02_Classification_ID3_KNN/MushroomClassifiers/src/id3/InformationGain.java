package id3;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * 
 * @author Theresa Brandt von Fackh
 *
 */
public class InformationGain implements Comparator<InformationGain>, Comparable<InformationGain>{

	private Object attribute;
	private Double gain;

	public InformationGain(ArrayList<ID3Object> objects, Object classifierAttribute, Object attribute) {
		super();
		this.gain = this.gain(objects, classifierAttribute, attribute);
		this.attribute = attribute;
	}

	/**
	 * 
	 * @param objects
	 * @param classifierAttribute
	 * @param attribute
	 * @return
	 */
	public double gain(ArrayList<ID3Object> objects, Object classifierAttribute, Object attribute){
		double info = info(objects,classifierAttribute);
		double info_A = info_A(objects,classifierAttribute,attribute);
		return info-info_A;	
	}
	
	/**
	 * 
	 * @param objects
	 * @param classifierAttribute
	 * @return
	 */
	private double info(ArrayList<ID3Object> objects, Object classifierAttribute){
		Buckets buckets = new Buckets(objects, classifierAttribute);
		double result = 0.0;
		for (ArrayList<ID3Object> bucket: buckets.getBuckets().values()){
			double temp = ((double)bucket.size()/(double)buckets.getCounter());
			result -= temp*log(temp,2);
		}
		return result;
	}
	
	/**
	 * 
	 * @param objects
	 * @param classifierAttribute
	 * @param attribute
	 * @return
	 */
	private double info_A(ArrayList<ID3Object> objects, Object classifierAttribute, Object attribute){	
		Buckets buckets = new Buckets(objects, attribute);
		double result = 0.0;
		for(Object classifier: buckets.getBuckets().keySet()){
			ArrayList<ID3Object> selection = buckets.getBuckets().get(classifier);
			double ratio = (double)selection.size()/(double)buckets.getCounter();
			double info = (double)info(selection,classifierAttribute);
			result += ratio*info;
		}
		return result;
	}

	/**
	 * Logarithms function
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private double log(double a, double b){	
		return Math.log(a)/Math.log(b);
	}
	
	@Override
	public int compareTo(InformationGain arg0) {
		return (this.gain).compareTo(arg0.gain);
	}

	@Override
	public int compare(InformationGain arg0, InformationGain arg1) {
		if (arg0.gain > arg1.gain){
			return 1;
		}
		else if (arg0.gain < arg1.gain){
			return -1;
		}
		else return 0;
	}
	
	public double getGain() {
		return gain;
	}
	
	public void setGain(double gain) {
		this.gain = gain;
	}
	
	public Object getAttribute() {
		return attribute;
	}
	
	public void setAttribute(Object attribute) {
		this.attribute = attribute;
	}

	@Override
	public String toString() {
		return attribute + ": " + gain;
	}	
	
}
