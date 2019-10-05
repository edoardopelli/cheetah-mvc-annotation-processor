package org.cheetah.processor.javaobject;

public class CheetahParameter extends CheetahAbstractJavaObject {

	private CheetahClass type;
	
	public CheetahParameter(CheetahClass type, String name) {
		super();
		this.type = type;
		this.name = name;
	}
	private String name; 
	@Override
	public String writeClass() {
		return new StringBuilder().append(type.writeShortClass()).append(" ").append(name).toString();
	}

}
