package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface EjbRef {
   String jndiName() default "UNSPECIFIED";

   Constants.RefType type() default Constants.RefType.UNSPECIFIED;

   String link() default "UNSPECIFIED";

   String name() default "UNSPECIFIED";

   String remote() default "UNSPECIFIED";

   String id() default "UNSPECIFIED";

   String home() default "UNSPECIFIED";
}
