package org.cheetah.processor.javaobject;

public interface CheetahJavaObject {

	
	/**
	 * Define a method for writing the complete class
	 * @return
	 */
	String writeClass();
	
	
	/**
	 * Define a method for writing only the class name (with classes inside diamonds if exists)
	 * @return
	 */
	String writeShortClass();
}
