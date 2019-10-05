package org.cheetah.processor.javaobject;

import java.util.ArrayList;
import java.util.List;

import org.cheetah.processor.javaobject.enums.CheetahClassType;

public class CheetahClass implements CheetaJavaObject {

	private String name;

	private List<CheetahAnnotation> annotations = new ArrayList<>();

	private List<CheetahClass> implementedClasses = new ArrayList<>();;

	private List<CheetahField> fields = new ArrayList<>();;

	private List<CheetahMethod> methods = new ArrayList<>();

	private List<CheetahImport> imports = new ArrayList<>();

	private CheetahClass extendedClass;

	public CheetahClass(String name, CheetahClass extendedClass, CheetahClassType type) {
		super();
		this.name = name;
		this.extendedClass = extendedClass;
		this.type = type;
	}

	private CheetahPackage cheetahPackage;

	private CheetahClassType type;

	public CheetahClass(String name, CheetahClassType type) {
		super();
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CheetahClass> getImplementedClasses() {
		return implementedClasses;
	}

	public List<CheetahField> getFields() {
		return fields;
	}

	public List<CheetahMethod> getMethods() {
		return methods;
	}

	@Override
	public String writeObject() {
		StringBuilder sb = new StringBuilder();
		sb.append(getPackage());
		for (CheetahImport cheetahImport : imports) {
			sb.append(cheetahImport.writeObject());
		}
		for (CheetahAnnotation cheetahAnnotation : annotations) {
			sb.append(cheetahAnnotation.writeObject());
		}
		sb.append("public ");

		switch (getType()) {
		case INTERFACE:
			sb.append(" interface ");
			break;

		default:
			sb.append(" class ");
			break;
		}
		sb.append(name);

		if (getExtendedClass() != null) {
			sb.append(" extends ").append(getExtendedClass().getName());
		}
		if (getImplementedClasses().size() > 0) {
			sb.append(" implements ");
			for (CheetahClass cheetahClass : implementedClasses) {
				sb.append(cheetahClass.getName()).append(",");
			}
			sb = new StringBuilder(sb.toString().substring(0, sb.lastIndexOf(",")));
		}

		sb.append("{\n");

		for (CheetahField cheetahField : fields) {
			sb.append(cheetahField.writeObject());
		}
		for (CheetahMethod cheetahMethod : methods) {
			sb.append(cheetahMethod.writeObject());
		}

		sb.append("}");

		return sb.toString();
	}

	public List<CheetahImport> getImports() {
		return imports;
	}

	public CheetahPackage getPackage() {
		return cheetahPackage;
	}

	public CheetahClassType getType() {
		return type;
	}

	public List<CheetahAnnotation> getAnnotations() {
		return annotations;
	}

	public CheetahClass getExtendedClass() {
		return extendedClass;
	}

	public void setExtendedClass(CheetahClass extendedClass) {
		this.extendedClass = extendedClass;
	}

	public void addAnnotation(CheetahAnnotation a) {
		this.annotations.add(a);
	}
	
	public void addField(CheetahField a) {
		this.fields.add(a);
	}
	
	public void addMethod(CheetahMethod a) {
		this.methods.add(a);
	}
	
	public void addImport(CheetahImport a) {
		this.imports.add(a);
	}
	
	public void addImplementedClass(CheetahClass a) {
		this.implementedClasses.add(a);
	}
	
	
	

}
