import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.JavaFileObject;

public class AnnotatedClass {
	private List<AnnotatedField> mAnnotateFields = new ArrayList<>();
	private TypeElement mType;
	private Elements mElementUtils;
	private Filer mFiler;
	
	public AnnotatedClass(TypeElement type, Elements elementUtils, Filer filer) {
		this.mType = type;
		this.mElementUtils = elementUtils;
		this.mFiler = filer;
	}

	public void addAnnotatedField(AnnotatedField annotatedField) {
		mAnnotateFields.add(annotatedField);
	}
	
	public void writeToFiler() {
		try {
			JavaFileObject file = mFiler.createSourceFile(mType.getSimpleName().toString() + BindRuntime.CLASS_PREFIX, (Element[]) null);
			Writer writer = file.openWriter();
			writer.append("class ");
			writer.append(mType.getSimpleName().toString() + BindRuntime.CLASS_PREFIX);
			writer.append(" implements IBind<" + mType.getSimpleName().toString() + "> {\n");
			writer.append(" public void bind(" + mType.getSimpleName() + " object) { \n");
			for (AnnotatedField field : mAnnotateFields) {
				writer.append("object" + field.getBindFieldJava());
			}
			writer.append("}/n");
			
			writer.append(" public void unbind(" + mType.getSimpleName() + " object) { \n");
			for (AnnotatedField field : mAnnotateFields) {
				writer.append("object" + field.getUnbindFieldJava());
			}
			writer.append("}");
			
			writer.append("}");
			writer.flush();
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
