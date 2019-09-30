package org.cheetah.processor;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

import org.cheetah.processor.support.CField;
import org.cheetah.processor.support.CMapping;
import org.cheetah.processor.support.CMethod;
import org.cheetah.spring.annotations.CheetahSpring;
import org.cheetah.spring.annotations.Type;

import com.google.auto.service.AutoService;

@AutoService(Processor.class)
public class CheetahProcessor extends AbstractProcessor {

	private Types typeUtils;
	private Elements elementUtils;
	private Filer filer;
	private Messager messager;

	private boolean foundEntities = false;

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		Set<String> annotations = new LinkedHashSet<String>();
		annotations.add(CheetahSpring.class.getCanonicalName());
		return annotations;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//		try {

//			if (!foundEntities) {
//				for (Element annotadedElement : roundEnv.getElementsAnnotatedWith(
//						(Class<? extends Annotation>) Class.forName("javax.persistence.Entity"))) {
//					foundEntities = true;
//					
//				}
//			}
//		} catch (ClassNotFoundException e1) {
//			e1.printStackTrace();
//		}
//		if (foundEntities) {
		for (Element annotadedElement : roundEnv.getElementsAnnotatedWith(CheetahSpring.class)) {
			List<CField> fields = new ArrayList<>();// memorizza i campi da creare
			List<CMethod> methods = new ArrayList<>();// memorizza i campi da creare

			List<String> imports = new ArrayList<>();
			TypeElement te = (TypeElement) annotadedElement;
			CheetahSpring ann = te.getAnnotation(CheetahSpring.class);
			Type type = ann.type();// maybe unuseful
			String packageRoot = ann.pkgroot();
			// get the entity type.
			TypeElement tEntity = this.elementUtils.getTypeElement(ann.entity());
			List<? extends Element> enclosedElements = tEntity.getEnclosedElements();
			// loop inside the enclosed elements (fields)
			String idType = "";
			enclosed: for (Element typeElement : enclosedElements) {
				if (!typeElement.getModifiers().contains(javax.lang.model.element.Modifier.STATIC)
						&& !typeElement.getModifiers().contains(javax.lang.model.element.Modifier.FINAL)) {
					if (typeElement.getKind() == ElementKind.FIELD) {
						String name = typeElement.getSimpleName().toString(); // the orginal attribute's name
						String _class = typeElement.asType().toString(); // the original attibute type
						CMapping mapping = null;
						// I'm getting the annotations' field
						List<? extends AnnotationMirror> entityAnnotations = typeElement.getAnnotationMirrors();
						for (AnnotationMirror annotationMirror : entityAnnotations) {
							if (isIdEntity(annotationMirror)) {
								// the attribute is an Id
								idType = typeElement.asType().toString();
							}
						}

						// verifico se il campo è annotato con ManyToOne. In questo caso lo salto e vado
						// a prendere il tipo primitivo
						for (AnnotationMirror am : typeElement.getAnnotationMirrors()) {
							if (am.getAnnotationType().toString().equals("javax.persistence.OneToMany")
									|| am.getAnnotationType().toString().equals("javax.persistence.ManyToMany")) {
								// I don't care
								continue enclosed;
							}
							if (am.getAnnotationType().toString().equals("javax.persistence.ManyToOne")) {
								TypeElement temp = this.elementUtils.getTypeElement(typeElement.asType().toString());
								// ciclo tra le annotazioni di questo elemento e prendo il campo annotato con id
								// e lo metto su questo dto.
								for (Element typeElementInner : temp.getEnclosedElements()) {
									for (AnnotationMirror amInner : typeElementInner.getAnnotationMirrors()) {
										if (isIdEntity(amInner)) {

											mapping = new CMapping(name + "." + name, name);
											_class = typeElementInner.asType().toString();

										}
									}
								}
							}
						}

						CField f = new CField(name, _class);
						CMethod m = new CMethod(name, _class);
						if (mapping != null) {
							f.setMapping(mapping);
						}
						fields.add(f);
						methods.add(m);

						if (!typeElement.asType().getKind().isPrimitive()) {
							if (!imports.contains(_class) && _class.indexOf("java.lang") == -1
									&& (_class.indexOf("byte") == -1 && _class.indexOf("double") == -1
											&& _class.indexOf("char") == -1 && _class.indexOf("short") == -1
											&& _class.indexOf("long") == -1 && _class.indexOf("int") == -1)) {
								imports.add(_class);
							}
						}
					}
				}
			}
			try {
				String name = ann.entity().substring(ann.entity().lastIndexOf(".") + 1);
				String packageOfAnnotadedElement = packageRoot + ".dto";
				String packageOfRepository = packageRoot + ".repository";
				String packageMapper = packageRoot + ".mapper";
				String packageService = packageRoot + ".service";
				createDto(fields, methods, imports, name, packageOfAnnotadedElement);
				if (!idType.equals("")) {
					createRepository(name, packageOfRepository, ann.entity(), idType);
				}
//				createMapper(packageMapper, fields, name + "Mapper", packageOfAnnotadedElement + "." + name + "Dto",
//						ann.entity());

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
//		}
		System.out.println("============================================");
		System.out.println("============================================");
		System.out.println("============================================");
		return true;

	}

//	private void createMapper(String pkgMapper, List<CField> fields, String name, String dtoWithPackage, String entity)
//			throws IOException {
//		String dtoName = dtoWithPackage.substring(dtoWithPackage.lastIndexOf(".")+1);
//		String entityName = entity.substring(entity.lastIndexOf(".")+1);
//		JavaFileObject fo = this.filer.createSourceFile(pkgMapper + "." + name);
//		Writer w = fo.openWriter();
//		w.append("package ").append(pkgMapper).append(";\n\r");
//		w.append("import java.util.List;\n" + "\n" + "import org.mapstruct.InheritConfiguration;\n"
//				+ "import org.mapstruct.Mapper;\n" + "import org.mapstruct.Mappings;\n"
//				+ "import org.mapstruct.factory.Mappers;\n" + "\n" + "import " + dtoWithPackage + ";\n" + "import "
//				+ entity + ";\n\n");
//
//		w.append("@Mapper\n" + "public abstract class " + name + "  {\n" + "\n"
//				+ "	public static final "+name+" INSTANCE = Mappers.getMapper("+name+".class);\n" + "\n");
//	
//		StringBuilder sb = new StringBuilder();
//		//concateno l'annotation mapping se presente
//		for (CField cField : fields) {
//			if(cField.getMapping()!=null) {
//				sb.append("@Mapping ("+cField.getMapping().getCode()+"),\n");
//			}
//		}
//		String mapping =sb.substring(0,sb.lastIndexOf(","));
//		if(mapping!=null && !mapping.equals("")) {
//			w.append("@Mappings(").append(mapping).append(")\n");
//		}
//		w.append("	public abstract "+dtoName+" toDto("+entityName+" e);\n\r");
//		if(mapping!=null && !mapping.equals("")) {
//			w.append("@Mappings(").append(mapping).append(")\n");
//		}
//		w.append("	public abstract "+dtoName+" toDto("+entityName+" e);\n\r");
//		
//		sb = new StringBuilder();
//		for (CField cField : fields) {
//			if(cField.getMapping()!=null) {
//				sb.append("@Mapping ("+cField.getMapping().getReverseCode()+"),\n");
//			}
//		}
//		mapping =sb.substring(0,sb.lastIndexOf(","));
//		if(mapping!=null && !mapping.equals("")) {
//			w.append("@Mappings(").append(mapping).append(")\n");
//		}
//		
//		w.append("public abstract "+entityName+" toEntity("+dtoName+" d);\n" + 
//				"\n" + 
//				"\n" + 
//				"	@InheritConfiguration\n" + 
//				"	public abstract List<"+entityName+"> toEntities(List<"+dtoName+"> dtos);\n" + 
//				"\n" + 
//				"\n" + 
//				"	@InheritConfiguration\n" + 
//				"	public abstract List<"+dtoName+"> toDtos(List<"+entityName+"> entities);\n"
//						+ "}" );
//		
//		w.close();
//		
//	}

	private boolean isIdEntity(AnnotationMirror amInner) {
		return amInner.getAnnotationType().toString().equals("javax.persistence.Id");
	}

	private void createRepository(String name, String packageOfRepository, String entity, String idType)
			throws IOException {
		name += "Repository";
		JavaFileObject fo = this.filer.createSourceFile(packageOfRepository + "." + name);
		Writer w = fo.openWriter();
		w.append("package ").append(packageOfRepository).append(";\n\r");
		w.append("import org.springframework.data.repository.PagingAndSortingRepository;\n"
				+ "import org.springframework.stereotype.Repository;\n");

		w.append("import ").append(entity).append(";\n\r");

		w.append("@Repository\n" + "public interface " + name + " extends PagingAndSortingRepository<" + entity + ", "
				+ idType + "> {\n" + "\n" + "}\n" + "\n" + "");
		w.close();

	}

	private void createDto(List<CField> fields, List<CMethod> methods, List<String> imports, String name,
			String thePackage) throws IOException {
		System.out.println("-----------> " + name);
		name += "Dto";
		JavaFileObject fo = this.filer.createSourceFile(thePackage + "." + name);
		Writer writer = fo.openWriter();
		writer.append("package " + thePackage + ";\n");
		for (String s : imports) {
			writer.append("import " + s + ";\n");
		}
		writer.append("import java.io.Serializable;\n");
		writer.append("public class " + name + " implements Serializable { \n");
		for (CField cField : fields) {
			writer.append(cField.getCode() + "\n");
		}
		for (CMethod cMethod : methods) {
			writer.append(cMethod.getCode() + "\n");
		}
		writer.append("}");
		writer.close();
	}

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		this.typeUtils = processingEnv.getTypeUtils();
		this.elementUtils = processingEnv.getElementUtils();
		this.filer = processingEnv.getFiler();
		this.messager = processingEnv.getMessager();
	}

}
