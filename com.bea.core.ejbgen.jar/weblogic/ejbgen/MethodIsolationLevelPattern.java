package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface MethodIsolationLevelPattern {
   Constants.IsolationLevel isolationLevel();

   String pattern();

   String id() default "UNSPECIFIED";
}
