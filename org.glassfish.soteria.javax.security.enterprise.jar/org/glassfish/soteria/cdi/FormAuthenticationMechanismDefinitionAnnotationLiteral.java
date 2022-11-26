package org.glassfish.soteria.cdi;

import javax.enterprise.util.AnnotationLiteral;
import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;

public class FormAuthenticationMechanismDefinitionAnnotationLiteral extends AnnotationLiteral implements FormAuthenticationMechanismDefinition {
   private static final long serialVersionUID = 1L;
   private final LoginToContinue loginToContinue;

   public FormAuthenticationMechanismDefinitionAnnotationLiteral(LoginToContinue loginToContinue) {
      this.loginToContinue = loginToContinue;
   }

   public LoginToContinue loginToContinue() {
      return this.loginToContinue;
   }
}
