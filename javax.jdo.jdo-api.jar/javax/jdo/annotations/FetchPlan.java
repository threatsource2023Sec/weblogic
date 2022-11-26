package javax.jdo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FetchPlan {
   String name() default "";

   String[] fetchGroups() default {};

   int maxFetchDepth() default 1;

   int fetchSize() default 0;
}
