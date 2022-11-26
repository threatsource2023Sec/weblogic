package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface MethodPermissionPattern {
   String pattern();

   String roles() default "";

   Constants.Interface itf() default Constants.Interface.UNSPECIFIED;

   String id() default "UNSPECIFIED";

   Constants.Bool unchecked() default Constants.Bool.UNSPECIFIED;
}
