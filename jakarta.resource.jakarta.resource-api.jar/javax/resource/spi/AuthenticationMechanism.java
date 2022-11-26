package javax.resource.spi;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface AuthenticationMechanism {
   String authMechanism() default "BasicPassword";

   String[] description() default {};

   CredentialInterface credentialInterface() default AuthenticationMechanism.CredentialInterface.PasswordCredential;

   public static enum CredentialInterface {
      PasswordCredential,
      GSSCredential,
      GenericCredential;
   }
}
