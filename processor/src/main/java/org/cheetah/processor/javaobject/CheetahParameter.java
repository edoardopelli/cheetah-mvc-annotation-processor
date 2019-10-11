package org.cheetah.processor.javaobject;

import java.util.ArrayList;
import java.util.List;

public class CheetahParameter extends CheetahAbstractJavaObject {

	private CheetahClass type;
	
	private List<CheetahAnnotation> annotations = new ArrayList<>();
	
	public CheetahParameter(CheetahClass type, String name) {
		super();
		this.type = type;
		this.name = name;
	}
	private String name; 
	@Override
	public String writeClass() {
		StringBuilder sb = new StringBuilder();
		for (CheetahAnnotation cheetahAnnotation : annotations) {
			sb.append(cheetahAnnotation.writeClass());
		}
		return sb.append(type.writeShortClass()).append(" ").append(name).toString();
	}
	
	public CheetahParameter addAnnotation(CheetahAnnotation a) {
		this.annotations.add(a);
		return this;
		
	}

}
