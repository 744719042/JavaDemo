import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

public class BindProcessor extends AbstractProcessor {
	private Set<String> mAnnotationSet = new HashSet<>();
	private Messager mMessager;
	private Filer mFiler;
	private Elements mElementUtils;
	private Map<String, AnnotatedClass> mContainer = new HashMap<>();
	
	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return mAnnotationSet;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

	@Override
	public synchronized void init(ProcessingEnvironment env) {
		super.init(env);
		mMessager = env.getMessager();
		mFiler = env.getFiler();
		mElementUtils = env.getElementUtils();
		
		mAnnotationSet.add(BindListener.class.getName());
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(BindListener.class);
		if (elements == null || elements.isEmpty()) {
			return false;
		}
		
		for (Element e : elements) {
			VariableElement v = (VariableElement) e;
			TypeElement type = (TypeElement) e.getEnclosingElement();
			String className = type.getSimpleName().toString();
			AnnotatedClass annotatedClass = mContainer.get(className);
			if (annotatedClass == null) {
				annotatedClass = new AnnotatedClass(type, mElementUtils, mFiler);
				mContainer.put(className, annotatedClass);
			}
			annotatedClass.addAnnotatedField(new AnnotatedField(v, mElementUtils));
		}
		
		for (Map.Entry<String, AnnotatedClass> entry : mContainer.entrySet()) {
			entry.getValue().writeToFiler();
		}
		
		return true;
	}

}
