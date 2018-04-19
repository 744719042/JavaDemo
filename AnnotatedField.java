import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.util.Elements;

public class AnnotatedField {
	private String name;
	private Elements mElementUtils;
	private VariableElement vElement;
	
	public AnnotatedField(VariableElement vElement, Elements elementUtils) {
		super();
		this.vElement = vElement;
		this.mElementUtils = elementUtils;
		this.name = vElement.getSimpleName().toString();
	}
	
	public String getUnbindFieldJava() {
		return "." + name + " = null;\n";
	}

	public String getBindFieldJava() {
		BindListener listener = vElement.getAnnotation(BindListener.class);
		String clazzName;
		try {
			clazzName = listener.value().getName();
		} catch (MirroredTypeException mte) {
			DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
	        TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
	        clazzName = classTypeElement.getQualifiedName().toString();
		}
		return "." + name + ".addActionListener(new " + clazzName + "()); \n";
	}
	
}
