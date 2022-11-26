package javax.security.enterprise.authentication.mechanism.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.util.Nonbinding;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CustomFormAuthenticationMechanismDefinition {
   @Nonbinding
   LoginToContinue loginToContinue();
}
