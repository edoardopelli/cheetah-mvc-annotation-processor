package org.cheetah.processor.javaobject;

public class CheetahPackage implements CheetaJavaObject {

	private String _package;
	
	
	public CheetahPackage(String _package) {
		super();
		this._package = _package;
	}


	@Override
	public String writeObject() {
		return _package+";\n\n";
	}

}
