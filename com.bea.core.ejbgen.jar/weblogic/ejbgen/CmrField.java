package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
public @interface CmrField {
   Constants.Bool readOnlyInValueObject() default Constants.Bool.UNSPECIFIED;

   String groupNames() default "UNSPECIFIED";

   String orderingNumber() default "UNSPECIFIED";

   Constants.Bool excludeFromValueObject() default Constants.Bool.UNSPECIFIED;
}
