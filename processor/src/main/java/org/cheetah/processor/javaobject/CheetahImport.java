package org.cheetah.processor.javaobject;

public class CheetahImport implements CheetaJavaObject {

	private String _import;
	
	
	
	public CheetahImport(String text) {
		super();
		this._import = text;
	}



	@Override
	public String writeObject() {
		return _import+";\n";
	}

}
