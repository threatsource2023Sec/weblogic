package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface ValueObject {
   Reference reference() default ValueObject.Reference.UNSPECIFIED;

   public static enum Reference {
      UNSPECIFIED,
      LOCAL,
      VALUE;
   }
}
