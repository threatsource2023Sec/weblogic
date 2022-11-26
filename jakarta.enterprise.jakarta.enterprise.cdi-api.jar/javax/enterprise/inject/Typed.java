package javax.enterprise.inject;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.util.AnnotationLiteral;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Typed {
   Class[] value() default {};

   public static final class Literal extends AnnotationLiteral implements Typed {
      public static final Literal INSTANCE = of(new Class[0]);
      private static final long serialVersionUID = 1L;
      private final Class[] value;

      public static Literal of(Class[] value) {
         return new Literal(value);
      }

      private Literal(Class[] value) {
         this.value = value;
      }

      public Class[] value() {
         return this.value;
      }
   }
}
