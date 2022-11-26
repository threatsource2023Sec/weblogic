package javax.enterprise.inject;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.util.AnnotationLiteral;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Specializes {
   public static final class Literal extends AnnotationLiteral implements Specializes {
      private static final long serialVersionUID = 1L;
      public static final Literal INSTANCE = new Literal();
   }
}
