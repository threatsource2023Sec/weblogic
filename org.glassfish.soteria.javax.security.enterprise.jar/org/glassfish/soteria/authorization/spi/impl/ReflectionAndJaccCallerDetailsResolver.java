package org.glassfish.soteria.authorization.spi.impl;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.jacc.PolicyContext;
import org.glassfish.soteria.authorization.JACC;
import org.glassfish.soteria.authorization.spi.CallerDetailsResolver;

public class ReflectionAndJaccCallerDetailsResolver implements CallerDetailsResolver {
   public Principal getCallerPrincipal() {
      Subject subject = JACC.getSubject();
      if (subject == null) {
         return null;
      } else {
         SubjectParser subjectParser = new SubjectParser(PolicyContext.getContextID(), Collections.emptyList());
         return subjectParser.getCallerPrincipalFromPrincipals(subject.getPrincipals());
      }
   }

   public Set getPrincipalsByType(Class pType) {
      Subject subject = JACC.getSubject();
      if (subject == null) {
         subject = new Subject();
      }

      return subject.getPrincipals(pType);
   }

   public boolean isCallerInRole(String role) {
      return JACC.isCallerInRole(role);
   }

   public Set getAllDeclaredCallerRoles() {
      return JACC.getAllDeclaredCallerRoles();
   }
}
