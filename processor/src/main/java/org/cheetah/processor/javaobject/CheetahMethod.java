package org.cheetah.processor.javaobject;

import java.util.ArrayList;
import java.util.List;

import org.cheetah.processor.javaobject.enums.CheetahModifier;

public class CheetahMethod extends CheetahAbstractJavaObject {

	private CheetahModifier modifier;

	// if return type is null then I'll write void as return type;
	private CheetahClass returnType;

	private String name;

	private List<CheetahParameter> params = new ArrayList<>();

	private List<CheetahLine> lines = new ArrayList<>();
	
	private List<CheetahAnnotation> annotations = new ArrayList<>();
	
	

	@Override
	public String writeClass() {
		StringBuilder sb = new StringBuilder("\n");
		
		for (CheetahAnnotation cheetahAnnotation : annotations) {
			sb.append(cheetahAnnotation.writeClass());
		}
		
		sb.append(modifier.equals(CheetahModifier.DEFAULT)? "" : modifier.name().toLowerCase()).append(" ");
		sb.append(returnType==null? CheetahModifier.VOID.name().toLowerCase() : returnType.writeShortClass()).append(" ");
		sb.append(name).append("(");
		
		for (CheetahParameter cheetahParameters : params) {
			sb.append(cheetahParameters.writeClass()).append(",");
		}
		if(sb.toString().endsWith(",")){
			sb = new StringBuilder(sb.substring(0,sb.lastIndexOf(",")));
		}
		
		sb.append("){\n");
		
		for (CheetahLine cheetahLine : lines) {
			sb.append(cheetahLine.writeClass());
		}
		
		sb.append("}\n\n");
		return sb.toString();
	}

	public void addParam(CheetahParameter param) {
		this.params.add(param);
	}

	public void addLine(CheetahLine line) {
		this.lines.add(line);
	}
	
	public void addAnnotation(CheetahAnnotation a) {
		this.annotations.add(a);
	}

	public CheetahMethod(CheetahModifier modifier, CheetahClass returnType, String name) {
		super();
		this.modifier = modifier;
		this.returnType = returnType;
		this.name = name;
	}

	public CheetahMethod(CheetahModifier modifier, String name) {
		this(modifier,null,name);
	}

}
