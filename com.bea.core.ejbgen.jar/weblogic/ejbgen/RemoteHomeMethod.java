package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
public @interface RemoteHomeMethod {
   String orderingNumber() default "UNSPECIFIED";

   Constants.IsolationLevel isolationLevel() default Constants.IsolationLevel.UNSPECIFIED;

   Constants.TransactionAttribute transactionAttribute() default Constants.TransactionAttribute.UNSPECIFIED;

   String roles() default "UNSPECIFIED";

   Constants.Bool isIdempotent() default Constants.Bool.UNSPECIFIED;

   int retryCount() default 0;
}
