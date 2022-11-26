package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface RoleMapping {
   String roleName();

   String globalRole() default "UNSPECIFIED";

   Constants.Bool externallyDefined() default Constants.Bool.UNSPECIFIED;

   String principals() default "UNSPECIFIED";

   String id() default "UNSPECIFIED";
}
