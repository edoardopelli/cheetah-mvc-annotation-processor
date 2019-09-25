package org.cheetah.processor.support;

public class CField {

	private String name;

	private String type;
	
	private String code="";

	public CField(String name, String type) {
		super();
		this.name = name;
		this.type = type;
		
		this.code+="private "+this.type+" "+this.name+";";
	}

	public String getCode() {
		return code;
	}


}
