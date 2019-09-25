package org.cheetah.processor;

import java.lang.module.ModuleDescriptor.Exports.Modifier;
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
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import org.cheetah.processor.support.CField;
import org.cheetah.processor.support.CMethod;
import org.cheetah.spring.annotations.CheetahSpring;
import org.cheetah.spring.annotations.Type;

import com.google.auto.service.AutoService;

@AutoService(Processor.class)
@SupportedAnnotationTypes(value = { "org.cheetah.spring.annotations.CheetahSpring" })
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
			TypeElement te = (TypeElement) annotadedElement;
			CheetahSpring ann = te.getAnnotation(CheetahSpring.class);
			Type type = ann.type();
			TypeElement tEntity = this.elementUtils.getTypeElement(ann.entity());
			List<? extends Element> enclosedElements = tEntity.getEnclosedElements();
			for (Element typeElement : enclosedElements) {
				if (!typeElement.getModifiers().contains(javax.lang.model.element.Modifier.STATIC)
						&& !typeElement.getModifiers().contains(javax.lang.model.element.Modifier.FINAL)) {
					if (typeElement.getKind() == ElementKind.FIELD) {
						String name = typeElement.getSimpleName().toString();
						String _class = typeElement.asType().toString();
						// verifico se il campo è annotato con ManyToOne. In questo caso lo salto e vado
						// a prendere il tipo primitivo
						for (AnnotationMirror am : typeElement.getAnnotationMirrors()) {
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

						System.out.println(f.getCode());
						System.out.println(m.getCode());
//						System.out.println(typeElement + " --> " + typeElement.asType() + " / " + typeElement.getKind()
//								+ " / " + typeElement.getModifiers());
					}
				}
			}

		}
		System.out.println("============================================");
		System.out.println("============================================");
		System.out.println("============================================");
		return false;

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
