package org.cheetah.processor.support;

public class CField {

	private String name;

	private String type;
	
	private String code="";

	private CMapping mapping;


	public CField(String name, String type) {
		super();
		this.name = name;
		this.type = type;
		
		this.code+="private "+this.type+" "+this.name+";";
	}

	public String getCode() {
		return code;
	}

	public String getType() {
		return type;
	}

	public void setMapping(CMapping mapping) {
		this.mapping=mapping;
	}

	public CMapping getMapping() {
		return mapping;
	}

}
