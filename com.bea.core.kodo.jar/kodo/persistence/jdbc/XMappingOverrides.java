package kodo.persistence.jdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** @deprecated */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface XMappingOverrides {
   XMappingOverride[] value() default {};
}
