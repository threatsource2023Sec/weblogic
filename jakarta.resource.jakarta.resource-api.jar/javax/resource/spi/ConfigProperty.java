package javax.resource.spi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ConfigProperty {
   Class type() default Object.class;

   String[] description() default {};

   String defaultValue() default "";

   boolean ignore() default false;

   boolean supportsDynamicUpdates() default false;

   boolean confidential() default false;
}
