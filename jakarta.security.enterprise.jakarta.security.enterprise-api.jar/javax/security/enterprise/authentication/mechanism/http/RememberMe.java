package javax.security.enterprise.authentication.mechanism.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface RememberMe {
   @Nonbinding
   int cookieMaxAgeSeconds() default 86400;

   @Nonbinding
   String cookieMaxAgeSecondsExpression() default "";

   @Nonbinding
   boolean cookieSecureOnly() default true;

   @Nonbinding
   String cookieSecureOnlyExpression() default "";

   @Nonbinding
   boolean cookieHttpOnly() default true;

   @Nonbinding
   String cookieHttpOnlyExpression() default "";

   @Nonbinding
   String cookieName() default "JREMEMBERMEID";

   @Nonbinding
   boolean isRememberMe() default true;

   @Nonbinding
   String isRememberMeExpression() default "";
}
