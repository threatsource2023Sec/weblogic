package javax.enterprise.inject.literal;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

public final class InjectLiteral extends AnnotationLiteral implements Inject {
   public static final InjectLiteral INSTANCE = new InjectLiteral();
   private static final long serialVersionUID = 1L;
}
