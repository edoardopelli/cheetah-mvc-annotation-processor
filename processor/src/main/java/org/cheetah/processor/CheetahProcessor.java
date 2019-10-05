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

import org.apache.commons.lang3.StringUtils;
import org.cheetah.processor.javaobject.CheetahAnnotation;
import org.cheetah.processor.javaobject.CheetahClass;
import org.cheetah.processor.javaobject.CheetahField;
import org.cheetah.processor.javaobject.CheetahImport;
import org.cheetah.processor.javaobject.CheetahLine;
import org.cheetah.processor.javaobject.CheetahMethod;
import org.cheetah.processor.javaobject.CheetahPackage;
import org.cheetah.processor.javaobject.CheetahParameter;
import org.cheetah.processor.javaobject.enums.CheetahClassType;
import org.cheetah.processor.javaobject.enums.CheetahModifier;
import org.cheetah.processor.support.CField;
import org.cheetah.processor.support.CMapping;
import org.cheetah.processor.support.CMethod;
import org.cheetah.processor.utils.Utils;
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

			String entityName = ann.entity().substring(ann.entity().lastIndexOf(".") + 1);

			Type type = ann.type();// maybe unuseful
			String packageRoot = ann.pkgroot();

			// preparing the classes I have to create;
			CheetahClass controller = new CheetahClass(entityName + "Controller", CheetahClassType.CLASS);

			CheetahClass service = new CheetahClass(entityName + "Service", CheetahClassType.CLASS);
			service.setPackage(new CheetahPackage(packageRoot + ".service"));

			CheetahClass dto = new CheetahClass(entityName + "Dto", CheetahClassType.CLASS);
			dto.setPackage(new CheetahPackage(packageRoot + ".dto"));

			CheetahClass repository = new CheetahClass(entityName + "Repository", CheetahClassType.INTERFACE);

			CheetahClass pagingRepo = new CheetahClass("PagingAndSortingRepository", CheetahClassType.INTERFACE);
			pagingRepo.addGeneric(entityName);

			repository.addImport(new CheetahImport(ann.entity()));
			repository.addImport(new CheetahImport("org.springframework.data.repository.PagingAndSortingRepository"));
			repository.addImport(new CheetahImport("org.springframework.stereotype.Repository"));
			repository.addAnnotation(new CheetahAnnotation("Repository"));

			repository.addImplementedClass(pagingRepo);
			repository.setPackage(new CheetahPackage(packageRoot + ".repository"));

			// add repository injection to service class
			CheetahField repositoryField = new CheetahField("repository", repository.getName(),
					CheetahModifier.PRIVATE);
			repositoryField.addAnnotation(new CheetahAnnotation("Autowired"));
			service.addField(repositoryField);
			service.addImport(new CheetahImport("org.springframework.beans.factory.annotation.Autowired"));
			service.addImport(new CheetahImport(repository.getQualifierName()));

			CheetahMethod serviceSaveMethod = new CheetahMethod(CheetahModifier.PUBLIC, dto, "save");
			service.addImport(new CheetahImport(dto.getQualifierName()));
			service.addImport(new CheetahImport(ann.entity()));
			service.addImport(new CheetahImport("org.springframework.stereotype.Service"));
			service.addAnnotation(new CheetahAnnotation("Service"));
			serviceSaveMethod.addParam(new CheetahParameter(dto, StringUtils.uncapitalize(dto.getName())));
			serviceSaveMethod.addLine(new CheetahLine(entityName + " ntt = new " + entityName + "()"));
			service.addMethod(serviceSaveMethod);
			CheetahLine lastLineSaveMethod = null;

			// get the entity type.
			TypeElement tEntity = this.elementUtils.getTypeElement(ann.entity());
			List<? extends Element> enclosedElements = tEntity.getEnclosedElements();
			// loop inside the enclosed elements (fields)
			String idType = "";
			enclosed: for (Element typeElement : enclosedElements) {
				if (!typeElement.getModifiers().contains(javax.lang.model.element.Modifier.STATIC)
						&& !typeElement.getModifiers().contains(javax.lang.model.element.Modifier.FINAL)) {
					if (typeElement.getKind() == ElementKind.FIELD) {
						boolean skip = false;
						String name = typeElement.getSimpleName().toString(); // the orginal attribute's name
						String _class = typeElement.asType().toString(); // the original attibute type
						// I'm getting the annotations' field
						List<? extends AnnotationMirror> entityAnnotations = typeElement.getAnnotationMirrors();
						for (AnnotationMirror annotationMirror : entityAnnotations) {
							if (isIdEntity(annotationMirror)) {
								// the attribute is an Id
								idType = typeElement.asType().toString();
								// add generic to the PagingAndSortRepository interface
								pagingRepo.addGeneric(idType);

								// at this point I create a cheethaline object that must be attached at the end
								// of save service method, for getting the id value
								lastLineSaveMethod = new CheetahLine(StringUtils.uncapitalize(dto.getName()) + ".set"
										+ StringUtils.capitalize(
												typeElement.getSimpleName().toString() + "(ntt.get" + StringUtils
														.capitalize(typeElement.getSimpleName().toString() + "())")));
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
								skip = true;
								TypeElement temp = this.elementUtils.getTypeElement(typeElement.asType().toString());
								// add code for dto to entity copy.
								service.addImport(new CheetahImport(_class));
								String simpleName = Utils.getClassName(_class);
								// this is the name of object
								String instanceName = StringUtils.uncapitalize(simpleName);
								serviceSaveMethod.addLine(new CheetahLine(
										simpleName + " " + instanceName + " = new " + simpleName + "()"));

								// ciclo tra le annotazioni di questo elemento e prendo il campo annotato con id
								// e lo metto su questo dto.
								for (Element typeElementInner : temp.getEnclosedElements()) {
									for (AnnotationMirror amInner : typeElementInner.getAnnotationMirrors()) {
										if (isIdEntity(amInner)) {

											_class = typeElementInner.asType().toString();
											// create the second part of the method name with capitalize
											String methodName = StringUtils
													.capitalize(typeElementInner.getSimpleName().toString());
											// Now set di value of id field in the inner class of entity
											serviceSaveMethod.addLine(new CheetahLine(instanceName + ".set" + methodName
													+ "(" + StringUtils.uncapitalize(dto.getName()) + ".get"
													+ methodName + "())"));

										}
									}
									// write value into entity
								}
							}

						}
						// here I'm writing the code for setting attributes of entity
						if (!skip) {
							String methodName = StringUtils.capitalize(name);

							serviceSaveMethod.addLine(new CheetahLine("ntt.set" + methodName + "("
									+ StringUtils.uncapitalize(dto.getName()) + ".get" + methodName + "())"));

						}

						CheetahField field = new CheetahField(name, _class, CheetahModifier.PRIVATE);

						/**
						 * Create the set method
						 */
						CheetahMethod setmethod = new CheetahMethod(CheetahModifier.PUBLIC,
								"set" + StringUtils.capitalize(name));
						CheetahParameter param1 = new CheetahParameter(new CheetahClass(_class, CheetahClassType.CLASS),
								name);
						setmethod.addParam(param1);
						CheetahLine line = new CheetahLine("this." + name + "=" + name);
						setmethod.addLine(line);
						/**
						 * Create the get method
						 */
						CheetahMethod getmethod = new CheetahMethod(CheetahModifier.PUBLIC,
								new CheetahClass(_class, CheetahClassType.CLASS), "get" + StringUtils.capitalize(name));
						getmethod.addLine(new CheetahLine("return this." + name));

						dto.addField(field);
						dto.addMethod(setmethod);
						dto.addMethod(getmethod);

						if (!typeElement.asType().getKind().isPrimitive()) {
							if (!imports.contains(_class) && _class.indexOf("java.lang") == -1
									&& (_class.indexOf("byte") == -1 && _class.indexOf("double") == -1
											&& _class.indexOf("char") == -1 && _class.indexOf("short") == -1
											&& _class.indexOf("long") == -1 && _class.indexOf("int") == -1)) {
								CheetahImport imp = new CheetahImport(_class);
								dto.addImport(imp);
							}
						}
					}
				}
			}

			try {

				serviceSaveMethod.addLine(new CheetahLine("ntt = " + repositoryField.getName() + ".save(ntt)"));
				serviceSaveMethod.addLine(lastLineSaveMethod);
				serviceSaveMethod.addLine(new CheetahLine("return " + StringUtils.uncapitalize(dto.getName())));

				createClass(dto);
				createClass(repository);
				createClass(service);
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

	private void createClass(CheetahClass _class) throws IOException {
		JavaFileObject fo = this.filer.createSourceFile(_class.getQualifierName());
		Writer writer = fo.openWriter();

		writer.append(_class.writeClass());
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
