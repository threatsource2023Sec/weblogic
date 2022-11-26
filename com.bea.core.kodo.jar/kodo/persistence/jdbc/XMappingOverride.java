package kodo.persistence.jdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.persistence.Column;
import org.apache.openjpa.persistence.jdbc.ContainerTable;
import org.apache.openjpa.persistence.jdbc.ElementJoinColumn;
import org.apache.openjpa.persistence.jdbc.XJoinColumn;

/** @deprecated */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface XMappingOverride {
   String name() default "";

   Column[] columns() default {};

   XJoinColumn[] joinColumns() default {};

   ElementColumn[] elementColumns() default {};

   ElementJoinColumn[] elementJoinColumns() default {};

   KeyColumn[] keyColumns() default {};

   KeyJoinColumn[] keyJoinColumns() default {};

   ContainerTable containerTable() default @ContainerTable(
   specified = false
);
}
