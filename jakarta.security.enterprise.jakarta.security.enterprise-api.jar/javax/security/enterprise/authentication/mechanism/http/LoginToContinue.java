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
public @interface LoginToContinue {
   @Nonbinding
   String loginPage() default "/login";

   @Nonbinding
   boolean useForwardToLogin() default true;

   @Nonbinding
   String useForwardToLoginExpression() default "";

   @Nonbinding
   String errorPage() default "/login-error";
}
