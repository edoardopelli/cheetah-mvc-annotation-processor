package org.cheetah.processor.javaobject;

public class CheetahPackage extends CheetahAbstractJavaObject {

	private String _package;
	
	
	public CheetahPackage(String _package) {
		super();
		this._package = _package;
	}


	@Override
	public String writeClass() {
		return "package "+_package+";\n\n";
	}


	@Override
	public String writeShortClass() {
		return _package;
	}
	
	

}
