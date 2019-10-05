package org.cheetah.processor.javaobject;

import java.util.ArrayList;
import java.util.List;

import org.cheetah.processor.javaobject.enums.CheetahClassType;

public class CheetahClass extends CheetahAbstractJavaObject {

	private String name;

	private List<CheetahAnnotation> annotations = new ArrayList<>();

	private List<CheetahClass> implementedClasses = new ArrayList<>();;

	private List<CheetahField> fields = new ArrayList<>();;

	private List<CheetahMethod> methods = new ArrayList<>();

	private List<CheetahImport> imports = new ArrayList<>();

	private List<String> generics = new ArrayList<>();

	private CheetahClass extendedClass;

	private CheetahPackage cheetahPackage;

	private CheetahClassType type;

	public CheetahClass(String name, CheetahClass extendedClass, CheetahClassType type) {
		super();
		this.name = name;
		this.extendedClass = extendedClass;
		this.type = type;
	}

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
	public String writeClass() {
		StringBuilder sb = new StringBuilder();
		sb.append(getPackage().writeClass());
		for (CheetahImport cheetahImport : imports) {
			sb.append(cheetahImport.writeClass());
		}
		for (CheetahAnnotation cheetahAnnotation : annotations) {
			sb.append(cheetahAnnotation.writeClass());
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
			switch (type) {
			case CLASS:
				sb.append(" implements ");
				break;

			default:
				sb.append(" extends ");
				break;
			}
			for (CheetahClass cheetahClass : implementedClasses) {
				sb.append(cheetahClass.writeShortClass()).append(",");
			}
			sb = new StringBuilder(sb.toString().substring(0, sb.lastIndexOf(",")));
		}

		sb.append("{\n\n");

		for (CheetahField cheetahField : fields) {
			System.out.println("write field: " + cheetahField.getName());
			sb.append(cheetahField.writeClass());
		}
		for (CheetahMethod cheetahMethod : methods) {
			sb.append(cheetahMethod.writeClass());
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
		if (!this.imports.contains(a)) {
			this.imports.add(a);
		}
	}

	public void addImplementedClass(CheetahClass a) {
		this.implementedClasses.add(a);
	}

	public void addGeneric(String generic) {
		this.generics.add(generic);
	}

	@Override
	public String writeShortClass() {
		boolean hasGenerics = generics.size() != 0;
		StringBuilder sb = new StringBuilder();
		sb.append(name).append(hasGenerics ? "<" : "");

		for (String string : generics) {
			sb.append(string).append(",");
		}

		if (sb.toString().endsWith(",")) {
			sb = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")));
		}
		sb.append(hasGenerics ? ">" : "");
		return sb.toString();
	}

	public void setPackage(CheetahPackage cheetahPackage) {
		this.cheetahPackage = cheetahPackage;
	}

	public String getQualifierName() {
		return getPackage().writeShortClass() + "." + getName();
	}
}
