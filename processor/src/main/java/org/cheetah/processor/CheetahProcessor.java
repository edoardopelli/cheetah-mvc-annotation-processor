package org.cheetah.processor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import org.apache.commons.lang3.StringUtils;
import org.cheetah.processor.support.CField;
import org.cheetah.processor.support.CMethod;
import org.cheetah.spring.annotations.CheetahSpring;
import org.cheetah.spring.annotations.Type;

import com.google.auto.service.AutoService;

@AutoService(Processor.class)
@SupportedAnnotationTypes(value = { "org.cheetah.spring.annotations.CheetahSpring" })
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class CheetahProcessor extends AbstractProcessor {

	private Types typeUtils;
	private Elements elementUtils;
	private Filer filer;
	private Messager messager;

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		System.out.println("============================================");
		System.out.println("============================================");
		System.out.println("============================================");
		for (Element annotadedElement : roundEnv.getElementsAnnotatedWith(CheetahSpring.class)) {
			List<CField> fields = new ArrayList<>();// memorizza i campi da creare
			List<CMethod> methods = new ArrayList<>();// memorizza i campi da creare
			List<String> imports = new ArrayList<>();
			TypeElement te = (TypeElement) annotadedElement;
			CheetahSpring ann = te.getAnnotation(CheetahSpring.class);
			Type type = ann.type();
			TypeElement tEntity = this.elementUtils.getTypeElement(ann.entity());
			List<? extends Element> enclosedElements = tEntity.getEnclosedElements();
			//loop inside  the enclosed elements (fields)
			enclosed: for (Element typeElement : enclosedElements) {
				if (!typeElement.getModifiers().contains(javax.lang.model.element.Modifier.STATIC)
						&& !typeElement.getModifiers().contains(javax.lang.model.element.Modifier.FINAL)) {
					if (typeElement.getKind() == ElementKind.FIELD) {
						String name = typeElement.getSimpleName().toString();
						String _class = typeElement.asType().toString();
						// verifico se il campo è annotato con ManyToOne. In questo caso lo salto e vado
						// a prendere il tipo primitivo
						for (AnnotationMirror am : typeElement.getAnnotationMirrors()) {
							if (am.getAnnotationType().toString().equals("javax.persistence.OneToMany") ||
									am.getAnnotationType().toString().equals("javax.persistence.ManyToMany")) {
								//I don't care
								continue enclosed;
							}
							if (am.getAnnotationType().toString().equals("javax.persistence.ManyToOne")) {
								TypeElement temp = this.elementUtils.getTypeElement(typeElement.asType().toString());
								// ciclo tra le annotazioni di questo elemento e prendo il campo annotato con id
								// e lo metto su questo dto.
								for (Element typeElementInner : temp.getEnclosedElements()) {
									for (AnnotationMirror amInner : typeElementInner.getAnnotationMirrors()) {
										if (amInner.getAnnotationType().toString().equals("javax.persistence.Id")) {
											_class = typeElementInner.asType().toString();
										}
									}
								}
							}
						}
						CField f = new CField(name, _class);
						CMethod m = new CMethod(name, _class);
						fields.add(f);
						methods.add(m);
						System.out.println(_class + " ---> "+typeElement.asType().getKind().isPrimitive());
						if(!typeElement.asType().getKind().isPrimitive()) {
							if(!imports.contains(_class) && _class.indexOf("java.lang")==-1 && (
									_class.indexOf("byte")==-1 && _class.indexOf("double")==-1 && _class.indexOf("char")==-1 && 
									_class.indexOf("short")==-1 && _class.indexOf("long")==-1 && _class.indexOf("int")==-1  
									)) {
								System.out.println(_class.indexOf("byte"));
								imports.add(_class);
							}
						}
//						System.out.println(f.getCode());
//						System.out.println(m.getCode());
//						System.out.println(typeElement + " --> " + typeElement.asType() + " / " + typeElement.getKind()
//								+ " / " + typeElement.getModifiers());
					}
				}
			}
			try {
				String path = StringUtils.replace(this.elementUtils.getPackageOf(annotadedElement).toString(), ".", "/");
				String name = ann.entity().substring(ann.entity().lastIndexOf(".")+1)+"Dto";
				System.out.println(name);
				FileObject fo = this.filer.createResource(StandardLocation.SOURCE_OUTPUT,"", path+"/"+name+".java");
				Writer writer = fo.openWriter();
				writer.append("package "+this.elementUtils.getPackageOf(annotadedElement)+";\n");
				for (String s : imports) {
					writer.append("import "+s+";\n");
				}
				writer.append("import java.io.Serializable;\n");
				writer.append("public class "+name+" impl ements Serializable { \n");
				for (CField cField : fields) {
					writer.append(cField.getCode()+"\n");
				}
				for (CMethod cMethod : methods) {
					writer.append(cMethod.getCode()+"\n");
				}
				writer.append("}");
				writer.close();
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println("============================================");
		System.out.println("============================================");
		System.out.println("============================================");
		return true;

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
