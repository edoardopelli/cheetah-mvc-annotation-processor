package org.cheetah.processor.support;

import org.apache.commons.lang3.StringUtils;

public class CMethod {

	private String name;

	private String setMethod="";
	
	private String getMethod="";
	
	private String parameterType;
	
	public CMethod(String name,String parameterType) {
		super();
		this.name = name;
		this.parameterType=parameterType;
		
		setMethod+="public void set"+StringUtils.capitalize(this.name)+"("+this.parameterType+" "+name+") { this."+name+"="+name+";}"; 
		getMethod+="public "+this.parameterType+" get"+StringUtils.capitalize(this.name)+"() { return "+this.name+";}"; 
		
		
	}

	
	public String getCode() {
		return setMethod+"\n\r"+getMethod;
	}
	
	
	
	
}
