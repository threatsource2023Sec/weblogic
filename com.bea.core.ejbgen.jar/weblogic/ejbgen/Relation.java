package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface Relation {
   Multiplicity multiplicity();

   String name();

   String groupName() default "UNSPECIFIED";

   /** @deprecated */
   @Deprecated
   String jointTable() default "UNSPECIFIED";

   String joinTable() default "UNSPECIFIED";

   String fkColumn() default "UNSPECIFIED";

   String roleName() default "UNSPECIFIED";

   String foreignKeyTable() default "UNSPECIFIED";

   String primaryKeyTable() default "UNSPECIFIED";

   String cmrField() default "UNSPECIFIED";

   Constants.Bool dbCascadeDelete() default Constants.Bool.UNSPECIFIED;

   Constants.Bool cascadeDelete() default Constants.Bool.UNSPECIFIED;

   String targetEjb() default "UNSPECIFIED";

   String id() default "UNSPECIFIED";

   public static enum Multiplicity {
      UNSPECIFIED,
      ONE,
      MANY;
   }
}
