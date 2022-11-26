package javax.enterprise.context;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;

@Qualifier
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Initialized {
   Class value();

   public static final class Literal extends AnnotationLiteral implements Initialized {
      public static final Literal REQUEST = of(RequestScoped.class);
      public static final Literal CONVERSATION = of(ConversationScoped.class);
      public static final Literal SESSION = of(SessionScoped.class);
      public static final Literal APPLICATION = of(ApplicationScoped.class);
      private static final long serialVersionUID = 1L;
      private final Class value;

      public static Literal of(Class value) {
         return new Literal(value);
      }

      private Literal(Class value) {
         this.value = value;
      }

      public Class value() {
         return this.value;
      }
   }
}
