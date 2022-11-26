package javax.jdo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Join {
   String table() default "";

   String column() default "";

   String indexed() default "";

   String index() default "";

   String unique() default "";

   String uniqueKey() default "";

   String outer() default "";

   ForeignKeyAction deleteAction() default ForeignKeyAction.UNSPECIFIED;

   Column[] columns() default {};

   String generatePrimaryKey() default "";

   String primaryKey() default "";

   String generateForeignKey() default "";

   String foreignKey() default "";

   Extension[] extensions() default {};
}
