package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
public @interface ServiceEndpointMethod {
   Constants.TransactionAttribute transactionAttribute() default Constants.TransactionAttribute.UNSPECIFIED;

   Constants.Bool unchecked() default Constants.Bool.UNSPECIFIED;

   String roles() default "UNSPECIFIED";
}
