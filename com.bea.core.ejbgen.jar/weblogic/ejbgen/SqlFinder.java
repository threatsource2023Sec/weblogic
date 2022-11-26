package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface SqlFinder {
   String maxElements() default "UNSPECIFIED";

   String signature();

   Constants.Bool includeUpdates() default Constants.Bool.UNSPECIFIED;

   Constants.IsolationLevel isolationLevel() default Constants.IsolationLevel.UNSPECIFIED;

   DBSpecificSQL[] dbSpecificSql() default {};

   String sqlShapeName() default "UNSPECIFIED";

   Constants.TransactionAttribute transactionAttribute() default Constants.TransactionAttribute.UNSPECIFIED;

   GenerateOn generateOn() default SqlFinder.GenerateOn.UNSPECIFIED;

   Constants.Bool sqlSelectDistinct() default Constants.Bool.UNSPECIFIED;

   Constants.Bool enableQueryCaching() default Constants.Bool.UNSPECIFIED;

   String sql();

   public static enum GenerateOn {
      UNSPECIFIED,
      LOCAL,
      REMOTE;
   }
}
