package org.cheetah.processor.javaobject;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CheetahAnnotation extends CheetahAbstractJavaObject{
	

	private String type;
	
	private Map<String, String> attributes = new HashMap<>();
	

	public CheetahAnnotation(String type) {
		super();
		this.type = type;
	}


	@Override
	public String writeClass() {
		return writeShortClass()+"\n";
	}


	/**
	 * write the annotation without  CRLF
	 */
	@Override
	public String writeShortClass() {
		StringBuffer sb = new StringBuffer();
		System.out.println("===================================");
		System.out.println("===================================");
		Set<Entry<String, String>> set = attributes.entrySet();
		for (Entry<String, String> entry : set) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key).append("=").append(value).append(",");
		}
		System.out.println("===================================");
		System.out.println("===================================");
		String s = "";
		if(sb.lastIndexOf(",")!=-1) {
			s=sb.toString().substring(0,sb.toString().lastIndexOf(","));
		}
		return "@"+type+"("+s+")";
	}
	
	public CheetahAnnotation addAttribute(String name,String value){
		this.attributes.put(name, value);
		return this;
	}

}
