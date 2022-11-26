package javax.jdo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Persistent {
   PersistenceModifier persistenceModifier() default PersistenceModifier.UNSPECIFIED;

   String table() default "";

   String defaultFetchGroup() default "";

   NullValue nullValue() default NullValue.NONE;

   String embedded() default "";

   String embeddedElement() default "";

   String embeddedKey() default "";

   String embeddedValue() default "";

   String serialized() default "";

   String serializedElement() default "";

   String serializedKey() default "";

   String serializedValue() default "";

   String dependent() default "";

   String dependentElement() default "";

   String dependentKey() default "";

   String dependentValue() default "";

   String primaryKey() default "";

   IdGeneratorStrategy valueStrategy() default IdGeneratorStrategy.UNSPECIFIED;

   String customValueStrategy() default "";

   String sequence() default "";

   String loadFetchGroup() default "";

   Class[] types() default {};

   String mappedBy() default "";

   Column[] columns() default {};

   String column() default "";

   String nullIndicatorColumn() default "";

   String name() default "";

   int recursionDepth() default 1;

   String cacheable() default "true";

   Extension[] extensions() default {};
}
