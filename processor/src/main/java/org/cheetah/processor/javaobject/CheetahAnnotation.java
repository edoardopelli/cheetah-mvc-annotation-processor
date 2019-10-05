package org.cheetah.processor.javaobject;

public class CheetahAnnotation extends CheetahAbstractJavaObject{
	

	private String type;
	

	public CheetahAnnotation(String type) {
		super();
		this.type = type;
	}


	@Override
	public String writeClass() {
		return "@"+type+"\n";
	}

}
