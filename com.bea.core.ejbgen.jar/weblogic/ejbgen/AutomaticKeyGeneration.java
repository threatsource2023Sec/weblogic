package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface AutomaticKeyGeneration {
   AutomaticKeyGenerationType type();

   String cacheSize() default "UNSPECIFIED";

   String name() default "UNSPECIFIED";

   Constants.Bool selectFirstSequenceKeyBeforeUpdate() default Constants.Bool.UNSPECIFIED;

   public static enum AutomaticKeyGenerationType {
      UNSPECIFIED,
      IDENTITY,
      SEQUENCE,
      SEQUENCE_TABLE;
   }
}
