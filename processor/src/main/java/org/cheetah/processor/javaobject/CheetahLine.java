package org.cheetah.processor.javaobject;

public class CheetahLine extends CheetahAbstractJavaObject {
	
	private String line;

	public CheetahLine(String line) {
		super();
		this.line = line;
	}

	@Override
	public String writeClass() {
		return line+";\n";
	}

}
