package javax.enterprise.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Nonbinding {
   public static final class Literal extends AnnotationLiteral implements Nonbinding {
      public static final Literal INSTANCE = new Literal();
      private static final long serialVersionUID = 1L;
   }
}
