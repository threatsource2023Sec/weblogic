package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface EntityCacheRef {
   Constants.ConcurrencyStrategy concurrencyStrategy();

   String name();

   int idleTimeoutSeconds() default 0;

   int readTimeoutSeconds() default 0;

   Constants.Bool cacheBetweenTransactions() default Constants.Bool.UNSPECIFIED;

   String estimatedBeanSize() default "UNSPECIFIED";
}
