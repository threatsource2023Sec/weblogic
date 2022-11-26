package org.python.compiler.custom_proxymaker;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE, ElementType.PACKAGE, ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Inherited
public @interface CustomAnnotation {
   String value();

   String[] changedBy() default {""};

   Priority[] priorities();

   Priority priority() default CustomAnnotation.Priority.MEDIUM;

   String createdBy() default "Darjus Loktevic";

   String lastChanged() default "08/06/2012";

   public static enum Priority {
      LOW,
      MEDIUM,
      HIGH;
   }
}
