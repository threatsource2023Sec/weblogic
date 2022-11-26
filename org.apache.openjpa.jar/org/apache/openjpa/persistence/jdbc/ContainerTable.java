package org.apache.openjpa.persistence.jdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ContainerTable {
   String name() default "";

   String catalog() default "";

   String schema() default "";

   XJoinColumn[] joinColumns() default {};

   ForeignKey joinForeignKey() default @ForeignKey(
   specified = false
);

   Index joinIndex() default @Index(
   specified = false
);

   boolean specified() default true;
}
