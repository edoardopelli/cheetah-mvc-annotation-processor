package org.cheetah.spring.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(CLASS)
@Target(TYPE)
public @interface CheetahSpring {

	/**
	 * Package's name  from which all classes will be created
	 * @return
	 */
	String pkgroot();

	Type type()  default Type.ALL;
	
	String entity();
	
	
	/**
	 * The http path of the rest service
	 * @return
	 */
	String httprest();
	
	/**
	 * The http root path
	 * @return
	 */
	String httproot() default "/";

	CheetahMapping[] mappings(); 
	
	
}
