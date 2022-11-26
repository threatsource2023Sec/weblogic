package javax.security.enterprise.identitystore;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface LdapIdentityStoreDefinition {
   String url() default "";

   String bindDn() default "";

   String bindDnPassword() default "";

   String callerBaseDn() default "";

   String callerNameAttribute() default "uid";

   String callerSearchBase() default "";

   String callerSearchFilter() default "";

   LdapSearchScope callerSearchScope() default LdapIdentityStoreDefinition.LdapSearchScope.SUBTREE;

   String callerSearchScopeExpression() default "";

   String groupSearchBase() default "";

   String groupSearchFilter() default "";

   LdapSearchScope groupSearchScope() default LdapIdentityStoreDefinition.LdapSearchScope.SUBTREE;

   String groupSearchScopeExpression() default "";

   String groupNameAttribute() default "cn";

   String groupMemberAttribute() default "member";

   String groupMemberOfAttribute() default "memberOf";

   int readTimeout() default 0;

   String readTimeoutExpression() default "";

   int maxResults() default 1000;

   String maxResultsExpression() default "";

   int priority() default 80;

   String priorityExpression() default "";

   IdentityStore.ValidationType[] useFor() default {IdentityStore.ValidationType.VALIDATE, IdentityStore.ValidationType.PROVIDE_GROUPS};

   String useForExpression() default "";

   public static enum LdapSearchScope {
      ONE_LEVEL,
      SUBTREE;
   }
}
