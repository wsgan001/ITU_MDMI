package supervisedLearning.id3;

import java.util.ArrayList;

public interface ID3Object {
	
	/**
	 * Returns a list (as .class objects) of the different attribute types.
	 * Note that the class label is not added to this list of attributes.
	 * @return
	 */
	public abstract ArrayList<Object> getAttributeList();
	
	/***
	 * Returns the value of an Attribute based on its .class type object.
	 * @param Attribute .class type object of its type
	 * @return
	 * @throws Exception 
	 */
	public abstract Object getAttributeValue(Object Attribute) throws Exception;

	public abstract Object getClasslabel();
	
	public abstract void setClasslabel(Object classLabel);

}
