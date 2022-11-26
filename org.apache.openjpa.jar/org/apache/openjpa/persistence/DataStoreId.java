package org.apache.openjpa.persistence;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.persistence.GenerationType;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataStoreId {
   GenerationType strategy() default GenerationType.AUTO;

   String generator() default "";
}
