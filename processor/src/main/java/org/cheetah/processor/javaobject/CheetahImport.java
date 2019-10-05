package org.cheetah.processor.javaobject;

public class CheetahImport extends CheetahAbstractJavaObject {

	private String _import;
	
	
	
	public CheetahImport(String text) {
		super();
		this._import = text;
	}



	@Override
	public String writeClass() {
		return "import "+_import+";\n\n";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_import == null) ? 0 : _import.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CheetahImport other = (CheetahImport) obj;
		if (_import == null) {
			if (other._import != null)
				return false;
		} else if (!_import.equals(other._import))
			return false;
		return true;
	}

}
