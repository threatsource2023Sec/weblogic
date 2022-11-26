package javax.security.enterprise.identitystore;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface DatabaseIdentityStoreDefinition {
   String dataSourceLookup() default "java:comp/DefaultDataSource";

   String callerQuery() default "";

   String groupsQuery() default "";

   Class hashAlgorithm() default Pbkdf2PasswordHash.class;

   String[] hashAlgorithmParameters() default {};

   int priority() default 70;

   String priorityExpression() default "";

   IdentityStore.ValidationType[] useFor() default {IdentityStore.ValidationType.VALIDATE, IdentityStore.ValidationType.PROVIDE_GROUPS};

   String useForExpression() default "";
}
