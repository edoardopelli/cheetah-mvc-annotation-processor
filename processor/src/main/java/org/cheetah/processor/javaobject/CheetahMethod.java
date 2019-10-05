package org.cheetah.processor.javaobject;

import java.util.List;

public class CheetahMethod implements CheetaJavaObject {

	private CheetahModifier modifier;

	// if return type is null then I'll write void as return type;
	private CheetahClass returnType;

	private String name;

	private List<CheetahParameter> params;

	private List<CheetahLine> lines;

	@Override
	public String writeObject() {
		StringBuilder sb = new StringBuilder("\n");
		
		sb.append(modifier.equals(CheetahModifier.DEFAULT)? "" : modifier.name().toLowerCase()).append(" ");
		sb.append(returnType==null? CheetahModifier.VOID.name().toLowerCase() : returnType.getName()).append(" ");
		sb.append(name).append("(");
		
		for (CheetahParameter cheetahParameters : params) {
			sb.append(cheetahParameters.writeObject()).append(",");
		}
		if(sb.toString().endsWith(",")){
			sb = new StringBuilder(sb.substring(0,sb.lastIndexOf(",")));
		}
		
		sb.append("){\n");
		
		for (CheetahLine cheetahLine : lines) {
			sb.append(cheetahLine.writeObject());
		}
		
		sb.append("}\n\n");
		return null;
	}

	public void addParam(CheetahParameter param) {
		this.params.add(param);
	}

	public void addLine(CheetahLine line) {
		this.lines.add(line);
	}

	public CheetahMethod(CheetahModifier modifier, CheetahClass returnType, String name) {
		super();
		this.modifier = modifier;
		this.returnType = returnType;
		this.name = name;
	}

}
