package javax.enterprise.inject.literal;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;

public final class QualifierLiteral extends AnnotationLiteral implements Qualifier {
   public static final QualifierLiteral INSTANCE = new QualifierLiteral();
   private static final long serialVersionUID = 1L;
}
