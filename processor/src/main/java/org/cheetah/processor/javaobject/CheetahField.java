package org.cheetah.processor.javaobject;

import java.util.ArrayList;
import java.util.List;

import org.cheetah.processor.javaobject.enums.CheetahModifier;

public class CheetahField extends CheetahAbstractJavaObject {
	
	private String name;
	
	private String type;
	
	private CheetahModifier modifier;
	
	private List<CheetahAnnotation> annotations = new ArrayList<>();
	
	private boolean isStatic = false;
	
	private boolean isFinal = false;

	public CheetahField(String name, String type, CheetahModifier modifier) {
		super();
		this.name = name;
		this.type = type;
		this.modifier = modifier;
	}

	@Override
	public String writeClass() {
		StringBuilder sb = new StringBuilder();
		for (CheetahAnnotation cheetahAnnotation : annotations) {
			sb.append(cheetahAnnotation.writeClass());
		}
		sb.append(modifier.equals(CheetahModifier.DEFAULT)? "" : modifier.toString().toLowerCase()).append(" ");
		sb.append(isStatic()? " static " : "");
		sb.append(isFinal()? " final " : "");
		sb.append(getType()).append(" ");
		sb.append(isFinal()?getName().toUpperCase():getName()).append(";\n\n");
		return sb.toString();
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public CheetahModifier getModifier() {
		return modifier;
	}

	public List<CheetahAnnotation> getAnnotations() {
		return annotations;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	
	public void addAnnotation(CheetahAnnotation annotation ) {
		this.annotations.add(annotation);
	}

}
