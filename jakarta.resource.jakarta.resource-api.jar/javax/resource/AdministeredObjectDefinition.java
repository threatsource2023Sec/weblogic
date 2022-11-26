package javax.resource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface AdministeredObjectDefinition {
   String name();

   String description() default "";

   String resourceAdapter();

   String className();

   String interfaceName() default "";

   String[] properties() default {};
}
