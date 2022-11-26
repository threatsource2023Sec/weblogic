package javax.resource.spi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Connector {
   String[] description() default {};

   String[] displayName() default {};

   String[] smallIcon() default {};

   String[] largeIcon() default {};

   String vendorName() default "";

   String eisType() default "";

   String version() default "";

   String[] licenseDescription() default {};

   boolean licenseRequired() default false;

   AuthenticationMechanism[] authMechanisms() default {};

   boolean reauthenticationSupport() default false;

   SecurityPermission[] securityPermissions() default {};

   TransactionSupport.TransactionSupportLevel transactionSupport() default TransactionSupport.TransactionSupportLevel.NoTransaction;

   Class[] requiredWorkContexts() default {};
}
