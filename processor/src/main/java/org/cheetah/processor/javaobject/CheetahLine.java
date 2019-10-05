package org.cheetah.processor.javaobject;

public class CheetahLine implements CheetaJavaObject {
	
	private String line;

	public CheetahLine(String line) {
		super();
		this.line = line;
	}

	@Override
	public String writeObject() {
		return line+";\n";
	}

}
