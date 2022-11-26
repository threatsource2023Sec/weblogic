package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface Finder {
   Constants.IsolationLevel isolationLevel() default Constants.IsolationLevel.UNSPECIFIED;

   Constants.TransactionAttribute transactionAttribute() default Constants.TransactionAttribute.UNSPECIFIED;

   GenerateOn generateOn() default Finder.GenerateOn.UNSPECIFIED;

   String comment() default "UNSPECIFIED";

   String id() default "UNSPECIFIED";

   String cachingName() default "UNSPECIFIED";

   String groupName() default "UNSPECIFIED";

   String maxElements() default "UNSPECIFIED";

   String signature() default "UNSPECIFIED";

   Constants.Bool sqlSelectDistinct() default Constants.Bool.UNSPECIFIED;

   Constants.Bool includeUpdates() default Constants.Bool.UNSPECIFIED;

   String ejbQl() default "UNSPECIFIED";

   String weblogicEjbQl() default "UNSPECIFIED";

   public static enum GenerateOn {
      UNSPECIFIED,
      LOCAL,
      REMOTE;
   }
}
