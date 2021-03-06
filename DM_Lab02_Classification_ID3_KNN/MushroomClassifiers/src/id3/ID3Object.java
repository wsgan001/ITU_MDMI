package id3;

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
	 */
	public abstract Object getAttributeValue(Object Attribute);

	public abstract iClass_Label getClasslabel();
	
	public abstract void setClasslabel(iClass_Label classLabel);

}
