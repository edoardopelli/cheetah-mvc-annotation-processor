package org.cheetah.processor.utils;

public class Utils {

	public static String getClassName(String qualifierName) {
		
		return qualifierName.substring(qualifierName.lastIndexOf(".")+1);
		
	}
}
