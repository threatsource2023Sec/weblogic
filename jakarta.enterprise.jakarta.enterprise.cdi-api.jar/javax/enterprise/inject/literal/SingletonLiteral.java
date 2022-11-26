package javax.enterprise.inject.literal;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Singleton;

public final class SingletonLiteral extends AnnotationLiteral implements Singleton {
   public static final SingletonLiteral INSTANCE = new SingletonLiteral();
   private static final long serialVersionUID = 1L;
}
