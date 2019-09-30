package org.cheetah.processor.support;

public class CMapping {

	private String target;
	private String source;

	/**
	 * 
	 * @param source the entity field name
	 * @param target the dto field name
	 */
	public CMapping(String source, String target) {
		this.source=source;
		this.target=target;
	}

	
	public String getCode() {
		return "@Mapping(target=\""+target+"\", source=\""+source+"\")";
	}
	public String getReverseCode() {
		return "@Mapping(source=\""+target+"\", target=\""+source+"\")";
	}
}
