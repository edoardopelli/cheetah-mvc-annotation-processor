package org.cheetah.processor.javaobject;

public class CheetahAnnotation implements CheetaJavaObject{
	

	private String type;
	

	public CheetahAnnotation(String type) {
		super();
		this.type = type;
	}


	@Override
	public String writeObject() {
		return "@"+type+"\n";
	}

}
