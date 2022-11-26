package javax.resource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.resource.spi.TransactionSupport;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ConnectionFactoryDefinition {
   String name();

   String description() default "";

   String resourceAdapter();

   String interfaceName();

   TransactionSupport.TransactionSupportLevel transactionSupport() default TransactionSupport.TransactionSupportLevel.NoTransaction;

   int maxPoolSize() default -1;

   int minPoolSize() default -1;

   String[] properties() default {};
}
