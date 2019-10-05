package org.cheetah.processor.javaobject;

public class CheetahParameter implements CheetaJavaObject {

	private CheetahClass type;
	
	public CheetahParameter(CheetahClass type, String name) {
		super();
		this.type = type;
		this.name = name;
	}
	private String name; 
	@Override
	public String writeObject() {
		return new StringBuilder().append(type).append(" ").append(name).toString();
	}

}
