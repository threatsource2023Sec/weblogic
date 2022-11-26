package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface SqlShape {
   String name();

   String[] relationNames() default {};

   SqlShapeTable[] tables() default {};

   int passThroughColumns() default 0;
}
